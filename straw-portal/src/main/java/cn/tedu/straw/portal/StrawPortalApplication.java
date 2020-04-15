package cn.tedu.straw.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: 启动类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/3$ 9:57$
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
//开启定时任务
@EnableScheduling
@EnableFeignClients(basePackages = "cn.tedu.straw.search.api")
public class StrawPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(StrawPortalApplication.class,args);
    }
}
