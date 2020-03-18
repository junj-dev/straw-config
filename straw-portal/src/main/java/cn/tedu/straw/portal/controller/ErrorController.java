package cn.tedu.straw.portal.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description: 自定义错误页面
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 10:14$
 * @Version: 1.0
 */
@Controller
public class ErrorController extends BasicErrorController {


    @Autowired
    public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
       HttpStatus status= getStatus(request);
       Map<String,Object> model=getErrorAttributes(request, isIncludeStackTrace(request,MediaType.TEXT_HTML));
       if(HttpStatus.NOT_FOUND==status){
           model.put("msg","抱歉，该页面不存在");
       }else if(HttpStatus.INTERNAL_SERVER_ERROR==status){
           model.put("msg","抱歉，服务器繁忙，请稍后再试！");
       }

       ModelAndView modelAndView=new ModelAndView("error",model,status);
        return modelAndView;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String,Object> data=getErrorAttributes(request,isIncludeStackTrace(request,MediaType.ALL));
        data.put("msg","抱歉，服务器繁忙，请稍后再试！");
        HttpStatus status=getStatus(request);
        return new ResponseEntity<>(data,status);
    }
}
