package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.domian.StrawResult;
import cn.tedu.straw.portal.exception.BusinessException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义全局异常处理
 */
@RestControllerAdvice
@Slf4j                   //记录日志
public class CustomExceptionHandler {

    //当系统中出现运行时异常时生效
    //区分 系统正常异常和跨域异常
    //说明:跨域访问时用户一定会添加callback参数
    @ExceptionHandler(RuntimeException.class)
    public Object error(Exception exception, HttpServletRequest request) {
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return StrawResult.builder().build().failed("服务繁忙，请稍后重试！");
        } else {
            //用户跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new JSONPObject(callback, StrawResult.builder().build().failed("服务繁忙，请稍后重试！"));
        }
    }

    @ExceptionHandler(BusinessException.class)
    public Object businessException(Exception exception, HttpServletRequest request) {
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return StrawResult.builder().build().failed(exception.getMessage());
        } else {
            //用户跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new JSONPObject(callback, StrawResult.builder().build().failed(exception.getMessage()));
        }
    }

    /**
     * 无权访问
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Object AccessDeniedHandler(Exception exception, HttpServletRequest request){
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return StrawResult.builder().build().forbidden();
        } else {
            //用户跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new JSONPObject(callback, StrawResult.builder().build().forbidden());
        }
    }

}
