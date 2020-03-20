package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.commom.CommonPage;
import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.constant.QuestionPublicStatus;
import cn.tedu.straw.portal.api.EsQuestionServiceApi;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.config.FastDfsConfig;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.exception.PageNotExistException;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.utils.ImgUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


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
    private FastDfsConfig fastDfsConfig;
    @Resource
    private QuestionTagMapper questionTagMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private EsQuestionServiceApi questionServiceApi;


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
             questionList=questionMapper.selectQuestionWithTags(getUseId(), QuestionPublicStatus.PUBLIC.getStatus());
        }else if(userRoleNames.contains("ROLE_TEACHER")){//只要拥有老师角色就ok,管理员拥有学生和老师的角色
            //老师角色和管理员可以查看所有的问题
            questionList=questionMapper.selectQuestionWithTags(null,null);
        }

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
       // PageHelper.startPage(pageNum,pageSize);

        //根据不同的角色请求不同的资源
        List<String> userRoleNames = getUserRoleNames();
        if(userRoleNames.contains("ROLE_STUDENT")&&userRoleNames.size()==1){//只有学生角色一个角色，没有其它的角色
            //只查找本人发布的问题和已经公开的问题

        }else if(userRoleNames.contains("ROLE_TEACHER")){//只要拥有老师角色就ok,管理员拥有学生和老师的角色
            //老师角色和管理员可以查看所有的问题

        }

        PageInfo<Question> pageInfo=new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public StrawResult uploadImg(MultipartFile[] files, HttpServletRequest request) {
        List<String> images=new ArrayList<>();
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    String fileName = file.getOriginalFilename();
                    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
                       return StrawResult.builder().code(400).msg("图片格式不正确，仅支持后缀名为.jpg,.png的图片").build();
                    }
                    //save file
                    if (!file.isEmpty()) {
                        String savePath = ImgUtils.upload(file);
                        if (savePath!=null){
                            String hostUrl = fastDfsConfig.getHostUrl();
                             String url = hostUrl + savePath;
                            System.out.println("url:"+url);
                            images.add(url);
                        }
                    }
                }
            }
           return StrawResult.builder().build().success(images);
        }catch (Exception e){
            log.error(e.getMessage());
            return StrawResult.builder().build().failed();
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(QuestionParam param) {
        //保存问题
        Question question=new Question(param.getTitle(),param.getContent()
                ,getUserNickname(),getUseId(),new Date(),0,0,QuestionPublicStatus.PRIVATE.getStatus());
        int n=questionMapper.insert(question);
        if(n!=1){
            throw  new BusinessException("服务器繁忙，请稍后重试");
        }
        //保存问题标签
       Integer[] tags= param.getTags();
       for(int i=0;i<tags.length;i++){
           QuestionTag questionTag=new QuestionTag();
           questionTag.setTagId(tags[i]);
           questionTag.setQuestionId(question.getId());
          int m= questionTagMapper.insert(questionTag);
          if(m!=1){
              throw  new BusinessException("服务器繁忙，请稍后重试");
          }
       }
    // TODO 2.0版本把该功能迁移到Kafka消息队列处理


       //保存到es
        List<String> tagNames=new ArrayList<>();
       List<Tag> tagList=new ArrayList<>();
        for(Integer tagId:tags){
            Tag tag = tagMapper.selectById(tagId);
            if (tag == null) {
                log.error("id为"+tagId+"的标签不存在");
                throw  new RuntimeException("服务器繁忙，请稍后重试");
            }
            tagNames.add(tag.getName());
            tagList.add(tag);

        }




        EsQuestion esQuestion=new EsQuestion(question.getId(),question.getTitle(),question.getContent(),
                question.getUserNickName(),question.getUserId(),question.getCreatetime(),question.getStatus(),question.getPageViews(),
                question.getPublicStatus(),question.getDistanceTime(),tagNames,tagList);
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
        // TODO 2.0版本应该改成kafka消息队列
        question.setPageViews(question.getPageViews()+1);
        questionMapper.updateById(question);


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


}
