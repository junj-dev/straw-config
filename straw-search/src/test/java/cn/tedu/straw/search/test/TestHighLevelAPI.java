package cn.tedu.straw.search.test;

import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.EsApplication;
import cn.tedu.straw.search.test.pojo.User;
import com.alibaba.fastjson.JSON;
import io.swagger.models.auth.In;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description: es7.6.2高级客户端测试 API
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/26$ 15:02$
 * @Version: 1.0
 */
@SpringBootTest(classes = EsApplication.class)
@RunWith(SpringRunner.class)
public class TestHighLevelAPI {
    @Autowired
    //如果不想用restHighLevelClient这么长的名字，可以用@Qualifier注解指定名字
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;


    /**
     * 索引的创建
     *
     * @throws IOException
     */
    @Test
    public void CreateIndex() throws IOException {
        //创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("straw-question");
        CreateIndexResponse res = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(res);
    }

    /**
     * 查看索引是否存在
     *
     * @throws IOException
     */
    @Test
    public void ExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("straw");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //删除索引
    @Test
    public void DeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("straw");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    //创建文档
    @Test
    public void createDoucument() throws IOException {
        EsQuestion esQuestion = new EsQuestion();
        esQuestion.setTitle("测试111");
        esQuestion.setContent("hhhhhhhhhhhh");
        esQuestion.setStatus(1);
        esQuestion.setPageViews(100);
        esQuestion.setTagNames(Arrays.asList("java基础", "php"));
        IndexRequest request = new IndexRequest("straw");
        request.timeout(TimeValue.timeValueSeconds(2));
        request.id("1");
        //把对象转成json字符串
        request.source(JSON.toJSONString(esQuestion), XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    //测试是否存在
    @Test
    public void isDocumentExist() throws IOException {
        GetRequest request = new GetRequest("straw", "1");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档
    @Test
    public void getDocument() throws IOException {
        GetRequest request = new GetRequest("straw", "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    //更新文档
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("straw", "1");
        request.timeout("2s");
        EsQuestion esQuestion = new EsQuestion();
        esQuestion.setTitle("稻草系统");
        request.doc(JSON.toJSONString(esQuestion), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除请求
    @Test
    public void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("straw", "1");
        request.timeout("2s");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //批量插入
    @Test
    public void bulkRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("20s");

        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "student1", 20));
        userList.add(new User(2, "stuent1", 22));
        userList.add(new User(3, "student3", 25));

        for (int i = 0; i < userList.size(); i++) {
            request.add(new IndexRequest("test")
                    .id("" + (i + 1))
                    .source(JSON.toJSONString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }


    //查询
    @Test
    public void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("straw");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "java");
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        //执行请求
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits()));
        //打印出来看看
        for(SearchHit documentFields:response.getHits().getHits()){
            System.out.println(documentFields.getSourceAsMap());
        }

    }

    //高亮搜索
    @Test
    public void highlightSearch() throws IOException {
        //构建请求
        SearchRequest searchRequest=new SearchRequest("straw");

        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //分页
        sourceBuilder.from(0);//第几页
        sourceBuilder.size(2);//每页多少条数据
        //精准搜索
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "什么");
        sourceBuilder.query(termQueryBuilder);
        //高亮显示
        HighlightBuilder highlightBuilder =new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //封装查询条件
        searchRequest.source(sourceBuilder);
        //执行搜索
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        //解析结果
        System.out.println("查询的条数："+response.getHits().getTotalHits().value);

        List<EsQuestion> result=new ArrayList<>();
        for (SearchHit documentFields : response.getHits().getHits()) {
            //解析高亮字段
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            String sourceAsString = documentFields.getSourceAsString();
            EsQuestion esQuestion = JSON.parseObject(sourceAsString, EsQuestion.class);
            //将原来的title字段的内容替换成高亮的title内容
            if(title!=null){
                Text[] fragments = title.fragments();
                StringBuilder hightlightTitle=new StringBuilder();
                for (Text text : fragments) {
                    hightlightTitle.append(text);
                }
                //将原来的title替换
               esQuestion.setTitle(hightlightTitle.toString());
            }
            result.add(esQuestion);
        }

        result.forEach(System.out::println);

    }
}