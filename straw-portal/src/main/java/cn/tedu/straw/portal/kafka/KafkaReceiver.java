package cn.tedu.straw.portal.kafka;

import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Message;
import cn.tedu.straw.portal.model.Question;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description: kafka消费信息
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/18$ 1:15$
 * @Version: 1.0
 */
@Component
@Slf4j
public class KafkaReceiver {

    private Gson gson = new GsonBuilder().create();
    @Resource
    private QuestionMapper questionMapper;


    @KafkaListener(topics = {"straw-portal-pageView"})
    public void listen(ConsumerRecord<String, String> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            String questionId = (String) kafkaMessage.get();
            log.info("----------------- record =" + record);
            log.info("------------------ questionId =" + questionId);
            Question question = questionMapper.selectById(questionId);
            question.setPageViews(question.getPageViews()+1);
            questionMapper.updateById(question);

        }

    }
}
