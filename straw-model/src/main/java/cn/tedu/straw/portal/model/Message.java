package cn.tedu.straw.portal.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description: kafka发送的消息
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/18$ 1:10$
 * @Version: 1.0
 */
@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳
}
