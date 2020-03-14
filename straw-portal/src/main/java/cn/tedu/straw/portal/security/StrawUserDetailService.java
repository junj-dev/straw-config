package cn.tedu.straw.portal.security;

import cn.tedu.straw.portal.mapper.PermissionMapper;
import cn.tedu.straw.portal.mapper.RoleMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/3$ 15:44$
 * @Version: 1.0
 */
@Service
public class StrawUserDetailService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> query=new QueryWrapper<>();
        query.eq("username",username);
        User user = userMapper.selectOne(query);
        if(user==null){return null; }
        //在security中的权限不仅仅是permission,也包括角色，GrantedAuthority只匹配字符串，
        // 因此把role和permission都当作GrantedAuthority
        List<GrantedAuthority> authorities=new ArrayList<>();
        Role role = roleMapper.selectById(user.getRoleId());
        authorities.add(role);
        List<GrantedAuthority> permissionsAuthorities = permissionMapper.getPermissionsByRoleId(user.getRoleId());
        authorities.addAll(permissionsAuthorities);
        //设置security的登录用户
        StrawUserDetails userDetails=new StrawUserDetails(user.getId(),username,user.getPassword(),user.getNickname(),role.getName(),authorities);



        return userDetails;
    }
}
