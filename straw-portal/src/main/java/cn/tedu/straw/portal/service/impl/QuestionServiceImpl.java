package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.constant.KafkaTopic;
import cn.tedu.straw.common.constant.QuestionPublicStatus;
import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.config.UploadFileConfig;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.domian.param.QuestionUpdateParam;
import cn.tedu.straw.portal.domian.vo.QuestionVO;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.exception.PageNotExistException;
import cn.tedu.straw.portal.mapper.*;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IQuestionTagService;
import cn.tedu.straw.portal.service.ITeacherQuestionService;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Service
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private TeacherQuestionMapper teacherQuestionMapper;
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
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private UserCollectMapper userCollectMapper;
    @Resource
    private IQuestionTagService questionTagService;
    @Resource
    private ITeacherQuestionService teacherQuestionService;

    private Gson gson = new GsonBuilder().create();
    @Value("${file.request.prefix}")
    private String requestPrefix;


    @Override
    public PageInfo<Question> listQuestions(Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw new BusinessException("分页参数不能为空！");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = new ArrayList<>();
        //根据不同的角色请求不同的资源
        List<String> userRoleNames = getUserRoleNames();
        if (userRoleNames.contains("ROLE_STUDENT") && userRoleNames.size() == 1) {//只有学生角色一个角色，没有其它的角色
            //只查找本人发布的问题和已经公开的问题
            questionList = questionMapper.findQuestionByUserIdOrPublicStatus(getUseId(), QuestionPublicStatus.PUBLIC.getStatus());
        } else if (userRoleNames.contains("ROLE_TEACHER")) {//只要拥有老师角色就ok,管理员拥有学生和老师的角色
            //老师角色和管理员可以查看所有的问题
            questionList = questionMapper.findAllQuestion();
        }

        PageInfo<Question> pageInfo = new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public PageInfo<Question> listPersonalQuestions(Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw new BusinessException("分页参数不能为空！");
        }
        //只查找本人发布的问题和已经公开的问题
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = questionMapper.findQuestionByUserId(getUseId());
        PageInfo<Question> pageInfo = new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public PageInfo<Question> selectPage(Integer tagId, Integer pageNum, Integer pageSize) {
        //使用分页插件
        if (pageNum == null || pageSize == null) {
            throw new BusinessException("分页参数不能为空！");
        }

        List<Question> questionList = new ArrayList<>();

        //根据tag找出所有拥有tag标签的问题
        PageHelper.startPage(pageNum, pageSize);

        //根据不同的角色请求不同的资源
        List<String> userRoleNames = getUserRoleNames();
        if (userRoleNames.contains("ROLE_STUDENT") && userRoleNames.size() == 1) {//只有学生角色一个角色，没有其它的角色
            //只查找本人发布的问题和已经公开的问题
            questionList = questionMapper.findQuestionByUserIdAndTagId(getUseId(), tagId);

        } else if (userRoleNames.contains("ROLE_TEACHER")) {//只要拥有老师角色就ok,管理员拥有学生和老师的角色
            //老师角色和管理员可以查看所有的问题
            questionList = questionMapper.findQuestionByTagId(tagId);
        }

        PageInfo<Question> pageInfo = new PageInfo<>(questionList);
        return pageInfo;
    }

    @Override
    public List<String> uploadImg(MultipartFile[] files, HttpServletRequest request) {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {

            //获取绝对路径
            String realPath = fileConfig.getFilePath();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String format = sdf.format(new Date());
            //文件存放的目录
            File folder = new File(realPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = file.getOriginalFilename();
            //文件后缀
            String suffix = oldName.substring(oldName.lastIndexOf("."), oldName.length());
            //文件新名字
            String newName = UUID.randomUUID().toString() + suffix;
            try {
                File targetFile = new File(folder, newName);

                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                } else {
                    targetFile.delete();
                }
                file.transferTo(targetFile);
                String filePath = requestPrefix + format + newName;
                images.add(filePath);
            } catch (IOException e) {
                log.error("上传图片出错!");
                throw new RuntimeException("上传图片出错!");

            }

        }

        return images;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveQuestion(QuestionParam param) {
        //保存问题
        Question question = new Question(param.getTitle(), param.getContent()
                , getUserNickname(), getUseId(), new Date(), 0, 0, QuestionPublicStatus.PRIVATE.getStatus());
        int rows = questionMapper.insert(question);
        //提取标签列表
        List<Tag> tagList = getTagList(param.getTagNames());
        //保存问题标签
        log.debug("保存问题标签:{}", tagList);
        saveQuestionTags(question, tagList);
        //保存老师问题记录
        log.debug("保存老师问题的关系:{}", param.getTeacherNames());
        saveTeacherQuestions(param.getTeacherNames(), question);
        //向kafka发送消息

        EsQuestion esQuestion = new EsQuestion(question.getId(), question.getTitle(), question.getContent(),
                question.getUserNickName(), question.getUserId(), question.getCreatetime(), question.getStatus(), question.getPageViews(),
                question.getPublicStatus(), Arrays.asList(param.getTagNames()), tagList);
        log.debug("向kafka发送消息:{}", esQuestion);
        ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(KafkaTopic.PORTAL_CREATE_QUESTION, gson.toJson(esQuestion));
        sendResult.addCallback(new SuccessCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug("向kafka发送消息成功:{}", esQuestion);
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("发送消息到kafka失败",ex);
                throw new BusinessException("服务器开小差，请稍后重试！");
            }
        });


    }

    /**
     * 批量插入老师问题记录
     *
     * @param teacherNames
     * @param question
     */
    private void saveTeacherQuestions(String[] teacherNames, Question question) {
        QueryWrapper teacherQuery = new QueryWrapper();
        teacherQuery.in("nickname", teacherNames);
        List<User> teacherList = userMapper.selectList(teacherQuery);
        List<TeacherQuestion> teacherQuestionList = new ArrayList<>();
        for (User user : teacherList) {
            TeacherQuestion teacherQuestion = new TeacherQuestion(user.getId(), question.getId(), new Date());
            teacherQuestionList.add(teacherQuestion);
        }
        //批量插入
        teacherQuestionService.saveBatch(teacherQuestionList);
        //向kafka发送消息（添加通知消息,给老师发送通知）
        Integer userId = getUseId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (User user : teacherList) {
                    Notice notice = new Notice(3, question.getId(), new Date(), user.getId(), userId, false);
                    log.debug("向老师发送通知：{}", notice);
                    kafkaTemplate.send(KafkaTopic.PORTAL_NOTICE, gson.toJson(notice));
                }

            }
        }).start();
    }

    /**
     * 获取标签列表
     *
     * @param tagNames
     * @return
     */
    public List<Tag> getTagList(String[] tagNames) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("name", tagNames);
        List<Tag> list = tagMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 批量导入问题标签
     *
     * @param question
     * @param tagList
     */
    public void saveQuestionTags(Question question, List<Tag> tagList) {
        List<QuestionTag> questionTagList = new ArrayList<>();
        for (Tag tag : tagList) {
            QuestionTag questionTag = new QuestionTag(question.getId(), tag.getId());
            questionTagList.add(questionTag);
        }
        //批量插入问题标签
        questionTagService.saveBatch(questionTagList);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question getQuestionDetailById(Integer id) {
        //学生角色只能查看自己和公开的问题，老师可以访问所有的问题
        QueryWrapper questionQuery = new QueryWrapper();
        questionQuery.eq("delete_status", false);
        questionQuery.eq("id", id);
        Question question = questionMapper.selectOne(questionQuery);
        if (question == null) {
            throw new PageNotExistException();
        }
        //如果该问题既不是本人发表的问题又不是公开的问题，角色又是学生
        if (getUserRoleNames().contains("ROLE_STUDENT") //学生角色
                && getUserRoleNames().size() == 1//只拥有一个角色，该角色是学生
                && question.getUserId().intValue() != getUseId().intValue() //问题不是本人发表
                && QuestionPublicStatus.PRIVATE.getStatus().equals(question.getPublicStatus())) { //问题不是公开的
            throw new PageNotExistException();//表示该问题不存在，无权访问
        }

        //设置标签
        List<Tag> tags = tagMapper.selectTagsByQuestionId(id);
        question.setTags(tags);
        //设置回答
        List<Answer> answers = getAnswers(id);
        question.setAnswers(answers);

        //浏览量加1
        //kafka消息队列
        kafkaTemplate.send("straw-portal-pageView", String.valueOf(question.getId()));

        return question;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAnswer(Integer id, String content) {
        if (StringUtils.isEmpty(id)) {
            log.info("问题id为空");
            throw new BusinessException("问题id为空！");
        }

        if (StringUtils.isEmpty(content)) {
            log.info("内容不能为空");
            throw new BusinessException("内容不能为空");
        }
        Question question = questionMapper.selectById(id);
        if(question==null){
            log.info("id参数错误，不存在该问题！");
            throw  new BusinessException("id参数错误，不存在该问题！");
        }
        Answer answer = new Answer(content, 0, getUseId(), getUserNickname(), id, new Date());
        //保存问题的答案
        int rows=answerMapper.insert(answer);
        if(rows!=1){
            log.error("服务器开小差了，问题保存失败！");
            throw new BusinessException("服务器开小差了，保存失败！");
        }
        //修改问题的状态为未解决
        question.setStatus(1);
        questionMapper.updateById(question);
        //生成消息通知
        Notice notice = new Notice(1, question.getId(), new Date(), question.getUserId(), getUseId(), false);
        ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(KafkaTopic.PORTAL_NOTICE, gson.toJson(notice));
        sendResult.addCallback(new SuccessCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug("保存问题答案时，发送通知信息到kafka成功！");
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("发送kafka信息失败！",ex);
                throw  new BusinessException("服务器开小差了，请稍后再试！");
            }
        });
    }

    @Override
    public R<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        List<String> userRoleNames = getUserRoleNames();
        R<CommonPage<EsQuestion>> result = new R<>();
        //只有学生角色
        if (userRoleNames.contains("ROLE_STUDENT") && userRoleNames.size() == 1) {
            result = questionServiceApi.searchOpenQuestion(keyword, pageNum, pageSize, getUseId(), QuestionPublicStatus.PUBLIC.getStatus());
        } else {
            result = questionServiceApi.search(keyword, pageNum, pageSize);
        }
        List<EsQuestion> esQuestions = result.getData().getList();
        //设置状态和浏览量
        for (EsQuestion esQuestion : esQuestions) {
            Question question = questionMapper.selectById(esQuestion.getId());
            if (question != null) {
                esQuestion.setPageViews(question.getPageViews());
                esQuestion.setStatus(question.getStatus());
            }
        }


        return result;
    }

    @Override
    public PageInfo<Question> findAllQuestion(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.findAllQuestion();
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        return pageInfo;
    }

    /**
     * 按条件查询提问
     *
     * @param condition
     * @return
     */
    @Override
    public PageInfo<Question> findQuestionByCondition(QuestionQueryParam condition) {
        if (condition == null) {
            throw new BusinessException("请求参数不能为空");
        }
        Integer pageNum = 1;
        Integer pageSize = 10;
        if (condition.getPageNum() != null) {
            pageNum = condition.getPageNum();
        }
        if (condition.getPageSize() != null) {
            pageSize = condition.getPageSize();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.findQuestionByCondition(condition);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        return pageInfo;
    }

    /**
     * 修改问题的公开状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestionPublicStatus(Integer[] ids,Integer status) {
        if(ids==null||ids.length==0){
            throw new BusinessException("id参数不能为空");
        }
        Question question = new Question();
        question.setPublicStatus(status);
        questionMapper.updateById(question);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", ids);
        int rows = questionMapper.update(question, queryWrapper);
        if(rows!=ids.length){
            log.error("服务器出错，修改问题公开状态失败！");
            throw new BusinessException("服务器开小差了，操作失败！");
        }
    }

    @Override
    public PageInfo<Question> findMyUnAnwerQuestion(Integer pageNum, Integer pageSize) {
        return getQuestionPageInfo(pageNum, pageSize, 0);
    }

    private PageInfo<Question> getQuestionPageInfo(Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.findQuestionByUserIdAndStatus(getUseId(), status);
        return new PageInfo<>(questions);
    }

    @Override
    public PageInfo<Question> findMyUnSolveQuestion(Integer pageNum, Integer pageSize) {
        return getQuestionPageInfo(pageNum, pageSize, 1);
    }

    @Override
    public PageInfo<Question> findMySolvedQuestion(Integer pageNum, Integer pageSize) {
        return getQuestionPageInfo(pageNum, pageSize, 2);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setQuestionSolved(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setStatus(2);
        return questionMapper.updateById(question) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setQuestionSolved(Integer[] ids) {
        Question question = new Question();
        question.setStatus(2);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", ids);
        return questionMapper.update(question, queryWrapper) >= 1;
    }

    /**
     * 把问题转发给其他老师
     *
     * @param teacherIds
     * @param questionIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferToTeacher(Integer[] teacherIds, Integer[] questionIds) {
        if (teacherIds.length == 0 || questionIds.length == 0) {
            throw new BusinessException("所选的老师和问题都不能为空！");
        }
        Date now = new Date();
        for (Integer teacherId : teacherIds) {
            for (Integer questionId : questionIds) {
                TeacherQuestion teacherQuestion = new TeacherQuestion(teacherId, questionId, now);
                //先查找该TeacherQuestion是否已存在，如果存在则不做处理
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_id", teacherId);
                queryWrapper.eq("question_id", questionId);
                TeacherQuestion t = teacherQuestionMapper.selectOne(queryWrapper);
                //不存在则添加，防止重复
                if (t == null) {
                    int n = teacherQuestionMapper.insert(teacherQuestion);
                    if (n != 1) {
                        throw new BusinessException("服务繁忙，请稍后再试！");
                    }
                }

            }
        }
        return true;
    }

    @Override
    public QuestionVO getQuestionParamById(Integer id) {
        QueryWrapper questionQuery = new QueryWrapper();
        questionQuery.eq("delete_status", false);
        questionQuery.eq("id", id);
        Question question = questionMapper.selectOne(questionQuery);
        if (question == null) {
            return null;
        }
        //查找出该问题的所有老师
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("question_id", id);
        List<TeacherQuestion> teacherQuestionList = teacherQuestionMapper.selectList(queryWrapper);
        List<String> teacherNames = teacherQuestionList.stream().map(userQuestion -> {
            User user = userMapper.selectById(userQuestion.getUserId());
            if (user != null) {
                return user.getNickname();
            }
            return null;
        }).collect(Collectors.toList());
        //查找出该问题的所有标签
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("question_id", id);
        List<QuestionTag> questionTags = questionTagMapper.selectList(queryWrapper2);
        List<String> tagNames = questionTags.stream().map(questionTag -> {
            Tag tag = tagMapper.selectById(questionTag.getTagId());
            if (tag != null) {
                return tag.getName();
            }
            return null;
        }).collect(Collectors.toList());


        QuestionVO questionVO = new QuestionVO(question.getTitle(), tagNames, teacherNames, question.getContent());
        return questionVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateQuestion(QuestionUpdateParam q) {
        //保存问题
        Question question = questionMapper.selectById(q.getId());
        question.setTitle(q.getTitle());
        question.setContent(q.getContent());
        int n = questionMapper.updateById(question);
        if (n != 1) {
            throw new BusinessException("服务器繁忙，请稍后重试");
        }
        //先删除问题和标签的关系记录
        QueryWrapper deleteQuestionTagQuery = new QueryWrapper();
        deleteQuestionTagQuery.eq("question_id", q.getId());
        questionTagMapper.delete(deleteQuestionTagQuery);
        //再保存问题标签
        String[] tagNames = q.getTagNames();
        List<Tag> tagList = new ArrayList<>();
        for (String tagName : tagNames) {
            QueryWrapper tagQuery = new QueryWrapper();
            tagQuery.eq("name", tagName);
            Tag tag = tagMapper.selectOne(tagQuery);
            if (tag == null) {
                throw new BusinessException(tagName + "标签已被删除,请重新选择!");
            }
            tagList.add(tag);
            QuestionTag questionTag = new QuestionTag(question.getId(), tag.getId());
            int m = questionTagMapper.insert(questionTag);
            if (m != 1) {
                throw new BusinessException("服务器繁忙，请稍后重试");
            }
        }
        //把之前的老师和问题的关系记录删除
        QueryWrapper deleteUserQuestionQuery = new QueryWrapper();
        deleteUserQuestionQuery.eq("question_id", q.getId());
        teacherQuestionMapper.delete(deleteUserQuestionQuery);
        //保存老师和问题的关系
        String[] teacherNames = q.getTeacherNames();
        for (String teaherName : teacherNames) {
            QueryWrapper teacherQuery = new QueryWrapper();
            teacherQuery.eq("nickname", teaherName);
            User teacher = userMapper.selectOne(teacherQuery);
            if (teacher == null) {
                throw new BusinessException(teaherName + ":该老师名称已被删除,请重新选择!");
            }
            TeacherQuestion teacherQuestion = new TeacherQuestion(teacher.getId(), question.getId(), new Date());
            int i = teacherQuestionMapper.insert(teacherQuestion);
            if (i != 1) {
                throw new BusinessException("服务器繁忙，请稍后重试");
            }


        }
        EsQuestion esQuestion = new EsQuestion(question.getId(), question.getTitle(), question.getContent(),
                question.getUserNickName(), question.getUserId(), question.getCreatetime(), question.getStatus(), question.getPageViews(),
                question.getPublicStatus(), Arrays.asList(q.getTagNames()), tagList);

        kafkaTemplate.send(KafkaTopic.PROTAL_UPDATE_QUESTION, gson.toJson(esQuestion));

        return true;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Integer id) {
        //删除问题
        Question question = new Question();
        question.setId(id);
        question.setDeleteStatus(true);
        question.setModifytime(new Date());

        return questionMapper.updateById(question) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean collectQuestion(Integer id) {
        //先判断该问题是否已收藏
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", getUseId());
        queryWrapper.eq("question_id", id);
        List<UserCollect> userCollect2 = userCollectMapper.selectList(queryWrapper);
        //只有该收藏不存在时才处理
        if (!CollectionUtils.isEmpty(userCollect2)) {
            throw new BusinessException("该问题已收藏");
        }
        UserCollect userCollect = new UserCollect(getUseId(), id, new Date());
        return userCollectMapper.insert(userCollect) == 1;

    }

    /**
     * 收藏提问
     *
     * @param id
     * @return
     */
    @Override
    public Boolean checkCollectStatus(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("question_id", id);
        queryWrapper.eq("user_id", getUseId());
        List<UserCollect> userCollects = userCollectMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(userCollects)) {
            return true;
        }
        return false;
    }

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelCollectQuestion(Integer id) {
        //用kafka消息队列
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", getUseId());
        queryWrapper.eq("question_id", id);
        //删除该收藏
        return userCollectMapper.delete(queryWrapper) == 1;


    }

    @Override
    public List<Answer> getQuestionAnswerById(Integer questionId) {
        List<Answer> answers = getAnswers(questionId);
        return answers;
    }

    private List<Answer> getAnswers(Integer questionId) {
        QueryWrapper<Answer> query = new QueryWrapper<>();
        query.eq("quest_id", questionId);
        query.orderBy(true, false, "createtime");
        List<Answer> answers = answerMapper.selectList(query);
        //设置评论
        if (!CollectionUtils.isEmpty(answers)) {
            for (Answer answer : answers) {
                List<Comment> commentList = commentMapper.findByAnswerId(answer.getId());
                answer.setCommentList(commentList);
            }
        }
        return answers;
    }


}
