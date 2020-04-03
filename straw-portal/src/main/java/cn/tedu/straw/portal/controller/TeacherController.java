package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.service.ITeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.annotation.Retention;
import java.util.List;

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
    private ITeacherService teacherService;

    @GetMapping("/loadAllTeachers")
    @ResponseBody
    @ApiOperation("加载所有老师")
    public StrawResult loadAllTeachers(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("enabled",true);
        List teachers = teacherService.list(queryWrapper);
        return new StrawResult().success(teachers);
    }

}
