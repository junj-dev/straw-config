package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.service.IMessageService;
import com.aliyuncs.CommonResponse;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 阿里云短信发送控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/28$ 0:30$
 * @Version: 1.0
 */
@Controller
@RequestMapping("/aliyunMessage")
public class AliyunMessageController {
    @Resource
    private IMessageService messageService;

    @GetMapping("/sendRegisterCode")
    @ResponseBody
    @ApiOperation("发送注册验证码")
    public StrawResult sendRegisterCode(@RequestParam("phone")String phone,@RequestParam("inviteCode")String inviteCode){
       return messageService.sendRegisterCode(phone,inviteCode);


    }

    @GetMapping("/sendResetPasswordCode")
    @ResponseBody
    @ApiOperation("发送忘记密码短信验证码")
    public StrawResult sendResetPasswordCode (@RequestParam("phone")String phone){
        return messageService.sendResetPasswordCode(phone);

    }


}
