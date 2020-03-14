package cn.tedu.straw.portal.test.mapper;

import cn.tedu.straw.portal.StrawPortalApplication;
import cn.tedu.straw.portal.mapper.PermissionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link cn.tedu.straw.portal.mapper.PermissionMapper}
 * @Description: PermissionMapper测试类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/13$ 23:37$
 * @Version: 1.0
 */
@SpringBootTest(classes = StrawPortalApplication.class)
@RunWith(SpringRunner.class)
public class TestPermissionMapper {
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * @see PermissionMapper#getPermissionsByRoleId(Integer) 
     */
    @Test
    public void testGetPermissionsByRoleId(){
        List<GrantedAuthority> authorities = permissionMapper.getPermissionsByRoleId(1);
        authorities.forEach(System.out::println);
    }
}
