package cn.tedu.straw.portal.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 登录失败的处理类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/11$ 10:54$
 * @Version: 1.0
 */
//@Component
public class StrawLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {


//    public LoginFailureHandler(){
//        this.setDefaultFailureUrl("/login.html?error=true");
//    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //TODO
        //可以在此处记录日志，或者记录登录失败次数，限制用户登录次数
        response.sendRedirect("/login.html?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}
