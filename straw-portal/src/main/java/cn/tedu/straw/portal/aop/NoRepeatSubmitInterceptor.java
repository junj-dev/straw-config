package cn.tedu.straw.portal.aop;

import cn.tedu.straw.portal.annotation.NoRepeatSubmit;
import cn.tedu.straw.portal.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
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


    @Around("execution(* cn.tedu.straw.portal.controller.*.*(..)) && @annotation(noRepeatSubmit)")
    public synchronized Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        ValueOperations<String, String> opsForValue = strRedisTemplate.opsForValue();
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
    }
}
