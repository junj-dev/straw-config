package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private IQuestionService questionService;


    @GetMapping(value = {"/index.html","/"})
    @ApiOperation("转到首页")
    @PreAuthorize("hasAuthority('/index.html')")
    public  String  index(
//            Model model,
//            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
//            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){

       // PageInfo<Question> pageInfo = questionService.selectPage(pageNum, pageSize);
      //  model.addAttribute("pageInfo",pageInfo);
        return "index";
    }







}
