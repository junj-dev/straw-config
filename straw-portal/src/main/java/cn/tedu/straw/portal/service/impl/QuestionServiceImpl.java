package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.constant.QuestionPublicStatus;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.config.FastDfsConfig;
import cn.tedu.straw.portal.domian.StrawResult;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.utils.ImgUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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


    @Override
    public PageInfo<Question> selectPage(Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw  new BusinessException("分页参数不能为空！");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionList=new ArrayList<>();
        //根据不同的角色请求不同的资源
        List<Role> roles= getUserRole();
        if(CollectionUtils.isEmpty(roles)){
            log.error("该用户没有角色，请检查该用户信息是否正常");
            throw  new RuntimeException("系统繁忙，请稍后重试!");
        }
        //把Role的集合转换成角色名称集合
      List<String> roleNames=  roles.stream().map(role -> {
            return role.getName();
        }).collect(Collectors.toList());

        if(roleNames.contains("ROLE_STUDENT")){//学生角色
            //只查找本人发布的问题和已经公开的问题
             questionList=questionMapper.selectQuestionWithTags(getUseId(), QuestionPublicStatus.PUBLIC.getStatus());
        }else if(roleNames.contains("ROLE_TEACHER")){
            //老师角色和管理员可以查看所有的问题
            questionList=questionMapper.selectQuestionWithTags(null,null);
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
        Question question=new Question();
        question.setTitle(param.getTitle());
        question.setContent(param.getContent());
        question.setCreatetime(new Date());
        question.setPageViews(0);
        question.setUserNickName(getUserNickname());
        question.setUserId(getUseId());
        question.setStatus("0");
        questionMapper.insert(question);
        //保存问题标签
       String[] tags= param.getTags();
       for(int i=0;i<tags.length;i++){
           Long tagId=Long.parseLong(tags[i]);
           QuestionTag questionTag=new QuestionTag();
           questionTag.setTagId(tagId);
           questionTag.setQuestionId(question.getId());
           questionTagMapper.insert(questionTag);
       }

        return true;
    }

    @Override
    public Question getQuestionDetailById(Long id) {
        Question question = questionMapper.selectById(id);
        if(question==null){
            throw  new BusinessException("该页面不存在！");
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

        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean answer(Long id, String content) {
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
        question.setStatus("1");
        questionMapper.updateById(question);

         return true;
    }
}
