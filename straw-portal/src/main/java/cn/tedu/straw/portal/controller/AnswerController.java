package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.IQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @Description: 回答控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/26$ 15:39$
 * @Version: 1.0
 */
@Controller
@Api(value = "回答控制器",tags = "回答控制器")
@RequestMapping("/answer")
public class AnswerController {
    @Resource
    private IAnswerService answerService;
    @Resource
    private IQuestionService questionService;

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

}
