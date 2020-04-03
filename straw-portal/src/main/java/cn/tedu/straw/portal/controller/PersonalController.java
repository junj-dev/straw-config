package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.service.ITeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 个人中心控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/21$ 21:03$
 * @Version: 1.0
 */
@Controller
@RequestMapping("/personal")
public class PersonalController extends BaseController {
    @Resource
    private ITeacherService teacherService;

    @GetMapping("/myquestion.html")
    public String toMyQuetionPage(){
        return "personal/myquestion";
    }

    @GetMapping("/mytask.html")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String toMyTaskPage(Model model){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("enabled",true);
        List teachers = teacherService.list(queryWrapper);
        model.addAttribute("teachers",teachers);
        return "personal/task";
    }


}
