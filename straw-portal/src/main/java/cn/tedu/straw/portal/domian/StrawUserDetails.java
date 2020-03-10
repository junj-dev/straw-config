package cn.tedu.straw.portal.domian;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @Description: 用户登录信息
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/3$ 15:47$
 * @Version: 1.0
 */
@Data
public class StrawUserDetails  implements UserDetails , Serializable {


    private static final long serialVersionUID = 3673538802497943355L;

    private  String username;
    private  String password;
    private Collection<? extends GrantedAuthority> authorities=new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
