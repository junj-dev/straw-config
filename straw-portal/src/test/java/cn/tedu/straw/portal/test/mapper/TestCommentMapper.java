package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: CommentMapper测试
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/26$ 14:26$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestCommentMapper {
    @Resource
    private CommentMapper commentMapper;
    /**
     * @see cn.tedu.straw.portal.mapper.CommentMapper#findByAnswerId(Integer)
     */
    @Test
    public void findByAnswerId(){
        List<Comment> comment = commentMapper.findByAnswerId(31);
        System.out.println(comment);
    }
}
