package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.enums.QuestionPublicStatusEnum;
import cn.tedu.straw.common.constant.RedisKey;
import cn.tedu.straw.common.constant.RedisKeyPrefix;
import cn.tedu.straw.common.constant.RoleName;
import cn.tedu.straw.portal.base.BaseService;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionTag;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 推荐问题业务实现类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/21$ 11:30$
 * @Version: 1.0
 */
@Service
public class RecommendQuestionServiceImpl extends BaseService implements IRecommendQuestionService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionTagMapper questionTagMapper;
    @Resource
    private AnswerMapper answerMapper;

    @Override
    public List<Question> getHotspotQuestion() {
        Set<String> questionIds = redisTemplate.opsForZSet().reverseRange(RedisKey.QUESTION_SCORE, 0, -1);
        if(CollectionUtils.isEmpty(questionIds)){return new ArrayList<Question>();}
        List<Question> questions=new ArrayList<>();
       //取排名的前十

        if(getUserRoleNames().contains(RoleName.TEACHER)){ //是否拥有老师角色
             List<Question>  quests= questionMapper.selectBatchIds(questionIds);
             questions=quests.stream().filter(question -> {return  question.getDeleteStatus()==false;}).limit(10).collect(Collectors.toList());

        }else {
            List<Question>  quests= questionMapper.selectBatchIds(questionIds);
            questions= quests.stream().filter(q->{
                //问题可见并且问题没有被删除
                return isQuestionCanView(q)&&!q.getDeleteStatus();
            }).limit(10).collect(Collectors.toList());
        }
        for (Question question:questions){
            //设置回答数量
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("quest_id",question.getId());
            Integer count = answerMapper.selectCount(queryWrapper);
            question.setAnswerCount(count);
        }

        return questions;
    }

    /**
     * 问题可见，包括自己提的问题和公开的问题
     * @param q
     * @return
     */
    private boolean isQuestionCanView(Question q) {
        //如果没有老师角色的用户只能查看自己提出的问题
        if(q.getUserId().intValue()==getUseId().intValue()){
            return true;
        }
        //或者是公开的问题
        if(q.getPublicStatus()== QuestionPublicStatusEnum.PUBLIC.getStatus()){
            return true;
        }
        //除此之外都不可见
        return false;
    }


    @Override
    public List<Question> getSimilarQuestion(Integer questionId) {
        //查出给定的问题的标签
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("question_id",questionId);
        List<QuestionTag> qusetionTags = questionTagMapper.selectList(queryWrapper);
        //获取该问题的标签
        List<Integer> tagIds = qusetionTags.stream().map(QuestionTag::getTagId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(tagIds)){return new ArrayList<Question>();}
        Set<String> allQuestionIds=new HashSet<>();
        for(Integer tagId:tagIds){
            Set<String> questionIds = redisTemplate.opsForZSet().reverseRange(RedisKeyPrefix.TAG_QUEATION_SCORE + tagId, 0, 9);
            allQuestionIds.addAll(questionIds);
        }
        List<Question> questions = questionMapper.selectBatchIds(allQuestionIds);
       List<Question> result= questions.stream().filter(question ->{return isQuestionCanView(question)&&!question.getDeleteStatus();}).collect(Collectors.toList());
       for(Question question:result){
           //设置回答数量
           QueryWrapper queryWrapper2=new QueryWrapper();
           queryWrapper2.eq("quest_id",question.getId());
           Integer count = answerMapper.selectCount(queryWrapper2);
           question.setAnswerCount(count);
       }
        return result;
    }
}
