package cn.tedu.straw.portal.kafka;

import cn.tedu.straw.common.constant.KafkaTopic;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description: 创建问题消费者
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/03$ 20:15$
 * @Version: 1.0
 */
@Component
@Slf4j
public class UpdateQuestionConsumer {


    @Resource
    private EsQuestionServiceApi esQuestionServiceApi;
    private Gson gson=new Gson();


    /**
     * 修改问题
     * @param record
     */
    @KafkaListener(topics = {KafkaTopic.PROTAL_UPDATE_QUESTION})
    public void listen(ConsumerRecord<String, String> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
          String esQuestionStr= (String) kafkaMessage.get();
          EsQuestion esQuestion= gson.fromJson(esQuestionStr,EsQuestion.class);
          //先把之前的记录删除
          esQuestionServiceApi.deleteQuestion(esQuestion.getId());
          esQuestionServiceApi.saveQuestion(esQuestion);

        }
    }








}
