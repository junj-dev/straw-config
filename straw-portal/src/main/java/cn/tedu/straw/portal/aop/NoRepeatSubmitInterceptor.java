package cn.tedu.straw.portal.aop;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.annotation.NoRepeatSubmit;
import cn.tedu.straw.portal.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 防止重复提交拦截器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/1$ 17:56$
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitInterceptor {
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    /**
     * 拦截任何controller类的有@NoRepeatSubmit注解的方法
     * 这里注意一定要加synchronized,否则鼠标多击的时候，同时启动多个线程
     * 此时redis 的key键的值都是为空，所以无法达到防止重复提交的效果
     * @param pjp
     * @param noRepeatSubmit
     * @return
     */

    @Around("execution(* cn.tedu.straw.portal.controller.*.*(..)) && @annotation(noRepeatSubmit)")
    public synchronized Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) {
        ValueOperations<String, String> opsForValue = strRedisTemplate.opsForValue();
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            if (opsForValue.get(key)==null) {
                Object o = pjp.proceed();
                opsForValue.set(key, "something", 3, TimeUnit.SECONDS);
                System.err.println("----------------");
               return o;
            } else {
                log.error("重复提交");
                throw  new BusinessException("请勿重复提交表单");
            }

        } catch (Throwable e) {
            e.printStackTrace();
            log.error("验证重复提交时出错!");
            return null;
        }

    }
}
