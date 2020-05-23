package cn.tedu.straw.search.service.impl;

import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.mapper.EsQuestionMapper;
import cn.tedu.straw.search.repository.EsQuestionRepository;
import cn.tedu.straw.search.service.IEsQuestionService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 问题业务实现类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:51$
 * @Version: 1.0
 */
@Service
public class EsQuestionServiceImpl implements IEsQuestionService {

    @Resource
    private EsQuestionMapper questionMapper;
    @Resource
    private EsQuestionRepository questionRepository;

    @Override
    public int importAllQuestionFromDB() {
        List<EsQuestion> questions = questionMapper.selectQuestionWithTags();
        if (CollectionUtils.isEmpty(questions)) {
            return 0;
        }
        int result = 0;
        for (EsQuestion esQuestion : questions) {
            EsQuestion question = questionRepository.save(esQuestion);
            if (question != null) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Page<EsQuestion> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //指定索引
        nativeSearchQueryBuilder.withIndices("straw");
        //指定type
        nativeSearchQueryBuilder.withTypes("question");
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //搜索
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {

            //按标题，内容，标签,查询
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.should(QueryBuilders.multiMatchQuery(keyword,"title","content","tags"));
            nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        }
        //按时间排序
        SortBuilder sortBuilder1 = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);

        nativeSearchQueryBuilder.withSort(sortBuilder1);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        return questionRepository.search(searchQuery);
    }

    @Override
    public Page<EsQuestion> searchByUserIdAndPublicStatus(String keyword, Integer pageNum, Integer pageSize,Integer userId,Integer publicStatus) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //指定索引
        nativeSearchQueryBuilder.withIndices("straw");
        //指定type
        nativeSearchQueryBuilder.withTypes("question");
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        BoolQueryBuilder filterBoolQueryBuilder = QueryBuilders.boolQuery();
        //过滤，本人提出的问题
        filterBoolQueryBuilder.should(QueryBuilders.termQuery("userId",userId));
        //公开的问题
        filterBoolQueryBuilder.should(QueryBuilders.termQuery("publicStatus",publicStatus));
        nativeSearchQueryBuilder.withFilter(filterBoolQueryBuilder);
        //全文搜索
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }else {
            //按标题，内容，标签，答案查询
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.should(QueryBuilders.multiMatchQuery(keyword,"title","content","tags"));
            nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        }

        //按时间排序
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        nativeSearchQueryBuilder.withSort(sortBuilder);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        return questionRepository.search(searchQuery);
    }

    @Override
    @Transactional
    public boolean insert(EsQuestion question) {
        questionRepository.save(question);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteQuestionById(Integer id) {
        questionRepository.deleteById(id);
        return true;
    }
}


