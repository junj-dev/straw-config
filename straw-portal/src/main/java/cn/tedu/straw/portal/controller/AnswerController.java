package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.common.constant.RoleName;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    @Resource
    private ICommentService commentService;

    @GetMapping("/acceptAnswer")
    @ApiOperation("采纳该回答")
    public String acceptAnswer(@RequestParam("answerId")Integer answerId,@RequestParam("questionId")Integer questionId){
        Answer answer=new Answer();
        answer.setId(answerId);
        answer.setAcceptStatus(true);
        boolean a = answerService.updateById(answer);
        //问题设置为已解决
        Question question=new Question();
        question.setId(questionId);
        question.setStatus(2);
        boolean b = questionService.updateById(question);

        if(a&&b){
            return "redirect:/question/detail/"+questionId;
        }
        throw  new BusinessException("服务繁忙，请稍后再试！");
    }

    @GetMapping("/edit/{id}")
    @ApiOperation("转到编辑回答页面")
    public String toEditPage(@PathVariable("id")Integer id, Model model){
        Answer answer = answerService.getById(id);
        if(answer==null){
           throw  new BusinessException("没有该记录！");
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
    public StrawResult getById(@PathVariable("id")Integer id){
        Answer answer = answerService.getById(id);
        if(answer!=null){
            return new StrawResult().success(answer);
        }

      return new StrawResult().failed("没有该记录");
    }

    @PostMapping("/update")
    @ApiOperation("提交修改答案")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public StrawResult update(@RequestParam("answerId")Integer answerId,@RequestParam("content")String content){
        Answer answer=new Answer();
        answer.setId(answerId);
        answer.setContent(content);
        boolean isSuccess = answerService.updateById(answer);
        if(isSuccess){
            return new StrawResult().success();
        }else {
            return new StrawResult().failed("服务器繁忙,请稍后重试！");
        }

    }

    @GetMapping("/delete/{answerId}")
    @ApiOperation("删除回答")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @ResponseBody
    public StrawResult delete(@PathVariable("answerId")Integer answerId){
      Boolean isSuccess=  answerService.deleteById(answerId);
      if(isSuccess){
          return new StrawResult().success();
      }
        return new StrawResult().failed("删除失败！");

    }

}
