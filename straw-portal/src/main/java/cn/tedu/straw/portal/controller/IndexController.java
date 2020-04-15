package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 主页控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/3$ 10:27$
 * @Version: 1.0
 */
@Controller
@Api(value = "首页控制器",tags = "首页控制器")
public class IndexController extends BaseController {




    @GetMapping(value = {"/index.html","/"})
    @ApiOperation("转到首页")
    @PreAuthorize("hasAuthority('/index.html')")
    public  String  index(){
        List<String> roles = getUserRoleNames();
        if(roles!=null&&roles.contains("ROLE_TEACHER")){
            return "index_teacher";
        }
        return "index";
    }







}
