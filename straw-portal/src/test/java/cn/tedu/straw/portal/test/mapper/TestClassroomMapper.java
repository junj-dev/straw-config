package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.model.Classroom;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link cn.tedu.straw.portal.mapper.ClassroomMapper}
 * @Description: ClassroomMapper测试类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/31$ 11:29$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestClassroomMapper {
    @Resource
    private ClassroomMapper classroomMapper;

    @Test
    public void test1(){
        List<Classroom> classrooms =classroomMapper.selectList(new QueryWrapper<>());
        classrooms.forEach(System.out::println);
    }
}
