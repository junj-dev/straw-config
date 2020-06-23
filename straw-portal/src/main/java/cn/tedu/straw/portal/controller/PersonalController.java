package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.vo.MyInfoVO;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserInfoVO;
import cn.tedu.straw.portal.service.IPersonalService;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import cn.tedu.straw.portal.service.IUserCollectService;
import cn.tedu.straw.portal.service.IUserService;
import com.github.pagehelper.PageInfo;
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
    @Resource
    private IRecommendQuestionService recommendQuestionService;
    @Resource
    private IUserCollectService userCollectService;

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
    public R getMyInfo(){
      MyInfoVO myInfo= personalService.getMyInfo();
      if(myInfo==null){
          log.error("系统出错，用户信息为空");
          return R.failed("系统繁忙，请稍后再试！");
      }
      return R.success(myInfo);
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    @ApiOperation("获取用户资料")
    public R<UserInfoVO> getUserInfo(){
      UserInfoVO user=  personalService.getUserInfo();
      if(user==null){
          return R.failed("系统繁忙，请稍后再试！");
      }
      return  R.success(user);
    }




    @GetMapping("/myinfo.html")
    public String toMyInfoPage(Model model){
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        return "personal/userInfo";
    }


    @PostMapping("/resetMyInfo")
    @ResponseBody
    @ApiOperation("修改用户资料")
    public R resetMyInfo(@RequestBody @Validated  UserInfoVO userInfo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.validateFailed(bindingResult);
        }
        return toAjax(personalService.resetMyInfo(userInfo));

    }


    @GetMapping("/resetPassword.html")
    public String toResetPasswordPage(Model model){
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        return "personal/resetPassword";
    }


    @PostMapping("/resetpasswd")
    @ResponseBody
    @ApiOperation("重置密码")
    public R resetpasswd(@RequestParam("oldpasswd")String oldpasswd,
                         @RequestParam("newpasswd")String newpasswd){
        //检查原密码是否正确
       boolean isCorrect= personalService.checkPasswd(oldpasswd);
       if(!isCorrect){
          return R.failed("原密码错误！");
       }
        return toAjax(personalService.resetpasswd(newpasswd));


    }


    @GetMapping("/collect")
    public  String toMyCollect(){

        return "personal/collect";
    }

    @PostMapping("/findCollectQuestionsPage")
    @ResponseBody
    @ApiOperation("分页查询收藏列表")
    public R<PageInfo<Question>> findCollectQuestionsPage(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo = userCollectService.selectPage(pageNum, pageSize);

        return R.success(pageInfo);
    }


    @GetMapping("/myQuestions")
    @ApiOperation("我的提问")
    public String toMyQuestionPage(){
        return "personal/myQuestion";
    }

}
