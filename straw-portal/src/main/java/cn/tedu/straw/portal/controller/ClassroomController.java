package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.model.Classroom;
import cn.tedu.straw.portal.service.IClassroomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  班级控制器
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/classroom")
public class ClassroomController extends BaseController {
    @Resource
    private IClassroomService classroomService;

    @GetMapping("/list")
    @ApiOperation("获取班级列表")
    public R<List<Classroom>> list(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("enabled",true);
        List<Classroom> list = classroomService.list(queryWrapper);
        return R.success(list);
    }

}
