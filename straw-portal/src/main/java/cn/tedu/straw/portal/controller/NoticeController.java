package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.model.Notice;
import cn.tedu.straw.portal.service.INoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  消息通知控制器
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-27
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Resource
    private INoticeService noticeService;

    @GetMapping("/list")
    @ApiOperation("获取本用户的消息")
    @ResponseBody
    public R selectMyNoticeList(){

       List<Notice> noticeList= noticeService.getMyNoticeList();
       return R.success(noticeList);
    }

    @GetMapping("/all")
    @ApiOperation("转到所有消息页面")
    public String allNotice(){
        return "notice/all";
    }

    @PostMapping("/getAll")
    @ApiOperation("获取所有的消息")
    @ResponseBody
    public R<PageInfo<Notice>> getAll(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Notice> pageInfo= noticeService.getAllNotice(pageNum,pageSize);
      return R.success(pageInfo);
    }


    @GetMapping("/count")
    @ApiOperation("获取本用户的消息")
    @ResponseBody
    public R countNotice(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",getUseId());
        queryWrapper.eq("read_status",false);
        int count = noticeService.count(queryWrapper);
        return R.success(count);
    }

    @GetMapping("/detail/{questionId}/{noticeId}")
    @ApiOperation("查看某个问题详情")
    public String noticeDetail(@PathVariable("questionId")Integer id,
                               @PathVariable("noticeId")Integer noticeId, Model model) {
        //把该条信息删除掉
        Notice notice=new Notice();
        notice.setId(noticeId);
        notice.setReadStatus(true);
        noticeService.updateById(notice);
        //转发到问题详情
        return "redirect:/question/detail/"+id;
    }

}
