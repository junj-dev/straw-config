package cn.tedu.straw.portal.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 登录成功的处理类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/11$ 0:09$
 * @Version: 1.0
 */
@Component
public class StrawAuthenctiationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

//        RequestCache cache = new HttpSessionRequestCache();
//        SavedRequest savedRequest = cache.getRequest(request, response);
        // 如果来源请求为空则跳转到首页
        String url = "/index";
//        if((savedRequest==null)){
//            url = "/index";
//        }else{
//            url = savedRequest.getRedirectUrl();
//        }

        //不同的用户角色跳转到不同的请求
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            StrawUserDetails user = (StrawUserDetails)authentication.getPrincipal();
//            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//            //如果是admin角色，跳转/admin/index
//            if(authorities.contains(new StrawGrantedAuthority("ROLE_ADMIN"))){
//                url="/admin/index";
//            }else if(authorities.contains(new StrawGrantedAuthority("ROLE_STUDENT"))){
//                url="/student/index";
//            }else if(authorities.contains(new StrawGrantedAuthority("ROLE_TEACHER"))){
//                url="/teacher/index";
//            }
//        }


        response.sendRedirect(url);
    }

}
