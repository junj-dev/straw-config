package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description: UserMapper测试类
 * {@link cn.tedu.straw.portal.mapper.UserMapper}
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 18:30$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestUserMapper {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testFindUserWithRoleByUsername(){
      User user= userMapper.findUserWithRoleByUserName("admin");
        System.out.println(user);
    }

}
