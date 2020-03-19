package cn.tedu.straw.search.test;

import cn.tedu.straw.search.EsApplication;
import cn.tedu.straw.search.model.EsQuestion;
import cn.tedu.straw.search.mapper.EsQuestionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link cn.tedu.straw.search.mapper.EsQuestionMapper}
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 11:04$
 * @Version: 1.0
 */
@SpringBootTest(classes = EsApplication.class)
@RunWith(SpringRunner.class)
public class TestEsQuestionMapper {
    @Resource
    private EsQuestionMapper esQuestionMapper;

    /**
     * @see EsQuestionMapper#selectQuestionWithTagsWithAnswer()
     */
    @Test
    public void testSelectQuestionWithTagsWithAnswer(){
        List<EsQuestion> esQuestions = esQuestionMapper.selectQuestionWithTagsWithAnswer();
        esQuestions.forEach(System.out::println);
    }

//    /**
//     * @see EsQuestionMapper#selectAnswersByQuestionId(Long)
//     */
//    @Test
//    public  void testSelectAnswersByQuestionId(){
//        List<String> esAnswers = esQuestionMapper.selectAnswersByQuestionId(13l);
//        esAnswers.forEach(System.out::println);
//    }
}
