package cn.tedu.straw.portal.kafka;

import cn.tedu.straw.common.constant.KafkaTopic;
import cn.tedu.straw.portal.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description: 系统异常信息消费者
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/22$ 16:51$
 * @Version: 1.0
 */
@Component
@Slf4j
public class RuntimeExceptionConsumer {

    @Resource
    private IMailService mailService;

    @Value("${spring.mail.to.admin}")
    private String toAdmin;


    @KafkaListener(topics = {KafkaTopic.RUNTIME_EXCEPTION})
    public void listen(ConsumerRecord<String,String> record){

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = (String) kafkaMessage.get();
            //把错误日志发送到管理员邮箱
            mailService.sendSimpleMail(toAdmin,"系统报错",message);
        }
    }
}
