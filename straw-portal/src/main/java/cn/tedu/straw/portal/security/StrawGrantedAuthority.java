package cn.tedu.straw.portal.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Description: 权限（这里的权限和shiro不一样，它包括角色和资源）
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/13$ 21:29$
 * @Version: 1.0
 */
public class StrawGrantedAuthority implements GrantedAuthority {

    private  String authority;

    public StrawGrantedAuthority(){

    }
    public StrawGrantedAuthority(String authority){
        this.authority=authority;
    }


    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
