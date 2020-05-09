package cn.tedu.straw.portal.kafka;

import cn.tedu.straw.common.constant.KafkaTopic;
import cn.tedu.straw.common.constant.RedisKey;
import cn.tedu.straw.common.constant.RedisKeyPrefix;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.model.Message;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 页面点击消费者
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/18$ 1:15$
 * @Version: 1.0
 */
@Component
@Slf4j
public class PageViewConsumer {


    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private QuestionTagMapper questionTagMapper;


    /**
     * kafka消费信息，记录页面的访问量
     * @param record
     */
    @KafkaListener(topics = {KafkaTopic.PORTAL_PAGEVIEW})
    public void listen(ConsumerRecord<String, String> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            String questionId = (String) kafkaMessage.get();
            Question question = questionMapper.selectById(questionId);
            question.setPageViews(question.getPageViews() + 1);
            questionMapper.updateById(question);
            //用一个有序集合zset保存问题的分数，热门问题从该有序集合里取出分数排名前10的问题
            //计算分值，分值由点击量和回答数构成。目前的公式为：socre=pageView*2+answer*8 一个访问量占2分，一个回答占8分，
            //后期会根据具体情况调整比重
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("quest_id",questionId);
            Integer count = answerMapper.selectCount(queryWrapper);
            double score=question.getPageViews()*2+count*8;
            redisTemplate.opsForZSet().add(RedisKey.QUESTION_SCORE, questionId, score);

            //按照标签分类,每个标签保存一个zset有序集合，集合的成员按照分数排名
            QueryWrapper queryWrapper2=new QueryWrapper();
            queryWrapper2.eq("question_id",questionId);
            List<QuestionTag> qusetionTags = questionTagMapper.selectList(queryWrapper2);
            qusetionTags
                   .stream()
                   .map(QuestionTag::getTagId)
                   .forEach(tagId->{
                       //一个标签维护一个问题的有序集合,在其它地方可以通过标签id获取拥有该标签的排名列表
                       redisTemplate.opsForZSet().add(RedisKeyPrefix.TAG_QUEATION_SCORE+tagId,questionId,score);
                   });

        }
    }








}
