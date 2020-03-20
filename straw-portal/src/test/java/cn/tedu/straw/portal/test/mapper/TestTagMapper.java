package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/10$ 10:25$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestTagMapper {
    @Resource
    private TagMapper tagMapper;

    /**
     * @see  TagMapper#selectTagsByQuestionId(Integer)
     */
    @Test
    public void testSelectTagsByQuestionId(){

        List<Tag> tags = tagMapper.selectTagsByQuestionId(1);
        tags.forEach(System.out::println);

    }

}
