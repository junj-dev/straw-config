package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.annotation.NoRepeatSubmit;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
    private IUserService userService;
    @Resource
    private EsQuestionServiceApi questionServiceApi;

    @GetMapping("/create.html")
    public  String create(Model model){
        //查询出所有的标签
        List<Tag> tags = tagService.list();
        model.addAttribute("tags",tags);
        List<User> teachers = getAvalibleTeachers();
        model.addAttribute("teachers",teachers);
        return "question/create";
    }

    @GetMapping("/importAllQuestionToEs")
    @ResponseBody
    @ApiOperation("把数据库所有的问题导入es")
    public StrawResult importAllQuestionToEs(){
       return questionServiceApi.importAllQuestionFromDB();
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



    @PostMapping("/askQuestion")
    @ApiOperation("创建问题")
    @NoRepeatSubmit
    @ResponseBody
    public StrawResult create(@Validated @RequestBody  QuestionParam question, BindingResult bindingResult){
        System.err.println(question);
        if(bindingResult.hasErrors()){
            return new StrawResult().validateFailed(bindingResult);
        }
      boolean isSucess=questionService.create(question);
        if(isSucess){
            return new StrawResult().success();
        }else {
            return new StrawResult().failed("服务繁忙,请稍后重试!");
        }



    }



    @GetMapping("/detail/{id}")
    @ApiOperation("查看某个问题详情")
    public String datail(@PathVariable("id")Integer id,Model model){
       Question question= questionService.getQuestionDetailById(id);
        model.addAttribute("question",question);
        return "question/detail";
    }

    @PostMapping("/answer")
    @PreAuthorize("hasAuthority('/question/answer')")
    @ApiOperation("回答问题")
    public  String answer(@RequestParam("id")Integer id,@RequestParam("content")String content){
       Boolean isSuccess= questionService.answer(id,content);
       if(isSuccess){
           return "redirect:/question/detail/"+id;
       }else {
           return "redirect:/error";
       }
    }

    @PostMapping("/search")
    @ResponseBody
    public StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        return questionService.search(keyword,pageNum,pageSize);
    }

    @PostMapping("/findQuestionByTagId")
    @ResponseBody
    @ApiOperation("根据标签查找问题")
    public  StrawResult<PageInfo<Question>>  findQuestionByTagId(@RequestParam("tagId")Integer tagId,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        //0代表查询所有标签的问题
        if(tagId.intValue()==0){
            PageInfo<Question> pageInfo = questionService.selectPage(pageNum, pageSize);
            return new StrawResult<PageInfo<Question>>().success(pageInfo);
        }
        //否则按照标签查找问题
        PageInfo<Question> pageInfo = questionService.selectPage(tagId,pageNum, pageSize);
        return new StrawResult<PageInfo<Question>>().success(pageInfo);
    }

    @PostMapping("/findPersonalAllQuestions")
    @ResponseBody
    @ApiOperation("查找本人提出的所有问题")
    public StrawResult<PageInfo<Question>> findPersonalAllQuestions(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo = questionService.selectPersonalQuestion(pageNum, pageSize);
        return new StrawResult<PageInfo<Question>>().success(pageInfo);
    }


    @GetMapping("/questionManager.html")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String toQuestionManagerPage(){
        return "question/questionmanager";
    }


    @PostMapping("findQuestionByCondition")
    @ApiOperation("条件查询提问")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ResponseBody
    public StrawResult<PageInfo<Question>> findQuestionByCondition( QuestionQueryParam queryParam){
        PageInfo<Question> pageInfo =questionService.findQuestionByCondition(queryParam);
        return new StrawResult<PageInfo<Question>>().success(pageInfo);
    }

    @GetMapping("/setQuestionPublic/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为公开提问")
    public StrawResult setQuestionPublic(@PathVariable("id")Integer id){
       boolean isSuccess= questionService.setQuestionPublic(id);
       if(isSuccess){
           return new StrawResult().success("操作成功");
       }else {
           return new StrawResult().failed("操作失败");
       }
    }

    @PostMapping("/setQuestionPublic")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为公开提问")
    public StrawResult setQuestionPublic(@RequestParam("ids[]") Integer[] ids){
        boolean isSuccess= questionService.setQuestionPublic(ids);
        if(isSuccess){
            return new StrawResult().success("操作成功");
        }else {
            return new StrawResult().failed("操作失败");
        }
    }



    @GetMapping("/cancelQuestionPublic/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("取消公开提问")
    public StrawResult cancelQuestionPublic(@PathVariable("id")Integer id){
        boolean isSuccess= questionService.cancelQuestionPublic(id);
        if(isSuccess){
            return new StrawResult().success("操作成功");
        }else {
            return new StrawResult().failed("操作失败");
        }
    }
    @PostMapping("/cancelQuestionPublic")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("取消公开提问")
    public StrawResult cancelQuestionPublic(@RequestParam("ids[]") Integer[] ids){
        boolean isSuccess= questionService.cancelQuestionPublic(ids);
        if(isSuccess){
            return new StrawResult().success("操作成功");
        }else {
            return new StrawResult().failed("操作失败");
        }
    }


    @PostMapping("/findMyUnAnwerQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取未回答的问题列表")
    public StrawResult<PageInfo<Question>> findMyUnAnwerQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.findMyUnAnwerQuestion(pageNum,pageSize);
        return new StrawResult<PageInfo<Question>>().success(pageInfo);
    }

    @PostMapping("/findMyUnSolveQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取未解决的问题列表")
    public StrawResult<PageInfo<Question>> findMyUnSolveQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.findMyUnSolveQuestion(pageNum,pageSize);
        return new StrawResult<PageInfo<Question>>().success(pageInfo);
    }

    @PostMapping("/findMySolvedQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取已解决的问题列表")
    public StrawResult<PageInfo<Question>> findMySolvedQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.findMySolvedQuestion(pageNum,pageSize);
        return new StrawResult<PageInfo<Question>>().success(pageInfo);
    }

    @GetMapping("/setQuestionSolved/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为已解决")
    public StrawResult setQuestionSolved(@PathVariable("id")Integer id){
        boolean isSuccess= questionService.setQuestionSolved(id);
        if(isSuccess){
            return new StrawResult().success("操作成功");
        }else {
            return new StrawResult().failed("操作失败");
        }
    }
    @PostMapping("/setQuestionSolved")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为已解决")
    public StrawResult setQuestionSolved(@RequestParam("ids[]") Integer[] ids){
        boolean isSuccess= questionService.setQuestionSolved(ids);
        if(isSuccess){
            return new StrawResult().success("操作成功");
        }else {
            return new StrawResult().failed("操作失败");
        }
    }

    @PostMapping("/transferToTeacher")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("将问题转发给其他老师")
    public StrawResult transferToTeacher(@RequestParam("teacherIds[]") Integer[] teacherIds,@RequestParam("questionIds[]")Integer[] questionIds){
       boolean isSuccess= questionService.transferToTeacher(teacherIds,questionIds);
        if(isSuccess){
            return new StrawResult().success("操作成功");
        }else {
            return new StrawResult().failed("操作失败");
        }
    }

    @GetMapping("/tag_question.html")
    @ApiOperation("访问标签页问题")
    public String toTagQuestionPge(@RequestParam("tagId")Integer tagId,Model model){
        if(tagId==0){
            Tag tag=new Tag();
            tag.setId(0);
            tag.setName("全部");
            model.addAttribute("tag",tag);
        }else {
            Tag tag = tagService.getById(tagId);
            if(tag==null){throw  new BusinessException("该标签不存在");
            }
            model.addAttribute("tag",tag);
        }


        return "tag/tag_question";
    }


    @GetMapping("/search.html")
    @ApiOperation("搜索页面")
    public String toSearchPage(@RequestParam("keyword")String keyword,Model model){
        model.addAttribute("keyword",keyword);
        return "search/search";
    }

}
