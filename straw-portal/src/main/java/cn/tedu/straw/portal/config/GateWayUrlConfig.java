package cn.tedu.straw.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 访问网关转发到此系统的url配置
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/22$ 17:03$
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "gateway")
@Data
public class GateWayUrlConfig {
    private String url;
}
