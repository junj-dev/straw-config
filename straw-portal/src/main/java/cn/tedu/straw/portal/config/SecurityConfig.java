package cn.tedu.straw.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
    private UserDetailsService userDetailsService;

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


        http.httpBasic().disable().cors().and().csrf().disable()
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
                        .loginProcessingUrl("/login")
                        .loginPage("/login.html")
                        .failureUrl("/login-error.html")
                        .defaultSuccessUrl("/index.html")
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


//    /**
//     * 角色继承，管理员角色>老师角色>学生角色，前者包含后者
//     *
//     * @return
//     */
//    @Bean
//    RoleHierarchy roleHierarchy(){
//        RoleHierarchyImpl roleHierarchy=new RoleHierarchyImpl();
//        String hiererchy="ROLE_ADMIN > ROLE_TEACHER  ROLE_TEACHER > ROLE_STUDENT";
//        roleHierarchy.setHierarchy(hiererchy);
//        return roleHierarchy;
//    }
    
}

