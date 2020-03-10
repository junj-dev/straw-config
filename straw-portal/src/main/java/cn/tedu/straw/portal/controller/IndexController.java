package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @Description: 主页控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/3$ 10:27$
 * @Version: 1.0
 */
@Controller
@Api(value = "首页控制器",tags = "首页控制器")
public class IndexController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IQuestionService questionService;


    @GetMapping("/")
    @ApiOperation("转到首页")
    public  String  index(Model model,
                          @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        PageInfo<Question> pageInfo = questionService.selectPage(pageNum, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }

    @GetMapping("/login")
    public String toLogin(){
        return  "login";
    }
    @GetMapping("/error")
    public String toError(){
        return  "login";
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

}
