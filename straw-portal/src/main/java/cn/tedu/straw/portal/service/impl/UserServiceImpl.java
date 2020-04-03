package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.common.constant.RedisKeyPrefix;
import cn.tedu.straw.portal.base.BaseServiceImpl;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.domian.param.ResetPasswordParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.mapper.*;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-03
 */
@Service
@Slf4j
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
    @Resource
    private ClassroomMapper classroomMapper;
    @Resource
    private  StudentMapper studentMapper;


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
        //判断邀请码是否正确,直接通过邀请码查找班级，如果班级不存在说明邀请码错误
        QueryWrapper query=new QueryWrapper();
        query.eq("invite_code",param.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(query);
        if(classroom==null){
            return new StrawResult().failed("邀请码错误");
        }
        //先判断手机验证码是否正确，从redis中取出验证码
        String realCode = strRedisTemplate.opsForValue().get(RedisKeyPrefix.REGISTER_CODE_PREFIX + param.getPhone());
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
        //密码要加密
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String realPasswd=passwordEncoder.encode(param.getPassword());
        User user=new User();
        user.setUsername(phone);
        user.setPassword(realPasswd);
        user.setNickname(param.getNickname());
        user.setCreatetime(new Date());
        user.setEnabled(true);
        user.setLocked(true);
        //保存
        if(userMapper.insert(user)!=1){
            throw  new BusinessException("服务繁忙，注册失败，请稍后再试!");
        }
        //创建学生
        Student student =new Student();
        student.setName(param.getNickname());
        student.setPhone(phone);
        student.setClassroomId(classroom.getId());
        if(studentMapper.insert(student)!=1){
            throw  new BusinessException("服务繁忙，注册失败，请稍后再试!");
        }
        //添加角色
        UserRole userRole=new UserRole();
        userRole.setRoleId(2);
        userRole.setUserId(user.getId());

        if(userRoleMapper.insert(userRole)!=1){
            throw  new BusinessException("服务繁忙，注册失败，请稍后再试!");
        }
        return new StrawResult().success("注册成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StrawResult resetPassword(ResetPasswordParam param) {
        //判断手机号是否已被注册
        String phone=param.getPhone();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",phone);
        User u = userService.getOne(queryWrapper);
        if(u==null){
            return new StrawResult().failed("该手机号未注册，请先注册！");
        }
        //先判断手机验证码是否正确，从redis中取出验证码
        String realCode = strRedisTemplate.opsForValue().get(RedisKeyPrefix.RESET_PASSWORD_CODE_PREFIX + param.getPhone());
        String code=param.getCode();
        if(StringUtils.isEmpty(realCode)|| !realCode.equals(code)){
            return new StrawResult().failed("手机验证码错误");
        }
        //修改密码
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String realPasswd=passwordEncoder.encode(param.getPassword());
        int n = userMapper.updatePasswordByUsername(phone,realPasswd);
        if(n!=1){
            log.error("修改密码时系统出错");
            throw  new BusinessException("系统繁忙，请稍后再试！");
        }

        return new StrawResult().success("设置密码成功！");
    }
}
