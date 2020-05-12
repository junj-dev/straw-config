package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.service.ICommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  评论控制器
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-04-25
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    private ICommentService commentService;


    @PostMapping("/add")
    @ApiOperation("添加评论")
    public String add(@RequestParam("answerId")Integer answerId,
                      @RequestParam("questionId") Integer questionId,
                      @RequestParam("content")String content,
                      Model model){

        commentService.create(answerId,content,questionId);
        return "redirect:/question/detail/"+questionId;
    }
    @PostMapping("/update")
    @ApiOperation("添加评论")
    public String update(@RequestParam("commentId")Integer commentId,
                      @RequestParam("questionId") Integer questionId,
                      @RequestParam("content")String content,
                      Model model){

        commentService.update(commentId,content);
        return "redirect:/question/detail/"+questionId;
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除提问")
    @ResponseBody
    public StrawResult delete(@PathVariable("id")Integer id){
        boolean isSuccess = commentService.removeById(id);
        if(isSuccess){
            return new StrawResult().success();
        }
        return new StrawResult().failed();
    }


}
