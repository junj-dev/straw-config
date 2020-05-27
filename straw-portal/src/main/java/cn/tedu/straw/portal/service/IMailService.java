package cn.tedu.straw.portal.service;


public interface IMailService {

    /**
     *发送简单格式的邮件
     * @param to 接收者邮箱地址
     * @param subject 主题
     * @param content 邮件内容
     */
   void sendSimpleMail(String to, String subject, String content);


}