package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.mapper.PermissionMapper;
import cn.tedu.straw.portal.mapper.RoleMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-03
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService , UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 此处应该加上redis缓存，而不要每次去读取权限
        User user = userMapper.findUserWithRoleByUserName(username);
        if(user==null){
           throw  new RuntimeException("用户不存在");
        }
        //在security中的权限不仅仅是permission,也包括角色，GrantedAuthority只匹配字符串，
        // 因此把role和permission都当作GrantedAuthority
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(!CollectionUtils.isEmpty(user.getRoles())){
            authorities.addAll(user.getRoles());
            for(Role role:user.getRoles()){
                List<SimpleGrantedAuthority> permissionsAuthorities = permissionMapper.getPermissionsByRoleId(role.getId());
                authorities.addAll(permissionsAuthorities);
                //设置security的登录用户
                user.setAuthorities(authorities);
            }

        }

        return user;
    }
}
