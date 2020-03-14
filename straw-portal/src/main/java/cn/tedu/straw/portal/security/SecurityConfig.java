package cn.tedu.straw.portal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private StrawAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

   // @Resource
    //private  LoginFailureHandler loginFailureHandler;

   @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new StrawAccessDeniedHandler();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.httpBasic().disable().cors().and().csrf().disable()
                //没有权限时
                .exceptionHandling()
                .accessDeniedHandler(getAccessDeniedHandler())
                .and()
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
                        "/actuator/**"
                ).permitAll()
                        .anyRequest().authenticated()   // 其他地址的访问均需验证权限
                        .and()
                        .formLogin()
                         .loginPage("/login.html")
                         .loginProcessingUrl("/login/doLogin")
                        .successHandler(myAuthenctiationSuccessHandler)
                        .permitAll()
                        .and()
                        .logout()
                        .logoutSuccessUrl("/login.html")


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

//    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//        String encode = passwordEncoder.encode("123456");
//        System.out.println("encode:"+encode);
//    }
    
}

