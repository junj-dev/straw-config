package cn.tedu.straw.portal.kafka;

import cn.tedu.straw.common.constant.KafkaTopic;
import cn.tedu.straw.common.util.ExceptionUtil;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.portal.service.IMailService;
import cn.tedu.straw.portal.service.IMessageService;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description: 创建或更新问题消费者
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/03$ 20:15$
 * @Version: 1.0
 */
@Component
@Slf4j
public class CreateOrUpdateQuestionConsumer {


    @Resource
    private EsQuestionServiceApi esQuestionServiceApi;
    private Gson gson=new Gson();
    @Resource
    private IMailService mailService;

    @Value("${spring.mail.to.admin}")
    private String toAdmin;

    /**
     * 创建或修改问题
     * @param record
     */
    @KafkaListener(topics = {KafkaTopic.PORTAL_CREATE_QUESTION,KafkaTopic.PROTAL_UPDATE_QUESTION})
    public void listen(ConsumerRecord<String, String> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
          String esQuestionStr= (String) kafkaMessage.get();
          EsQuestion esQuestion= gson.fromJson(esQuestionStr,EsQuestion.class);
          try{
              boolean flag= esQuestionServiceApi.saveQuestion(esQuestion);
          }catch (Exception e){
              log.error(e.getMessage(),e);
              mailService.sendSimpleMail(toAdmin,"稻草系统出错信息", ExceptionUtil.getStackTraceInfo(e));
          }

        }
    }








}
