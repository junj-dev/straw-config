package cn.tedu.straw.search.service.impl;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.constant.EsIndexName;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.mapper.EsQuestionMapper;
import cn.tedu.straw.search.repository.EsQuestionRepository;
import cn.tedu.straw.search.service.IEsQuestionService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 问题业务实现类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:51$
 * @Version: 1.0
 */
@Service
@Slf4j
public class EsQuestionServiceImpl implements IEsQuestionService {

    @Resource
    private EsQuestionMapper questionMapper;
    @Resource
    private EsQuestionRepository questionRepository;
    @Autowired
    private RestHighLevelClient restHighLevelClient;



    @Override
    public int importAllQuestionFromDB() {
        List<EsQuestion> questions = questionMapper.selectQuestionWithTags();
        if (CollectionUtils.isEmpty(questions)) {
            return 0;
        }
        //把html标签去掉
        for (EsQuestion question : questions) {
            //使用Jsoup去掉html标签
            Document document = Jsoup.parse(question.getContent());
            question.setContent(document.text());
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

//    @Override
//    public Page<EsQuestion> search(String keyword, Integer pageNum, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        //指定索引
//        nativeSearchQueryBuilder.withIndices("straw");
//        //指定type
//        nativeSearchQueryBuilder.withTypes("question");
//        //分页
//        nativeSearchQueryBuilder.withPageable(pageable);
//        //搜索
//        if (StringUtils.isEmpty(keyword)) {
//            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
//        } else {
//            //按标题，内容，标签,查询
//           BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//           boolQueryBuilder.should(QueryBuilders.multiMatchQuery(keyword,"title","content","tags"));
//           nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
//        }
//
//
//        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
//        Page<EsQuestion> search = questionRepository.search(searchQuery);
//        return  search;
//    }
    @Override
    public CommonPage<EsQuestion> search(String keyword, Integer pageNum, Integer pageSize) {
        SearchRequest searchRequest=new SearchRequest(EsIndexName.STRAW_QUESTION);
        //创建查询构建器
        SearchSourceBuilder  sourceBuilder=new SearchSourceBuilder();
        //分页
        int from = (pageNum-1)*pageSize;
        sourceBuilder.from(from);//当前页
        sourceBuilder.size(pageSize);//每页条数
        //全文搜索
        if(StringUtils.isEmpty(keyword)){//关键字为空则查出全部数据
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            sourceBuilder.query(matchAllQueryBuilder);
        }else {
            //按标题和内容查询
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "content");
            sourceBuilder.query(multiMatchQueryBuilder);
            //高亮显示
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            HighlightBuilder.Field highlightTitle =
                    new HighlightBuilder.Field("title");
            HighlightBuilder.Field highlightContent =
                    new HighlightBuilder.Field("content");
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            highlightBuilder.field(highlightTitle);
            highlightBuilder.field(highlightContent);
            sourceBuilder.highlighter(highlightBuilder);

        }
        CommonPage<EsQuestion> commonPage=new CommonPage<>();
        //搜索请求对象中设置搜索源
        searchRequest.source(sourceBuilder);
        //执行搜索
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<EsQuestion> esQuestionList = getEsQuestions(response);
            //封装commomPage
            restCommonPage(pageNum, pageSize, commonPage, response, esQuestionList);

        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        return commonPage;
    }


    @Override
    public CommonPage<EsQuestion> searchByUserIdAndPublicStatus(String keyword, Integer pageNum, Integer pageSize, Integer userId, Integer publicStatus) {
        SearchRequest searchRequest=new SearchRequest(EsIndexName.STRAW_QUESTION);

        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //分页
        int from = (pageNum-1)*pageSize;
        sourceBuilder.from(from);//当前页
        sourceBuilder.size(pageSize);//每页条数
        //过滤其它的数据，只查本用户提出的问题或者公开的问题
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQueryBuilder filterBoolQueryBuilder = QueryBuilders.boolQuery();
        //过滤，本人提出的问题
        filterBoolQueryBuilder.should(QueryBuilders.termQuery("userId",userId));
        //公开的问题
        filterBoolQueryBuilder.should(QueryBuilders.termQuery("publicStatus",publicStatus));
        boolQueryBuilder.filter(filterBoolQueryBuilder);
        //全文搜索
        if (StringUtils.isEmpty(keyword)) {
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            sourceBuilder.query(matchAllQueryBuilder);
        }else {
            //按标题和内容查询
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "content");
            sourceBuilder.query(multiMatchQueryBuilder);
            //高亮显示
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            HighlightBuilder.Field highlightTitle =
                    new HighlightBuilder.Field("title");
            HighlightBuilder.Field highlightContent =
                    new HighlightBuilder.Field("content");
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            highlightBuilder.field(highlightTitle);
            highlightBuilder.field(highlightContent);
            sourceBuilder.highlighter(highlightBuilder);

        }
        CommonPage<EsQuestion> commonPage=new CommonPage<>();
        //搜索请求对象中设置搜索源
        searchRequest.source(sourceBuilder);
        //执行搜索
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            List<EsQuestion> esQuestionList = getEsQuestions(response);
            //封装commomPage

            restCommonPage(pageNum, pageSize, commonPage, response, esQuestionList);

        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        return commonPage;
    }

    /**
     * 解析结果
     * @param response
     * @return
     */
    private List<EsQuestion> getEsQuestions(SearchResponse response) {
        List<EsQuestion> esQuestionList = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            //获取结果
            String sourceAsString = hit.getSourceAsString();
            EsQuestion esQuestion = JSON.parseObject(sourceAsString, EsQuestion.class);
            //获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightTitle = highlightFields.get("title");

            if (highlightTitle != null) {//如果标题高亮字段不为空，则把结果的title替换成高亮的title
                StringBuilder newTitle = new StringBuilder();
                //获取高亮字段的内容拼接起来
                Text[] texts = highlightTitle.fragments();
                for (Text text : texts) {
                    newTitle.append(text);
                }
                //用高亮字段的title覆盖esQuestion里的title字段的内容
                esQuestion.setTitle(newTitle.toString());

            }
            HighlightField highlightContent = highlightFields.get("content");
            if (highlightContent != null) {
                StringBuilder newContent = new StringBuilder();
                //获取高亮字段的内容拼接起来
                Text[] texts = highlightContent.fragments();
                for (Text text : texts) {
                    newContent.append(text);
                }
                esQuestion.setContent(newContent.toString());
            }
            esQuestionList.add(esQuestion);


        }
        return esQuestionList;
    }

    private void restCommonPage(Integer pageNum, Integer pageSize, CommonPage<EsQuestion> commonPage, SearchResponse response, List<EsQuestion> esQuestionList) {
        commonPage.setList(esQuestionList);
        commonPage.setPageNum(pageNum);
        commonPage.setPageSize(pageSize);
        long total = response.getHits().getTotalHits().value;
        commonPage.setTotal(total);
        int totalPage = (int) (total % pageSize == 0 ? total / pageSize : (total / pageSize + 1));
        commonPage.setTotalPage(totalPage);
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


