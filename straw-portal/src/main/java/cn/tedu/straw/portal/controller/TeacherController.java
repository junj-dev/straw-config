package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.vo.TeacherVO;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    @Resource
    private IUserService userService;

    @GetMapping("/loadAllTeachers")
    @ResponseBody
    @ApiOperation("加载所有老师")
    public R<User> loadAllTeachers(){
        List<User> teachers = getAvalibleTeachers();
        return R.success(teachers);
    }
    @GetMapping("/loadAllTeacherNames")
    @ResponseBody
    @ApiOperation("加载所有老师")
    public R<List<String>> loadAllTeacherNames(){
        List<User> teachers = getAvalibleTeachers();
        List<String> teacherNames = teachers.stream().map(User::getNickname).collect(Collectors.toList());
        return R.success(teacherNames);
    }

    @GetMapping("/loadAllTeacherVos")
    @ResponseBody
    @ApiOperation("加载所有老师,用于复选框展示")
    public R loadAllTeacherVos(){
        List<User> teachers = getAvalibleTeachers();
        //把老师封装成页面的复选框数组内容
        List<TeacherVO> teacherVOS = teachers.stream().map(t -> {
            TeacherVO vo = new TeacherVO();
            vo.setText(t.getNickname());
            vo.setValue(t.getId());
            return vo;
        }).collect(Collectors.toList());
        return R.success(teacherVOS);
    }




}
