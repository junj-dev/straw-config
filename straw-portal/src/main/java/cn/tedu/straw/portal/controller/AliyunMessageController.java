package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.service.IMessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
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
public class AliyunMessageController extends BaseController {
    @Resource
    private IMessageService messageService;

    @GetMapping("/sendRegisterCode")
    @ResponseBody
    @ApiOperation("发送注册验证码")
    public R sendRegisterCode(@RequestParam("phone")String phone, @RequestParam("inviteCode")String inviteCode){
       return toAjax(messageService.sendRegisterCode(phone,inviteCode));
    }

    @GetMapping("/sendResetPasswordCode")
    @ResponseBody
    @ApiOperation("发送忘记密码短信验证码")
    public R sendResetPasswordCode (@RequestParam("phone")String phone){
         return toAjax(messageService.sendResetPasswordCode(phone));
    }


}
