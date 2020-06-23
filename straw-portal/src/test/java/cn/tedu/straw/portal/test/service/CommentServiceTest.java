package cn.tedu.straw.portal.test.service;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.service.ICommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description: 评论业务测试类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/6/22$ 17:00$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @Resource
    private ICommentService commentService;

    @Test
    public void create(){

    }
}
