package cn.tedu.straw.portal.security;

import cn.tedu.straw.portal.config.GateWayUrlConfig;
import cn.tedu.straw.portal.security.handler.LoginSuccessHandler;
import cn.tedu.straw.portal.security.strategy.StrawExpiredSessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


   @Resource
   private UserDetailsService userDetailsService;
   @Resource
   private GateWayUrlConfig gateWayUrlConfig;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.httpBasic().disable().cors()
                         .and()
                            .csrf()
                            .disable()
                            .authorizeRequests()
                            .antMatchers(
                                    "/webjars/**",
                                    "/doc.html",
                                    "/plugins/**",
                                    "/logout",
                                    "/css/**",
                                    "/dist/**",
                                    "/js/**",
                                    "/img/**",
                                    "/fonts/**",
                                    "/swagger-resources/**",
                                    "/v2/api-docs",
                                    "/favicon.ico",
                                    "/swagger-ui.html",
                                    "/actuator/**",
                                    "/register.html",
                                    "/register",
                                    "/resetpassword.html",
                                    "/resetpassword",
                                    "/aliyunMessage/sendResetPasswordCode",
                                    "/aliyunMessage/sendRegisterCode")//阿里云注册短信
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated()   // 其他地址的访问均需验证权限
                        .and()
                            .formLogin()
                            .loginProcessingUrl("/login")
                            //.successHandler(loginSuccessHandler())
                            .loginPage("/login.html")
                            .failureUrl("/login-error.html")
                            .defaultSuccessUrl(gateWayUrlConfig.getUrl()+"/index.html")
                            .permitAll()
                        .and()
                            .rememberMe()
                            .alwaysRemember(true)
                            .tokenValiditySeconds(2419200) //记住我，4周有效
                        .and()
                            .logout()
                            .logoutSuccessUrl(gateWayUrlConfig.getUrl()+"/login.html")
                        .and()
                            .sessionManagement()
                            .invalidSessionUrl(gateWayUrlConfig.getUrl()+"/login.html")
                            .maximumSessions(1)
                            .maxSessionsPreventsLogin(false) // 当达到maximumSessions时，true表示不能踢掉前面的登录，false表示踢掉前面的用户
                            .expiredSessionStrategy(new StrawExpiredSessionStrategy()) // 当达到maximumSessions时，踢掉前面登录用户后的操作


        ;
    }
   

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	  CorsConfiguration configuration = new CorsConfiguration();
          configuration.setAllowedOrigins(Arrays.asList("*"));
          configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE"));
          configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type","Access-Control-Allow-Origin"));
          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          source.registerCorsConfiguration("/**", configuration);
          return source;
    }


    
}

