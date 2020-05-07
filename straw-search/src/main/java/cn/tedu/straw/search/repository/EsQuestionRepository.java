package cn.tedu.straw.search.repository;

import cn.tedu.straw.portal.model.EsQuestion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:42$
 * @Version: 1.0
 */
public interface EsQuestionRepository extends ElasticsearchRepository<EsQuestion,Integer> {
   // List<EsQuestion> findByTitleOrContentOrTagsOrAnswersOrderByCreatetimeDesc(String title,String content,String tags,String answer);

}
