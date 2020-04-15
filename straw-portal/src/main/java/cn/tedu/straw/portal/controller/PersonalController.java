package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.vo.MyInfo;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IPersonalService;
import cn.tedu.straw.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ApiOperation("获取个人信息")
    public StrawResult getMyInfo(){
      MyInfo myInfo= personalService.getMyInfo();
      if(myInfo==null){
          return new StrawResult().failed("系统出现故障!");
      }
      return new StrawResult().success(myInfo);
    }


    @GetMapping("/myinfo.html")
    public String toMyInfoPage(){
        return "personal/myInfo";
    }

}
