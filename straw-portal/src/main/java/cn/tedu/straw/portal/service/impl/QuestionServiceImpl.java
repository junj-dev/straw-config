package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.constant.QuestionPublicStatus;
import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.config.UploadFileConfig;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.exception.PageNotExistException;
import cn.tedu.straw.portal.mapper.*;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Service
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Resource
    private  QuestionMapper questionMapper;

    @Resource
    private UserQuestionMapper userQuestionMapper;
    @Resource
    private QuestionTagMapper questionTagMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private EsQuestionServiceApi questionServiceApi;
    @Resource
    private UploadFileConfig fileConfig;
    @Resource
    private UserMapper userMapper;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();


    @Override
    public PageInfo<Question> selectPage(Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw  new BusinessException("分页参数不能为空！");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionList=new ArrayList<>();
        //根据不同的角色请求不同的资源
        List<String> userRoleNames = getUserRoleNames();
        if(userRoleNames.contains("ROLE_STUDENT")&&userRoleNames.size()==1){//只有学生角色一个角色，没有其它的角色
            //只查找本人发布的问题和已经公开的问题
           questionList=questionMapper.findQuestionByUserIdOrPublicStatus(getUseId(),QuestionPublicStatus.PUBLIC.getStatus());
        }else if(userRoleNames.contains("ROLE_TEACHER")){//只要拥有老师角色就ok,管理员拥有学生和老师的角色
            //老师角色和管理员可以查看所有的问题
            questionList=questionMapper.findAllQuestion();
        }

        PageInfo<Question> pageInfo=new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public PageInfo<Question> selectPersonalQuestion(Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw  new BusinessException("分页参数不能为空！");
        }
        //只查找本人发布的问题和已经公开的问题
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionList=questionMapper.findQuestionByUserId(getUseId());
        PageInfo<Question> pageInfo=new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public PageInfo<Question> selectPage(Integer tagId, Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw  new BusinessException("分页参数不能为空！");
        }

        List<Question> questionList=new ArrayList<>();

        //根据tag找出所有拥有tag标签的问题
        PageHelper.startPage(pageNum,pageSize);

        //根据不同的角色请求不同的资源
        List<String> userRoleNames = getUserRoleNames();
        if(userRoleNames.contains("ROLE_STUDENT")&&userRoleNames.size()==1){//只有学生角色一个角色，没有其它的角色
            //只查找本人发布的问题和已经公开的问题
            questionList=questionMapper.findQuestionByUserIdAndTagId(getUseId(),tagId);

        }else if(userRoleNames.contains("ROLE_TEACHER")){//只要拥有老师角色就ok,管理员拥有学生和老师的角色
            //老师角色和管理员可以查看所有的问题
            questionList=questionMapper.findQuestionByTagId(tagId);
        }

        PageInfo<Question> pageInfo=new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public StrawResult uploadImg(MultipartFile[] files, HttpServletRequest request) {
        List<String> images=new ArrayList<>();
        for(MultipartFile file:files){

            //获取绝对路径
            String realPath=fileConfig.getFilePath();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");
            String format=sdf.format(new Date());
            //文件存放的目录
            File folder=new File(realPath+format);
            if(!folder.isDirectory()){
                folder.mkdirs();
            }
            String oldName=file.getOriginalFilename();
            //文件后缀
            String suffix=oldName.substring(oldName.lastIndexOf("."),oldName.length());
            //文件新名字
            String newName=UUID.randomUUID().toString()+suffix;
            try {
                File targetFile=new File(folder,newName);

                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }else {
                    targetFile.delete();
                }
                file.transferTo(targetFile);
                String filePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/uploadFile/"+format+newName;
                images.add(filePath);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
       return new StrawResult().success(images);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create( QuestionParam param) {
        //保存问题
        Question question=new Question(param.getTitle(),param.getContent()
                ,getUserNickname(),getUseId(),new Date(),0,0, QuestionPublicStatus.PRIVATE.getStatus());
        int n=questionMapper.insert(question);
        if(n!=1){
            throw  new BusinessException("服务器繁忙，请稍后重试");
        }
        //保存问题标签
       String[] tagNames= param.getTagNames();
        List<Tag> tagList=new ArrayList<>();
       for(String tagName:tagNames){
           QueryWrapper tagQuery=new QueryWrapper();
           tagQuery.eq("name",tagName);
           Tag tag = tagMapper.selectOne(tagQuery);
           if(tag==null){
               throw  new BusinessException(tagName+"标签已被删除,请重新选择!");
           }
           tagList.add(tag);
           QuestionTag questionTag=new QuestionTag();
           questionTag.setTagId(tag.getId());
           questionTag.setQuestionId(question.getId());
          int m= questionTagMapper.insert(questionTag);
          if(m!=1){
              throw  new BusinessException("服务器繁忙，请稍后重试");
          }


       }
       //保存老师和问题的关系
        String[] teacherNames = param.getTeacherNames();
       for(String teaherName:teacherNames){
           QueryWrapper teacherQuery=new QueryWrapper();
           teacherQuery.eq("nickname",teaherName);
           User teacher = userMapper.selectOne(teacherQuery);
           if(teacher==null){throw  new BusinessException(teaherName+":该老师名称已被删除,请重新选择!");}
           UserQuestion userQuestion =new UserQuestion(teacher.getId(),question.getId(),new Date());
           int i = userQuestionMapper.insert(userQuestion);
           if(i!=1){
               throw  new BusinessException("服务器繁忙，请稍后重试");
           }
       }
        // TODO 2.0版本把该功能迁移到Kafka消息队列处理

        EsQuestion esQuestion=new EsQuestion(question.getId(),question.getTitle(),question.getContent(),
                question.getUserNickName(),question.getUserId(),question.getCreatetime(),question.getStatus(),question.getPageViews(),
                question.getPublicStatus(),Arrays.asList(param.getTagNames()),tagList);
       boolean flag= questionServiceApi.saveQuestion(esQuestion);
        if(!flag){
            throw  new BusinessException("服务器繁忙，请稍后重试");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question getQuestionDetailById(Integer id) {
        //学生角色只能查看自己和公开的问题，老师可以访问所有的问题
        Question question = questionMapper.selectById(id);
        if(question==null){
            throw  new PageNotExistException();
        }
        //如果该问题既不是本人发表的问题又不是公开的问题，角色又是学生
        if(getUserRoleNames().contains("ROLE_STUDENT") //学生角色
                &&getUserRoleNames().size()==1//只拥有一个角色，该角色是学生
                &&question.getUserId().intValue()!=getUseId().intValue() //问题不是本人发表
                &&QuestionPublicStatus.PRIVATE.getStatus().equals(question.getPublicStatus())){ //问题不是公开的
            throw  new PageNotExistException();//表示该问题不存在，无权访问
        }

        //设置标签
        List<Tag> tags = tagMapper.selectTagsByQuestionId(id);
        question.setTags(tags);
        //设置回答
        QueryWrapper<Answer> query=new QueryWrapper<>();
        query.eq("quest_id",id);
        query.orderBy(true,false,"createtime");
        List<Answer> answers = answerMapper.selectList(query);
        question.setAnswers(answers);

        //浏览量加1
        //kafka消息队列
     //  question.setPageViews(question.getPageViews()+1);
//        questionMapper.updateById(question);
        kafkaTemplate.send("straw-portal-pageView",String.valueOf(question.getId()));

        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean answer(Integer id, String content) {
        if(StringUtils.isEmpty(id)){
            log.error("问题id为空");
            throw  new BusinessException("系统繁忙，请稍后重试");
        }
        if(StringUtils.isEmpty(content)){
            throw  new BusinessException("内容不能为空");
        }
        Answer answer=new Answer();
        answer.setContent(content);
        answer.setQuestId(id);
        answer.setCreatetime(new Date());
        answer.setLikeCount(0);
        answer.setUserId(getUseId());
        answer.setUserNickName(getUserNickname());
        //保存问题的答案
         answerMapper.insert(answer);
         //修改问题的状态为未解决
        Question question=new Question();
        question.setId(id);
        question.setStatus(1);
        questionMapper.updateById(question);

         return true;
    }

    @Override
    public StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        List<String> userRoleNames = getUserRoleNames();
        //只有学生角色
        if(userRoleNames.contains("ROLE_STUDENT")&&userRoleNames.size()==1){
            return  questionServiceApi.searchOpenQuestion(keyword,pageNum,pageSize,getUseId(), QuestionPublicStatus.PUBLIC.getStatus());
        }
        return questionServiceApi.search(keyword,pageNum,pageSize);

    }

    @Override
    public PageInfo<Question> findAllQuestion(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = questionMapper.findAllQuestion();
        PageInfo<Question> pageInfo=new PageInfo<>(questions);
        return pageInfo;
    }

    /**
     *
     * 按条件查询提问
     * @param condition
     * @return
     */
    @Override
    public PageInfo<Question> findQuestionByCondition(QuestionQueryParam condition) {
        if(condition==null){throw  new BusinessException("请求参数不能为空");}
        Integer pageNum=1;
        Integer pageSize=10;
        if(condition.getPageNum()!=null){
            pageNum=condition.getPageNum();
        }
        if(condition.getPageSize()!=null){
            pageSize=condition.getPageSize();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions =  questionMapper.findQuestionByCondition(condition);
        PageInfo<Question> pageInfo=new PageInfo<>(questions);
        return pageInfo;
    }

    /**
     * 设置提问为开放问题
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setQuestionPublic(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setPublicStatus(QuestionPublicStatus.PUBLIC.getStatus());
        return questionMapper.updateById(question)==1;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setQuestionPublic(Integer[] ids) {
        Question question=new Question();
        question.setPublicStatus(QuestionPublicStatus.PUBLIC.getStatus());
        questionMapper.updateById(question);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("id",ids);
        int update = questionMapper.update(question, queryWrapper);
        return update>=1;
    }

    /**
     * 取消公开问题，设置为私密问题，只能提问者和老师查看
     * @param id
     * @return
     */
    @Override
    public boolean cancelQuestionPublic(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setPublicStatus(QuestionPublicStatus.PRIVATE.getStatus());
        return questionMapper.updateById(question)==1;
    }
    @Override
    public boolean cancelQuestionPublic(Integer[] ids) {
        Question question=new Question();
        question.setPublicStatus(QuestionPublicStatus.PRIVATE.getStatus());
        questionMapper.updateById(question);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("id",ids);
        int update = questionMapper.update(question, queryWrapper);
        return update>=1;
    }

    @Override
    public PageInfo<Question> findMyUnAnwerQuestion(Integer pageNum, Integer pageSize) {
        return getQuestionPageInfo(pageNum, pageSize,0);
    }

    private PageInfo<Question> getQuestionPageInfo(Integer pageNum, Integer pageSize,Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.findQuestionByUserIdAndStatus(getUseId(), status);
        return new PageInfo<>(questions);
    }

    @Override
    public PageInfo<Question> findMyUnSolveQuestion(Integer pageNum, Integer pageSize) {
        return getQuestionPageInfo(pageNum, pageSize,1);
    }

    @Override
    public PageInfo<Question> findMySolvedQuestion(Integer pageNum, Integer pageSize) {
        return getQuestionPageInfo(pageNum, pageSize,2);
    }

    @Override
    public boolean setQuestionSolved(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setStatus(2);
        return questionMapper.updateById(question)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setQuestionSolved(Integer[] ids) {
        Question question=new Question();
        question.setStatus(2);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("id",ids);
        return  questionMapper.update(question,queryWrapper)>=1;
    }

    /**
     * 把问题转发给其他老师
     * @param teacherIds
     * @param questionIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferToTeacher(Integer[] teacherIds, Integer[] questionIds) {
       if(teacherIds.length==0||questionIds.length==0){
           throw  new BusinessException("所选的老师和问题都不能为空！");
       }
       Date now=new Date();
       for(Integer teacherId:teacherIds){
           for(Integer questionId:questionIds){
               UserQuestion userQuestion =new UserQuestion(teacherId,questionId,now);
               //先查找该TeacherQuestion是否已存在，如果存在则不做处理
               QueryWrapper queryWrapper=new QueryWrapper();
               queryWrapper.eq("teacher_id",teacherId);
               queryWrapper.eq("question_id",questionId);
               UserQuestion t = userQuestionMapper.selectOne(queryWrapper);
               //不存在则添加，防止重复
               if(t==null){
                   int n = userQuestionMapper.insert(userQuestion);
                   if(n!=1){
                       throw  new BusinessException("服务繁忙，请稍后再试！");
                   }
               }

           }
       }
        return true;
    }




}
