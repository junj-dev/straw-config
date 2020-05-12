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

/**
 * <p>
 *  服务实现类
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
        if(answer==null){throw  new BusinessException("没有该记录");
        }
        int m = answerMapper.deleteById(answerId);
        //删除评论
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("answer_id",answerId);
        int n = commentMapper.delete(queryWrapper);
        if(m==1&&n>=0){
            //如果答案全部删除了，需要对该提问的状态进行修改，修改成未回复状态
            QueryWrapper query=new QueryWrapper();
            query.eq("quest_id",answer.getQuestId());
            Integer count = answerMapper.selectCount(query);
            if(count==0){//如果一个回答都没有了，那么把问题设置为未回复
                Question question=new Question();
                question.setId(answer.getQuestId());
                question.setStatus(0);
                questionMapper.updateById(question);
            }
            return true;
        }
        return false;
    }
}
