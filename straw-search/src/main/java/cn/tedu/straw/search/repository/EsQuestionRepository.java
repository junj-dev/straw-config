package cn.tedu.straw.search.repository;

import cn.tedu.straw.search.model.EsQuestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:42$
 * @Version: 1.0
 */
public interface EsQuestionRepository extends ElasticsearchRepository<EsQuestion,Long> {
}
