package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.RoleMapper;
import cn.tedu.straw.portal.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: RoleMapper测试类
 * {@link cn.tedu.straw.portal.mapper.RoleMapper}
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 18:10$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestRoleMapper {

    @Resource
    private RoleMapper roleMapper;

    /**
     * @see RoleMapper#findAllRoleByUserId(Integer)
     */
    @Test
    public void testFindAllRoleByUserId(){
      List<Role> roles= roleMapper.findAllRoleByUserId(1);
      roles.forEach(System.out::println);

    }
}
