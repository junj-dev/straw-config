package cn.tedu.straw.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * @Description: Es客户端配置
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/26$ 14:55$
 * @Version: 1.0
 */
@Configuration
public class ElasticSearchClientConfig {


    /**
     * 高级API
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        return client;

    }

}
