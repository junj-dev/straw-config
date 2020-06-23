package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.R;
import cn.tedu.straw.common.constant.QuestionPublicStatus;
import cn.tedu.straw.common.constant.RoleName;
import cn.tedu.straw.portal.annotation.NoRepeatSubmit;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.config.GateWayUrlConfig;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.domian.param.QuestionUpdateParam;
import cn.tedu.straw.portal.domian.vo.QuestionInfoVO;
import cn.tedu.straw.portal.domian.vo.QuestionVO;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.*;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
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
import java.util.Arrays;
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
    @Resource
    private IRecommendQuestionService recommendQuestionService;
    @Resource
    private GateWayUrlConfig gateWayUrlConfig;
    @Resource
    private IUserCollectService userCollectService;
    private Gson gson=new Gson();

    @GetMapping("/create.html")
    public  String create(Model model){
        //查询出所有的标签
        List<Tag> tags = tagService.list();
        model.addAttribute("tags",tags);
        List<User> teachers = getAvalibleTeachers();
        model.addAttribute("teachers",teachers);
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        return "question/create";
    }

    @GetMapping("/importAllQuestionToEs")
    @ResponseBody
    @ApiOperation("把数据库所有的问题导入es")
    public R importAllQuestionToEs(){
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
    public R uploadImg(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        List<String> imgs = questionService.uploadImg(files, request);
        return R.success(imgs);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找问题")
    @ResponseBody
    public R getQuestionById(@PathVariable("id")Integer id){
        QuestionVO questionVO= questionService.getQuestionParamById(id);
      if(questionVO!=null){
          return R.success(questionVO);
      }
      return R.failed();

    }


    @PostMapping("/askQuestion")
    @ApiOperation("创建问题")
    @NoRepeatSubmit
    @ResponseBody
    public R insertQuestion(@Validated @RequestBody  QuestionParam question, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.validateFailed(bindingResult);
        }
        questionService.saveQuestion(question);
        return R.success();

    }

    @GetMapping("/detail/{id}")
    @ApiOperation("查看某个问题详情")
    public String datail(@PathVariable("id")Integer id,Model model){
       Question question= questionService.getQuestionDetailById(id);
        model.addAttribute("question",question);
        //相关提问
        List<Question> similarQuestions = recommendQuestionService.getSimilarQuestion(id);
        model.addAttribute("similarQuestions",similarQuestions);
        //问题被收藏数
        QueryWrapper collectQuery=new QueryWrapper();
        collectQuery.eq("question_id",id);
        int collectCount = userCollectService.count(collectQuery);
        model.addAttribute("collectCount",collectCount);
        //设置问题的相关信息
        QuestionInfoVO questionInfo=new QuestionInfoVO(question.getUserId().intValue()==getUseId().intValue()?true:false,
                getUseId(),getUserRoleNames().contains(RoleName.TEACHER)?RoleName.TEACHER:RoleName.STUDENT,question.getId(),question.getStatus()
                );
        model.addAttribute("questionInfo",gson.toJson(questionInfo));
        model.addAttribute("isMyQuestion",questionInfo.isMyQuestion());
        return "question/detail";
    }

    @GetMapping("/answers/{id}")
    @ResponseBody
    @ApiOperation("获取某个问题的回答")
    public R getAnswers(@PathVariable("id")Integer questionId){
        List<Answer> answers= questionService.getQuestionAnswerById(questionId);
        return R.success(answers);
    }



    @PostMapping("/answer")
    @PreAuthorize("hasAuthority('/question/answer')")
    @ApiOperation("回答问题")
    public  String answer(@RequestParam("id")Integer id,@RequestParam("content")String content){
        questionService.saveAnswer(id, content);
        return "redirect:"+gateWayUrlConfig.getUrl()+"/question/detail/"+id;

    }

    @PostMapping("/search")
    @ResponseBody
    public R<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        return questionService.search(keyword,pageNum,pageSize);
    }

    @PostMapping("/findQuestionByTagId")
    @ResponseBody
    @ApiOperation("根据标签查找问题")
    public R<PageInfo<Question>> findQuestionByTagId(@RequestParam("tagId")Integer tagId, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        //0代表查询所有标签的问题
        if(tagId.intValue()==0){
            PageInfo<Question> pageInfo = questionService.selectPage(pageNum, pageSize);
            return R.success(pageInfo);
        }
        //否则按照标签查找问题
        PageInfo<Question> pageInfo = questionService.selectPage(tagId,pageNum, pageSize);
        return R.success(pageInfo);
    }

    @PostMapping("/findPersonalAllQuestions")
    @ResponseBody
    @ApiOperation("查找本人提出的所有问题")
    public R<PageInfo<Question>> findPersonalAllQuestions(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo = questionService.selectPersonalQuestion(pageNum, pageSize);
        return R.success(pageInfo);
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
    public R<PageInfo<Question>> findQuestionByCondition(QuestionQueryParam queryParam){
        PageInfo<Question> pageInfo =questionService.findQuestionByCondition(queryParam);
        return R.success(pageInfo);
    }

    @GetMapping("/setQuestionPublic/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为公开提问")
    public R setQuestionPublic(@PathVariable("id")Integer id){
      questionService.updateQuestionPublicStatus(new Integer[]{id}, QuestionPublicStatus.PUBLIC.getStatus());
      return R.success();
    }

    @PostMapping("/setQuestionPublic")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为公开提问")
    public R setQuestionPublic(@RequestParam("ids[]") Integer[] ids){
        questionService.updateQuestionPublicStatus(ids, QuestionPublicStatus.PUBLIC.getStatus());
        return R.success();
    }



    @GetMapping("/cancelQuestionPublic/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("取消公开提问")
    public R cancelQuestionPublic(@PathVariable("id")Integer id){
        questionService.updateQuestionPublicStatus(new Integer[]{id}, QuestionPublicStatus.PRIVATE.getStatus());
        return R.success();

    }
    @PostMapping("/cancelQuestionPublic")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("取消公开提问")
    public R cancelQuestionPublic(@RequestParam("ids[]") Integer[] ids){
        questionService.updateQuestionPublicStatus(ids, QuestionPublicStatus.PRIVATE.getStatus());
        return R.success();

    }


    @PostMapping("/findMyUnAnwerQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取未回答的问题列表")
    public R<PageInfo<Question>> findMyUnAnwerQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.findMyUnAnwerQuestion(pageNum,pageSize);
        return R.success(pageInfo);
    }

    @PostMapping("/findMyUnSolveQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取未解决的问题列表")
    public R<PageInfo<Question>> findMyUnSolveQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.findMyUnSolveQuestion(pageNum,pageSize);
        return R.success(pageInfo);
    }

    @PostMapping("/findMySolvedQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取已解决的问题列表")
    public R<PageInfo<Question>> findMySolvedQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.findMySolvedQuestion(pageNum,pageSize);
        return R.success(pageInfo);
    }

    @GetMapping("/setQuestionSolved/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为已解决")
    public R setQuestionSolved(@PathVariable("id")Integer id){
        boolean isSuccess= questionService.setQuestionSolved(id);
        if(isSuccess){
            return R.success("操作成功");
        }else {
            return R.failed("操作失败");
        }
    }
    @PostMapping("/setQuestionSolved")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为已解决")
    public R setQuestionSolved(@RequestParam("ids[]") Integer[] ids){
        boolean isSuccess= questionService.setQuestionSolved(ids);
        if(isSuccess){
            return R.success("操作成功");
        }else {
            return R.failed("操作失败");
        }
    }

    @PostMapping("/transferToTeacher")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("将问题转发给其他老师")
    public R transferToTeacher(@RequestParam("teacherIds[]") Integer[] teacherIds, @RequestParam("questionIds[]")Integer[] questionIds){
       boolean isSuccess= questionService.transferToTeacher(teacherIds,questionIds);
        if(isSuccess){
            return R.success("操作成功");
        }else {
            return R.failed("操作失败");
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
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);

        return "tag/tag_question";
    }


    @GetMapping("/search.html")
    @ApiOperation("搜索页面")
    public String toSearchPage(@RequestParam("keyword")String keyword,Model model){
        model.addAttribute("keyword",keyword);
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        return "search/search";
    }


    @GetMapping("/edit/{id}")
    @ApiOperation("编辑问题")
    public String edit(@PathVariable("id")Integer id,Model model){
        model.addAttribute("id",id);
        List<Question> hotspotQuestions = recommendQuestionService.getHotspotQuestion();
        model.addAttribute("hotspotQuestions",hotspotQuestions);
        return "question/edit";
    }

    @PostMapping("/edit")
    @ApiOperation("修改问题")
    @NoRepeatSubmit
    @ResponseBody
    public R edit(@Validated @RequestBody QuestionUpdateParam question, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.validateFailed(bindingResult);
        }
        boolean isSucess=questionService.updateQuestion(question);
        if(isSucess){
            return R.success();
        }else {
            return R.failed("服务繁忙,请稍后重试!");
        }
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除提问")
    @ResponseBody
    public R delete(@PathVariable("id")Integer id){
       Boolean flag= questionService.deleteById(id);
       if(flag){
           return R.success();
       }else {
           return R.failed();
       }
    }

    @GetMapping("/collect/{id}")
    @ApiOperation("收藏问题")
    @ResponseBody
    public R collect(@PathVariable("id")Integer id){
       Boolean flag= questionService.collectQuestion(id);
       if(flag){
           return R.success();
       }else {
           return R.failed("系统繁忙，请稍后重试！");
       }
    }

    @GetMapping("/cancelCollect/{id}")
    @ApiOperation("取消收藏")
    @ResponseBody
    public R cancelCollect(@PathVariable("id")Integer id){
        Boolean flag= questionService.cancelCollectQuestion(id);
        if(flag){
            return R.success();
        }else {
            return R.failed("系统繁忙，请稍后重试！");
        }
    }

    @GetMapping("/checkCollectStatus/{id}")
    @ApiOperation("查看问题是否已收藏")
    @ResponseBody
    public R checkCollectStatus(@PathVariable("id")Integer id){
       Boolean isCollect= questionService.checkCollectStatus(id);
       return R.success(isCollect);
    }

}
