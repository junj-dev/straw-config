package cn.tedu.straw.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: 启动类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 11:04$
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class,args);
    }
}
