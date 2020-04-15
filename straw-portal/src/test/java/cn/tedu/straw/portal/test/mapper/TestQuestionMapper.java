package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.QuestionQueryParam;
import cn.tedu.straw.portal.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: QuestionMapper测试类
 * {@link cn.tedu.straw.portal.mapper.QuestionMapper}
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/10$ 10:07$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestQuestionMapper {
    @Resource
    private QuestionMapper questionMapper;




    /**
     * @see QuestionMapper#findQuestionByUserId(Integer)
     */
    @Test
    public  void findQuestionByUserId(){
        List<Question> questions = questionMapper.findQuestionByUserId(11);
        System.out.println(questions.size());
        questions.forEach(System.out::println);


    }

    /**
     * @see QuestionMapper#findQuestionByUserIdAndTagId(Integer, Integer)
     */
    @Test
    public void findQuestionByUserIdAndTagId(){
        List<Question> questions = questionMapper.findQuestionByUserIdAndTagId(11,15);
        System.out.println(questions.size());
        questions.forEach(System.out::println);
    }

    /**
     * @see QuestionMapper#findQuestionByTagId(Integer) 
     */
    @Test
    public void  findQuestionByTagId(){
        List<Question> questions = questionMapper.findQuestionByTagId(1);
        System.out.println(questions.size());
        questions.forEach(System.out::println);
        
    }

    /**
     * @see  QuestionMapper#findAllQuestion()
     */
    @Test
    public void findAllQuestion(){
        List<Question> allQuestion = questionMapper.findAllQuestion();
        System.out.println(allQuestion.size());
        allQuestion.forEach(System.out::println);
    }

    /**
     * @see QuestionMapper#findQuestionByUserIdOrPublicStatus(Integer, Integer)
     */
    @Test
    public  void findQuestionByUserIdOrPublicStatus(){
        List<Question> questions = questionMapper.findQuestionByUserIdOrPublicStatus(11, 1);
        System.out.println(questions.size());
        questions.forEach(System.out::println);
    }

    /**
     * @see QuestionMapper#findQuestionByCondition(QuestionQueryParam)
     */
    @Test
    public void findQuestionByCondition() throws ParseException {
        QuestionQueryParam condition=new QuestionQueryParam();
        condition.setPublicStatus(0);
        condition.setAnswerStatus(0);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // condition.setStartDate(sdf.parse("2020-03-01 00:00:00"));
        //condition.setEndDate(sdf.parse("2020-03-20 00:00:00"));
        condition.setTagId(1);
        List<Question> questions = questionMapper.findQuestionByCondition(condition);
        System.out.println(questions.size());
        questions.forEach(System.out::println);
    }

    /**
     * @see QuestionMapper#findQuestionByTagIdsAndStatus(List, Integer)
     */
    @Test
    public void findQuestionByTagIdsAndStatus(){
        List<Integer> tagIds=new ArrayList<>();
        tagIds.add(1);
        tagIds.add(2);

        List<Question> questions = questionMapper.findQuestionByTagIdsAndStatus(tagIds, 0);
        System.out.println(questions.size());
        questions.forEach(System.out::println);
    }

    /**
     * @see QuestionMapper#findQuestionByUserIdAndStatus(Integer, Integer)
     */
    @Test
    public  void findQuestionByUserIdAndStatus(){
        List<Question> questions = questionMapper.findQuestionByUserIdAndStatus(4, 1);
        questions.forEach(System.out::println);
    }


    /**
     * @see QuestionMapper#countTaskByUserId(Integer)
     */
    @Test
    public  void countTaskByUserId(){
        Integer taskCount = questionMapper.countTaskByUserId(3);
        System.out.println(taskCount);
    }
}
