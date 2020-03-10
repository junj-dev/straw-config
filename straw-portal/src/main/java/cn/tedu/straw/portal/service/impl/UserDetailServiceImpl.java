package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.domian.StrawUserDetails;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/3$ 15:44$
 * @Version: 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> query=new QueryWrapper<>();
        query.eq("username",username);
        User user = userMapper.selectOne(query);
        if(user==null){return null; }
        StrawUserDetails userDetails=new StrawUserDetails();
        userDetails.setUsername(username);
        userDetails.setPassword(user.getPassword());
        //TODO 权限信息先不写
        return userDetails;
    }
}
