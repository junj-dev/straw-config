package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
     *
     * @see  QuestionMapper#selectQuestionWithTags(Integer, String)
     */
    @Test
    public void testSelectQuestion(){
        List<Question> questionList = questionMapper.selectQuestionWithTags(null,null);
        questionList.forEach(System.out::println);
    }
}
