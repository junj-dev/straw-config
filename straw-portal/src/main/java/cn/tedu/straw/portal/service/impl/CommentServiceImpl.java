package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.constant.KafkaTopic;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.mapper.NoticeMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.model.Notice;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-25
 */
@Service
@Slf4j
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson=new Gson();

    /**
     * 创建评论
     * @param answerId
     * @param content
     * @param questionId
     * @return
     */
    @Override
    @Transactional
    public boolean create(Integer answerId, String content, Integer questionId) {

        if(answerId==null){
            throw  new BusinessException("答案id不能为空！");
        }

        if(StringUtils.isEmpty(content)){
            throw  new BusinessException("内容不能为空！");
        }
        if(questionId==null){
            throw  new BusinessException("问题id不能为空！");
        }
        //判断是否存在该回答
        Answer answer = answerMapper.selectById(answerId);
        if(answer==null){
            throw  new BusinessException("参数错误，不存在该回答");
        }
        //判断是否存在该问题
        Question question = questionMapper.selectById(questionId);
        if(question==null){
            throw new BusinessException("参数错误，不存在该问题id");
        }

        Comment comment=new Comment(getUseId(),answerId,content,new Date());
        int b = commentMapper.insert(comment);
        if(b!=1){
            throw  new BusinessException("服务器开小差了，评论保存失败！");
        }
        //生成消息通知
        //添加评论的消息通知，2个人会收到消息通知，问题的提问者和问题的回答者
        if(answer.getUserId().intValue()!=getUseId().intValue()){ //如果该回答不是该用户的，才会生成消息
            Notice notice=new Notice(0,questionId,new Date(),answer.getUserId(),getUseId(),false);
            ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(KafkaTopic.PORTAL_NOTICE, gson.toJson(notice));
            //添加回调函数，检查发送状态
            checkKafkaSendStatus(notice, sendResult);
        }
        if(question.getUserId().intValue()!=getUseId().intValue()){
            Notice notice=new Notice(2,questionId,new Date(),question.getUserId(),getUseId(),false);
            ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(KafkaTopic.PORTAL_NOTICE, gson.toJson(notice));
            //添加回调函数，检查发送状态
            checkKafkaSendStatus(notice,sendResult);
        }

        return true;
    }

    /**
     * 添加回调函数，检查kafka发送状态
     * @param notice
     * @param sendResult
     */
    private void checkKafkaSendStatus(Notice notice, ListenableFuture<SendResult<String, String>> sendResult) {
        sendResult.addCallback(new SuccessCallback<SendResult<String, String>>() {
                             @Override
                             public void onSuccess(SendResult<String, String> result) {
                                     log.debug("发送通知到kafka:{}",notice);
                             }
                         },
                new FailureCallback() {

                    @Override
                    public void onFailure(Throwable ex) {
                        throw new BusinessException("服务开小差了！");
                    }
                });
    }

    /**
     * 修改评论
     * @param commentId
     * @param content
     * @return
     */
    @Override
    public boolean update(Integer commentId, String content) {
        if(commentId==null){
            throw new BusinessException("评论id参数不能为空");
        }
        Comment comment = commentMapper.selectById(commentId);
        if(comment==null){
            throw new BusinessException("参数错误，不存在该评论");
        }
        //只能修改自己提的评论内容
        if(comment.getUserId().intValue()==getUseId().intValue()){
            comment.setContent(content);
            return commentMapper.updateById(comment)==1;
        }
       return  true;
    }
}
