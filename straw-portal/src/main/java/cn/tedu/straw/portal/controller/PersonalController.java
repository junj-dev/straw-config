package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.vo.MyInfo;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserInfoVO;
import cn.tedu.straw.portal.service.IPersonalService;
import cn.tedu.straw.portal.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
public class PersonalController extends BaseController {
    @Resource
    private IUserService userService;
    @Resource
    private IPersonalService personalService;

    @GetMapping("/myquestion.html")
    public String toMyQuetionPage(){
        return "personal/myquestion";
    }

    @GetMapping("/mytask.html")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String toMyTaskPage(Model model){
        List<User> teachers =getAvalibleTeachers();
        model.addAttribute("teachers",teachers);
        return "personal/task";
    }

    @GetMapping("/getMyInfo")
    @ResponseBody
    @ApiOperation("获取首页个人信息，包括任务，提问信息")
    public StrawResult getMyInfo(){
      MyInfo myInfo= personalService.getMyInfo();
      if(myInfo==null){
          log.error("系统出错，用户信息为空");
          return new StrawResult().failed("系统繁忙，请稍后再试！");
      }
      return new StrawResult().success(myInfo);
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    @ApiOperation("获取用户资料")
    public StrawResult<UserInfoVO> getUserInfo(){
      UserInfoVO user=  personalService.getUserInfo();
      if(user==null){
          return new StrawResult<UserInfoVO>().failed("系统繁忙，请稍后再试！");
      }
      return new StrawResult<UserInfoVO>().success(user);
    }




    @GetMapping("/myinfo.html")
    public String toMyInfoPage(){
        return "personal/userInfo";
    }


    @PostMapping("/resetMyInfo")
    @ResponseBody
    @ApiOperation("修改用户资料")
    public StrawResult resetMyInfo(@RequestBody @Validated  UserInfoVO userInfo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new StrawResult().validateFailed(bindingResult);
        }
        boolean isSuccess= personalService.resetMyInfo(userInfo);
        if(isSuccess){
            return new StrawResult().success();
        }else {
            return new StrawResult().failed("系统繁忙，修改失败，请稍后重试！");
        }
    }


    @GetMapping("/resetPassword.html")
    public String toResetPasswordPage(){
        return "personal/resetPassword";
    }


    @PostMapping("/resetpasswd")
    @ResponseBody
    @ApiOperation("重置密码")
    public StrawResult resetpasswd(@RequestParam("oldpasswd")String oldpasswd,
                                   @RequestParam("newpasswd")String newpasswd){
        //检查原密码是否正确
       boolean isCorrect= personalService.checkPasswd(oldpasswd);
       if(!isCorrect){
          return new StrawResult().failed("原密码错误！");
       }
       boolean isSuccess= personalService.resetpasswd(newpasswd);
        if(isSuccess){
            return new StrawResult().success();
        }

        return new StrawResult().failed("服务繁忙，请稍后再试！");

    }
}
