package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.UserCollectMapper;
import cn.tedu.straw.portal.model.Question;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 用户收藏
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/8$ 21:32$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestUserCollectMapper {
    @Resource
    private UserCollectMapper userCollectMapper;

    /**
     * @see cn.tedu.straw.portal.mapper.UserCollectMapper#findUserCollectQuestionByUserId(Integer)
     */
    @Test
    public void findUserCollectQuestionByUserId(){
        PageHelper.startPage(0,2);
        List<Question> questions = userCollectMapper.findUserCollectQuestionByUserId(11);
        PageInfo<Question> pageInfo=new PageInfo(questions);
        System.out.println(pageInfo);
    }
}
