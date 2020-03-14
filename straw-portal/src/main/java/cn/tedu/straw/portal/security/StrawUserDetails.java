package cn.tedu.straw.portal.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
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

    private  Integer id;
    private  String username;
    private  String password;
    private  String nickName;
    private String role;

    public StrawUserDetails(Integer id, String username, String password, String nickName, String role,Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.role=role;
        this.authorities = authorities;
    }

    private Collection<? extends GrantedAuthority> authorities=new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public String getNickName() {
        return nickName;
    }

    public String getRole() {
        return role;
    }

}
