package cn.tedu.straw.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: mybatis配置类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/2/11$ 22:01$
 * @Version: 1.0
 */
@EnableTransactionManagement
@MapperScan(value = {
        "cn.tedu.straw.search.mapper"
})
@Configuration
public class MybatisConfig {



}
