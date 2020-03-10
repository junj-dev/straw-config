package cn.tedu.straw.portal.test.service;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * {@link cn.tedu.straw.portal.service.IQuestionService}
 *
 * @Description: 问题业务类测试
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/10$ 10:53$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestQuestionService {

    @Resource
    private IQuestionService questionService;

    /**
     *
     * @see  IQuestionService#selectPage(Integer, Integer)
     */
    @Test
    public void  testSelectPage(){
        PageInfo<Question> pageInfo = questionService.selectPage(0, 10);
        System.out.println(pageInfo);
    }

}
