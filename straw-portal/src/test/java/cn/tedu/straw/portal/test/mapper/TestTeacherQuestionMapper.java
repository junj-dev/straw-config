package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.TeacherQuestionMapper;
import cn.tedu.straw.portal.model.TeacherQuestion;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/3$ 10:00$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestTeacherQuestionMapper {
    @Resource
    private TeacherQuestionMapper teacherQuestionMapper;

    @Test
    public void selectOne(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("teacher_id",4);
        queryWrapper.eq("question_id",44);
        List<TeacherQuestion> teacherQuestions = teacherQuestionMapper.selectList(queryWrapper);
        System.out.println(teacherQuestions);
    }

}
