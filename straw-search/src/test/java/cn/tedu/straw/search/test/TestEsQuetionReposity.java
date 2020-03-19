package cn.tedu.straw.search.test;

import cn.tedu.straw.search.EsApplication;
import cn.tedu.straw.search.model.EsQuestion;
import cn.tedu.straw.search.repository.EsQuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/18$ 14:47$
 * @Version: 1.0
 */
@SpringBootTest(classes = EsApplication.class)
@RunWith(SpringRunner.class)
public class TestEsQuetionReposity {
    @Resource
    private EsQuestionRepository repository;

//    @Test
//    public void testFindByTitleOrContentOrTagNameOrAnswerContent(){
//        String keyword="java";
//        List<EsQuestion> questions = repository.findByTitleOrContentOrTagsOrAnswersOrderByCreatetimeDesc(keyword,keyword,keyword,keyword);
//        questions.forEach(System.out::println);
//
//    }
}
