package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.mapper.PermissionMapper;
import cn.tedu.straw.portal.mapper.RoleMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.mapper.UserRoleMapper;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserRole;
import cn.tedu.straw.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    @Resource
    private IUserService userService;
    @Resource
    private UserRoleMapper userRoleMapper;


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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StrawResult register(RegisterParam param) {
        //先判断手机验证码是否正确，从redis中取出验证码
        String realCode = strRedisTemplate.opsForValue().get("straw:register:phone:" + param.getPhone());
        String code=param.getCode();
        if(StringUtils.isEmpty(realCode)|| !realCode.equals(code)){
            return new StrawResult().failed("手机验证码错误");
        }
        //判断手机号是否已被注册
        String phone=param.getPhone();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",phone);
        User u = userService.getOne(queryWrapper);
        if(u!=null){
            return new StrawResult().failed("该手机号已注册！请直接登录！");
        }
        //开始注册
        User user=new User();
        user.setUsername(phone);
        //密码要加密
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String realPasswd=passwordEncoder.encode(param.getPassword());
        user.setPassword(realPasswd);
        user.setNickname(param.getNickname());
        user.setPhone(phone);
        user.setCreatetime(new Date());
        user.setEnabled(true);
        user.setLocked(true);
        //保存
        int n = userMapper.insert(user);
        if(n!=1){
            throw  new BusinessException("服务繁忙，注册失败，请稍后再试!");
        }
        //添加角色
        UserRole userRole=new UserRole();
        userRole.setRoleId(2);
        userRole.setUserId(user.getId());

        int m = userRoleMapper.insert(userRole);
        if(m!=1){
            throw  new BusinessException("服务繁忙，注册失败，请稍后再试!");
        }
        return new StrawResult().success("注册成功！");
    }
}
