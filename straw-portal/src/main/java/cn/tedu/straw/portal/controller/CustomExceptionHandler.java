package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.exception.PageNotExistException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
            return new StrawResult().failed("服务繁忙，请稍后重试！");
        } else {
            //用户跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new JSONPObject(callback, new StrawResult().failed("服务繁忙，请稍后重试！"));
        }
    }

    @ExceptionHandler(BusinessException.class)
    public Object businessException(Exception exception, HttpServletRequest request) {
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new StrawResult().failed(exception.getMessage());
        } else {
            //用户跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new JSONPObject(callback, new StrawResult().failed(exception.getMessage()));
        }
    }

    /**
     * 无权访问
     *
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Object AccessDeniedHandler(Exception exception, HttpServletRequest request){
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new StrawResult().forbidden();
        } else {
            //用户跨域请求
            exception.printStackTrace();
            log.error(exception.getMessage());
            return new JSONPObject(callback, new StrawResult().forbidden());
        }
    }

    /**
     * 页面不存在
     *
     */
    @ExceptionHandler(PageNotExistException.class)
    public void PageNotFoundExceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error(exception.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("页面也被删除或不存在！");
    }

}
