package cn.tedu.straw.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description: 文件上传配置
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/27$ 0:15$
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "upload")
@Data
public class UploadFileConfig {
    private  String filePath;
}
