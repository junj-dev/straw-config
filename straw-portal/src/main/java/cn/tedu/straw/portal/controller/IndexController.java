package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @Resource
    private IRecommendQuestionService recommendQuestionService;


    @GetMapping(value = {"/index.html","/"})
    @ApiOperation("转到首页")
    @PreAuthorize("hasAuthority('/index.html')")
    public  String  index(Model model){

        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        List<String> roles = getUserRoleNames();
        if(roles!=null&&roles.contains("ROLE_ADMIN")){//管理员角色跳转页面
            return "admin/index";
        }
        if(roles!=null&&roles.contains("ROLE_TEACHER")){ //老师跳转页面
            return "index_teacher";
        }
        return "index";//学生跳转页面
    }







}
