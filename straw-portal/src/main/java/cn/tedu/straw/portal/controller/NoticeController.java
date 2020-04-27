package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.model.Notice;
import cn.tedu.straw.portal.service.INoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Resource
    private INoticeService noticeService;

    @GetMapping("/list")
    @ApiOperation("获取本用户的消息")
    public StrawResult selectMyNoticeList(){

       List<Notice> noticeList= noticeService.getMyNoticeList();
       return new StrawResult().success(noticeList);
    }

}
