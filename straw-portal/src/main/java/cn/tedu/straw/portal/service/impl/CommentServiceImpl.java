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
import com.google.gson.Gson;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public boolean create(Integer answerId, String content, Integer questionId) {

        if(answerId==null||content==null){
            throw  new BusinessException("服务繁忙,请稍后再试！");
        }
        Comment comment=new Comment(getUseId(),answerId,content,new Date());
        int b = commentMapper.insert(comment);
        if(b!=1){ throw  new BusinessException("服务繁忙，请稍后再试！");}
        //生成消息通知
        Answer answer = answerMapper.selectById(answerId);
        if(answer==null){
            throw  new BusinessException("服务繁忙，请稍后再试！");
        }
        //添加评论的消息通知，2个人会收到消息通知，问题的提问者和问题的回答者
        if(answer.getUserId().intValue()!=getUseId().intValue()){ //如果该回答不是该用户的，才会生成消息
            Notice notice=new Notice(0,questionId,new Date(),answer.getUserId(),getUseId(),false);
            kafkaTemplate.send(KafkaTopic.PORTAL_NOTICE,gson.toJson(notice));
        }
        //
        Question question = questionMapper.selectById(questionId);
        if(question!=null&&question.getUserId().intValue()!=getUseId().intValue()){
            Notice notice=new Notice(2,questionId,new Date(),question.getUserId(),getUseId(),false);
            kafkaTemplate.send(KafkaTopic.PORTAL_NOTICE,gson.toJson(notice));
        }

        return true;
    }
}
