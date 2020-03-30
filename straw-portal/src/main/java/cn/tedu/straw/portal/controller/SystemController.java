package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.service.IUserService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public StrawResult register(@Validated RegisterParam param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new StrawResult().validateFailed(bindingResult);
        }
       return userService.register(param);

    }

    @GetMapping("/resetpassword.html")
    @ApiOperation("手机找回密码")
    public String resetPassword(){
        return "resetpassword";
    }
}
