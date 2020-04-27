package cn.tedu.straw.portal.domian.vo;

import lombok.Data;

/**
 * @Description: 用户信息
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/10$ 22:47$
 * @Version: 1.0
 */
@Data
public class MyInfo {
    //金币数量
    private  Integer goldCount;
    //回答问题的数量
    private  Integer answerCount;
    //提问题的数量
    private  Integer questionCount;
    //任务的数量
    private  Integer taskCount;
}