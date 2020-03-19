package cn.tedu.straw.search.test;

import cn.tedu.straw.search.EsApplication;
import cn.tedu.straw.search.model.EsQuestion;
import cn.tedu.straw.search.service.IEsQuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/18$ 17:55$
 * @Version: 1.0
 */
@SpringBootTest(classes = EsApplication.class)
@RunWith(SpringRunner.class)
public class TestEsQuestionService {

    @Resource
    private IEsQuestionService questionService;

    /**
     *
     * @see IEsQuestionService#searchByUserIdAndPublicStatus(String, Integer, Integer, Integer, Integer)
     */
    @Test
    public  void testSearchByUserIdAndPublicStatus(){
        Page<EsQuestion> questions = questionService.searchByUserIdAndPublicStatus("", 1, 5, 11, 1);
        questions.forEach(System.out::println);

    }

    @Test
    public void testSearch(){
        Page<EsQuestion> questions = questionService.search("", 1, 5);
        questions.forEach(System.out::println);
    }
}
