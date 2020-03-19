package cn.tedu.straw.portal.controller;


import cn.tedu.straw.commom.CommonPage;
import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.constant.QuestionPublicStatus;
import cn.tedu.straw.portal.api.EsQuestionServiceApi;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 问题控制器
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/question")
@Api(value = "问题管理控制器",tags = "问题管理API")
@Validated
public class QuestionController extends BaseController {
    @Resource
    private ITagService tagService;
    @Resource
    private IQuestionService questionService;
    @Resource
    private  EsQuestionServiceApi  questionServiceApi;

    @GetMapping("/create.html")
    public  String create(Model model){
        //查询出所有的标签
        List<Tag> tags = tagService.list();
        model.addAttribute("tags",tags);
        return "/question/create.html";
    }

    /**
     * 上传图片到图片服务器
     *
     *
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadMultipleFile")
    @ApiOperation(value = "上传图片到图片服务器")
    @ResponseBody
    public StrawResult uploadImg(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        return questionService.uploadImg(files, request);
    }



    @PostMapping("/create")
    @ApiOperation("创建问题")
    public String create(@Validated  QuestionParam question, BindingResult result,RedirectAttributes attributes){
        if(result.hasErrors()){
            List<String> errors= getErrorInfo(result);
            attributes.addFlashAttribute("errors",errors);
            attributes.addFlashAttribute("title",question.getTitle());
            attributes.addFlashAttribute("content",question.getContent());
            return "redirect:/question/create.html";
        }
        questionService.create(question);
        return "redirect:/index.html";


    }

    @GetMapping("/detail/{id}")
    @ApiOperation("查看某个问题详情")
    public String datail(@PathVariable("id")Long id,Model model){
       Question question= questionService.getQuestionDetailById(id);
        model.addAttribute("question",question);
        return "/question/detail";
    }

    @PostMapping("/answer")
    @PreAuthorize("hasAuthority('/question/answer')")
    @ApiOperation("回答问题")
    public  String answer(@RequestParam("id")Long id,@RequestParam("content")String content){
       Boolean isSuccess= questionService.answer(id,content);
       if(isSuccess){
           return "redirect:/question/detail/"+id;
       }else {
           return "redirect:/error";
       }
    }

    @RequestMapping("/search")
    @ResponseBody
    public StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        List<String> userRoleNames = getUserRoleNames();
        //只有学生角色
        if(userRoleNames.contains("ROLE_STUDENT")&&userRoleNames.size()==1){
            return  questionServiceApi.searchOpenQuestion(keyword,pageNum,pageSize,getUseId(), QuestionPublicStatus.PUBLIC.getStatus());
        }
        return questionServiceApi.search(keyword,pageNum,pageSize);

    }
}
