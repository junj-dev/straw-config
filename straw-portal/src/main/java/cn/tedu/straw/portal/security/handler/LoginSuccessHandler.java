package cn.tedu.straw.portal.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/31$ 21:47$
 * @Version: 1.0
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//        session.setAttribute("user",authentication.getPrincipal());
//        session.setMaxInactiveInterval(60);  // Session保存两小时
//        Cookie cookie = new Cookie("JSESSIONID", session.getId());
//        cookie.setMaxAge(60);  // 客户端的JSESSIONID也保存两小时
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//        Authentication authentication1 = securityContextImpl.getAuthentication();
//        authentication1.
    }
}
