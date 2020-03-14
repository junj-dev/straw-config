package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.StrawResult;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-09
 */
@Controller
@RequestMapping("/question")
@Validated
public class QuestionController extends BaseController {
    @Resource
    private ITagService tagService;
    @Resource
    private IQuestionService questionService;

    @GetMapping("/toCreate")
    public  String create(Model model){
        //查询出所有的标签
        List<Tag> tags = tagService.list();
        model.addAttribute("tags",tags);
        return "/question/create";
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
    public String create(@RequestParam("title") @NotNull(message = "标题不能为空")String title,
                         @RequestParam("tags") @NotNull(message = "标签不能为空")String[] tags,
                         @RequestParam("content") @NotNull(message = "内容不能为空")String content){
       questionService.create(title,tags,content);
       return "redirect:/index";


    }

    @GetMapping("/detail/{id}")
    public String datail(@PathVariable("id")Long id,Model model){
       Question question= questionService.getQuestionDetailById(id);
        model.addAttribute("question",question);
        return "/question/detail";
    }

    @PostMapping("/answer")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    public  String answer(@RequestParam("id")Long id,@RequestParam("content")String content){
       Boolean isSuccess= questionService.answer(id,content);
       if(isSuccess){
           return "redirect:/question/detail/"+id;
       }else {
           return "redirect:/error";
       }



    }

}
