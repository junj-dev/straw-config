package cn.tedu.straw.search.service.impl;

import cn.tedu.straw.search.mapper.EsQuestionMapper;
import cn.tedu.straw.search.model.EsQuestion;
import cn.tedu.straw.search.repository.EsQuestionRepository;
import cn.tedu.straw.search.service.IEsQuestionService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
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
        List<EsQuestion> questions = questionMapper.selectQuestionWithTagsWithAnswer();
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
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //搜索
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("title", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("content", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("tagName", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("answerContent", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));

            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);

        }

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        return questionRepository.search(searchQuery);
    }
}


