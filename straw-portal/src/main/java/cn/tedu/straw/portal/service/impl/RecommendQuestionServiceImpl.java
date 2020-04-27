package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.constant.QuestionPublicStatus;
import cn.tedu.straw.common.constant.RedisKey;
import cn.tedu.straw.common.constant.RedisKeyPrefix;
import cn.tedu.straw.common.constant.RoleName;
import cn.tedu.straw.common.util.NumberUtils;
import cn.tedu.straw.portal.base.BaseService;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionTag;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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

    @Override
    public List<Question> getHotspotQuestion() {
        Set<String> questionIds = redisTemplate.opsForZSet().reverseRange(RedisKey.QUESTION_SCORE, 0, -1);
        if(CollectionUtils.isEmpty(questionIds)){return new ArrayList<Question>();}
        List<Question> questions=new ArrayList<>();
       //取排名的前十

        if(getUserRoleNames().contains(RoleName.TEACHER)){ //是否拥有老师角色
             List<Question>  quests= questionMapper.selectBatchIds(questionIds);
             questions=quests.stream().limit(10).collect(Collectors.toList());

        }else {
            List<Question>  quests= questionMapper.selectBatchIds(questionIds);
            questions= quests.stream().filter(q->{
                return questionIsPublic(q);
            }).limit(10).collect(Collectors.toList());
        }

        return questions;
    }

    private boolean questionIsPublic(Question q) {
        //如果没有老师角色的用户只能查看自己提出的问题
        if(q.getUserId().intValue()==getUseId().intValue()){
            return true;
        }
        //或者是公开的问题
        if(q.getPublicStatus()== QuestionPublicStatus.PUBLIC.getStatus()){
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
        //如果一个问题有多个标签，则在每个问题中提取前(10/标签数)
        Integer n=10/tagIds.size();
        Set<String> allQuestionIds=new HashSet<>();
        for(Integer tagId:tagIds){
            Set<String> questionIds = redisTemplate.opsForZSet().reverseRange(RedisKeyPrefix.TAG_QUEATION_SCORE + tagId, 0, n-1);
            allQuestionIds.addAll(questionIds);
        }
        List<Question> questions = questionMapper.selectBatchIds(allQuestionIds);
       List<Question> result= questions.stream().filter(question ->{return questionIsPublic(question);}).collect(Collectors.toList());
        return result;
    }
}
