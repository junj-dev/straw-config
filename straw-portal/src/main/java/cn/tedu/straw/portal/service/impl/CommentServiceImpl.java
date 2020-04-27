package cn.tedu.straw.portal.service.impl;

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
    private NoticeMapper noticeMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Override
    @Transactional
    public boolean create(Integer answerId, String content, Integer questionId) {
        Comment comment=new Comment();
        if(answerId==null||content==null){
            throw  new BusinessException("服务繁忙,请稍后再试！");
        }
        comment.setAnswerId(answerId);
        comment.setContent(content);
        comment.setUserId(getUseId());
        comment.setCreatetime(new Date());

        //TODO 下一步采用kafka消息队列，暂时直接存数据库
        //生成消息通知
        Answer answer = answerMapper.selectById(answerId);
        if(answer==null){throw  new BusinessException("服务繁忙，请稍后再试！");}
        Notice notice=new Notice();
        notice.setCreatetime(new Date());
        notice.setQuestionId(questionId);
        notice.setType(0);//1代表回复问题,0代表评论
        notice.setUserId(answer.getUserId());//收到消息的用户
        notice.setReplyUserId(getUseId());//回复者
        int a = noticeMapper.insert(notice);
        int b = commentMapper.insert(comment);
        if(a!=1||b!=1){
           throw  new BusinessException("服务繁忙，请稍后再试！");
        }
        return true;
    }
}
