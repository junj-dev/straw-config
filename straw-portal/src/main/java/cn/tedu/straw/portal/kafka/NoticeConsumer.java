package cn.tedu.straw.portal.kafka;

import cn.tedu.straw.portal.mapper.NoticeMapper;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.portal.model.Notice;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description: 消息消费者
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/03$ 20:15$
 * @Version: 1.0
 */
@Component
@Slf4j
public class NoticeConsumer {


    @Resource
    private NoticeMapper noticeMapper;
    private Gson gson=new Gson();

    /**
     * 创建问题
     * @param record
     */
    @KafkaListener(topics = {"straw-portal-notice"})
    public void listen(ConsumerRecord<String, String> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String noticeStr=(String) kafkaMessage.get();
            Notice notice=gson.fromJson(noticeStr, Notice.class);
            int k = noticeMapper.insert(notice);
            if(k!=1){
                log.error(notice+":保存出错");
            }
        }
    }








}
