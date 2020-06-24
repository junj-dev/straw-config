package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.exception.PageNotExistException;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 回答控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/26$ 15:39$
 * @Version: 1.0
 */
@Controller
@Api(value = "回答控制器",tags = "回答控制器")
@RequestMapping("/answer")
public class AnswerController extends BaseController {
    @Resource
    private IAnswerService answerService;
    @Resource
    private IQuestionService questionService;
    @Resource
    private IRecommendQuestionService recommendQuestionService;


    @GetMapping("/acceptAnswer")
    @ApiOperation("采纳该回答")
    public String acceptAnswer(@RequestParam("answerId")Integer answerId,@RequestParam("questionId")Integer questionId){

       answerService.acceptAnswer(answerId,questionId);
       return "redirect:/question/detail/"+questionId;

    }

    @GetMapping("/edit/{id}")
    @ApiOperation("转到编辑回答页面")
    public String toEditPage(@PathVariable("id")Integer id, Model model){
        Answer answer = answerService.getById(id);
        if(answer==null){
           throw  new PageNotExistException("没有该记录！");
        }
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        model.addAttribute("answerId",id);

        return "answer/edit";
    }

    @GetMapping("/{id}")
    @ApiOperation("获取答案")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public R getById(@PathVariable("id")Integer id){
        Answer answer = answerService.getById(id);
        if(answer!=null){
            return R.success(answer);
        }

      return R.failed("没有该记录");
    }

    @PostMapping("/update")
    @ApiOperation("提交修改答案")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public R update(@RequestParam("answerId")Integer answerId, @RequestParam("content")String content){
        Answer answer=new Answer();
        answer.setId(answerId);
        answer.setContent(content);
       return toAjax(answerService.updateById(answer));


    }

    @GetMapping("/delete/{answerId}")
    @ApiOperation("删除回答")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @ResponseBody
    public R delete(@PathVariable("answerId")Integer answerId)
    {
      answerService.deleteAnswerById(answerId);
      return R.success();
    }

}
