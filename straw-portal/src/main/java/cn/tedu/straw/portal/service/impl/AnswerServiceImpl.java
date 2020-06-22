package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IAnswerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Service
public class AnswerServiceImpl extends BaseServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Integer answerId) {
        //删除回答
        Answer answer = answerMapper.selectById(answerId);
        if (answer == null) {
            throw new BusinessException("删除失败，没有该记录！");
        }
        int m = answerMapper.deleteById(answerId);
        if (m != 1) {
            throw new BusinessException("服务器开小差了，删除失败！");
        }
        //删除该答案的评论
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("answer_id", answerId);
        //删除之前先查看一下有多少条记录
        Integer commentCount = commentMapper.selectCount(queryWrapper);
        int row = commentMapper.delete(queryWrapper);
        if (commentCount != row) {
            throw new BusinessException("服务器开小差了，删除失败！");
        }

        //如果答案全部删除了，需要对该提问的状态进行修改，修改成未回复状态
        QueryWrapper query = new QueryWrapper();
        query.eq("quest_id", answer.getQuestId());
        Integer count = answerMapper.selectCount(query);
        if (count == 0) {//如果一个回答都没有了，那么把问题设置为未回复
            Question question = new Question();
            question.setId(answer.getQuestId());
            question.setStatus(0);
            int row2 = questionMapper.updateById(question);
            if (row2 != 1) {
                throw new BusinessException("服务器开小差了");
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean acceptAnswer(Integer answerId, Integer questionId) {
        if(answerId==null){
            throw new BusinessException("回答id不能为空！");
        }
        if(questionId==null){
            throw new BusinessException("问题id不能为空");
        }
        if(answerMapper.selectById(answerId)==null){
            throw new BusinessException("不存在该回答");
        }
        if(questionMapper.selectById(questionId)==null){
            throw new BusinessException("不存在该提问！");
        }
        Answer answer = new Answer();
        answer.setId(answerId);
        answer.setAcceptStatus(true);
        int row1 = answerMapper.updateById(answer);
        if(row1!=1){
            throw new BusinessException("服务器开小差了");
        }
        //问题设置为已解决
        Question question = new Question();
        question.setId(questionId);
        question.setStatus(2);
        int row2 = questionMapper.updateById(question);
        if (row2 != 1) {
            throw new BusinessException("服务器开小差了");
        }
        return true;
    }
}
