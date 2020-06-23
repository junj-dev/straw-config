package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.domian.param.ResetPasswordParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.service.IUserService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.tedu.straw.portal.domian.param.TeacherCreateForm;

import javax.annotation.Resource;

/**
 * @Description: 系统控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/27$ 20:47$
 * @Version: 1.0
 */
@Controller
public class SystemController {

    @Resource
    private IUserService userService;

    @GetMapping("/login.html")
    public String toLoginPage() {
        return "login";
    }

    @GetMapping("/login-error.html")
    public String toLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register.html")
    public String toRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    @ApiModelProperty("注册新用户")
    public R register(@Validated RegisterParam param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  R.validateFailed(bindingResult);
        }
        userService.register(param);
        return R.success();
    }

    @GetMapping("/resetpassword.html")
    @ApiOperation("手机找回密码")
    public String resetPassword(){
        return "resetpassword";
    }

    @PostMapping("/resetpassword")
    @ResponseBody
    @ApiOperation("重置密码")
    public R resetPassword(@Validated ResetPasswordParam param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.validateFailed(bindingResult);
        }
        userService.resetPassword(param);
        return R.success();
    }

    /**
     * 创建老师
     * @param teacher
     * @param bindingResult
     * @return
     */
    @PostMapping("/createTeacher")
    public R createTeacher(@RequestBody @Validated TeacherCreateForm teacher, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.validateFailed(bindingResult);
        }
        userService.createTeacher(teacher);
        return R.success();

    }
}
