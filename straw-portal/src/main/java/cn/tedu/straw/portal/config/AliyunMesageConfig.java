package cn.tedu.straw.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description: 阿里云短信服务配置
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/28$ 1:34$
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "aliyun.message")
@PropertySource(value = {"classpath:aliyun-message.properties"}, encoding = "utf-8")
@Data
public class AliyunMesageConfig {

    private  String regionId;
    private  String accessKeyId;
    private  String secret;
    private  String SignName;
    //注册验证码
    private  String TemplateCode_register;
    //重置密码验证码
    private  String TemplateCode_resetPassword;
}
