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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AnswerServiceImpl extends BaseServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAnswerById(Integer answerId) {
        if(answerId==null){
            log.info("answerId参数不能为空！");
            throw new BusinessException("参数不能为空！");
        }
        //删除回答
        Answer answer = answerMapper.selectById(answerId);
        if (answer == null) {
            log.info("answerId为：{}的记录不存在");
            throw new BusinessException("删除失败，没有该记录！");
        }
        log.debug("删除回答：{}",answer);
        int m = answerMapper.deleteById(answerId);
        if (m != 1) {
            throw new BusinessException("服务器开小差了，删除失败！");
        }
        //删除该答案的评论
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("answer_id", answerId);
        //删除之前先查看一下有多少条记录
        Integer commentCount = commentMapper.selectCount(queryWrapper);
        log.debug("删除answerId为：{}的评论",answerId);
        int row = commentMapper.delete(queryWrapper);
        if (commentCount != row) {
            log.error("服务器出错，删除answerId为：{}的评论失败！",answerId);
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
                throw new BusinessException("服务器开小差了,请稍后再试！");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptAnswer(Integer answerId, Integer questionId) {
        if(answerId==null){
            log.info("回答id不能为空！");
            throw new BusinessException("answerId参数不能为空！");
        }
        if(questionId==null){
            log.info("questionId参数不能为空");
            throw new BusinessException("questionId参数不能为空");
        }
        if(answerMapper.selectById(answerId)==null){
            log.info("不存在该回答记录");
            throw new BusinessException("不存在该回答记录");
        }
        if(questionMapper.selectById(questionId)==null){
            log.info("不存在该提问记录！");
            throw new BusinessException("不存在该提问记录！");
        }
        Answer answer = new Answer();
        answer.setId(answerId);
        answer.setAcceptStatus(true);
        log.debug("采纳答案，修改答案的状态为已采纳");
        int row1 = answerMapper.updateById(answer);
        if(row1!=1){
            log.error("服务器出错,修改answerId为:{}的答案状态失败",answerId);
            throw new BusinessException("服务器开小差了");
        }
        //问题设置为已解决
        Question question = new Question();
        question.setId(questionId);
        question.setStatus(2);
        log.debug("questionId为：{}的问题设置为已解决",questionId);
        int row2 = questionMapper.updateById(question);
        if (row2 != 1) {
            log.error("服务器出错,questionId为：{}的问题设置失败",questionId);
            throw new BusinessException("服务器开小差了");
        }
    }
}
