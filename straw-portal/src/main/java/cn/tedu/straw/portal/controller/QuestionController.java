package cn.tedu.straw.portal.controller;


import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.R;
import cn.tedu.straw.common.enums.QuestionPublicStatusEnum;
import cn.tedu.straw.common.constant.RoleName;
import cn.tedu.straw.common.enums.QuestionStatusEnum;
import cn.tedu.straw.portal.annotation.NoRepeatSubmit;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.config.GateWayUrlConfig;
import cn.tedu.straw.portal.domian.param.QuestionParam;
import cn.tedu.straw.portal.domian.param.QuestionUpdateParam;
import cn.tedu.straw.portal.domian.vo.QuestionInfoVO;
import cn.tedu.straw.portal.domian.vo.QuestionVO;
import cn.tedu.straw.portal.exception.BusinessException;
import cn.tedu.straw.portal.exception.PageNotExistException;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.service.*;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
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
        QuestionInfoVO questionInfo=new QuestionInfoVO();
        questionInfo.setMyQuestion(question.getUserId().intValue()==getUseId().intValue()?true:false)
            .setUserId(getUseId())
            .setRole(getUserRoleNames().contains(RoleName.TEACHER)?RoleName.TEACHER:RoleName.STUDENT)
            .setQuestionId(question.getId())
            .setQuestionStatus(question.getStatus());

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
            PageInfo<Question> pageInfo = questionService.listQuestions(pageNum, pageSize);
            return R.success(pageInfo);
        }
        //否则按照标签查找问题
        PageInfo<Question> pageInfo = questionService.listQuestions(tagId,pageNum, pageSize);
        return R.success(pageInfo);
    }

    @PostMapping("/findPersonalAllQuestions")
    @ResponseBody
    @ApiOperation("查找本人提出的所有问题")
    public R<PageInfo<Question>> findPersonalAllQuestions(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo = questionService.listPersonalQuestions(pageNum, pageSize);
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
        PageInfo<Question> pageInfo =questionService.getQuestionByCondition(queryParam);
        return R.success(pageInfo);
    }

    @GetMapping("/setQuestionPublic/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为公开提问")
    public R setQuestionPublic(@PathVariable("id")Integer id){
        Question question=new Question();
        question.setId(id).setPublicStatus(QuestionPublicStatusEnum.PUBLIC.getStatus());
        return toAjax(questionService.updateById(question));
    }

    @PostMapping("/setQuestionPublic")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为公开提问")
    public R setQuestionPublic(@RequestParam("ids[]") Integer[] ids){
        Question question=new Question();
        question.setPublicStatus(QuestionPublicStatusEnum.PUBLIC.getStatus());
        questionService.updateQuestion(ids,question);
        return R.success();

    }



    @GetMapping("/cancelQuestionPublic/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("取消公开提问")
    public R cancelQuestionPublic(@PathVariable("id")Integer id){
        Question question=new Question();
        question.setId(id).setPublicStatus(QuestionPublicStatusEnum.PRIVATE.getStatus());
        return toAjax(questionService.updateById(question));

    }
    @PostMapping("/cancelQuestionPublic")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("取消公开提问")
    public R cancelQuestionPublic(@RequestParam("ids[]") Integer[] ids){
        Question question=new Question();
        question.setPublicStatus(QuestionPublicStatusEnum.PRIVATE.getStatus());
        questionService.updateQuestion(ids,question);
        return R.success();

    }


    @PostMapping("/findMyUnAnwerQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取未回答的问题列表")
    public R<PageInfo<Question>> findMyUnAnwerQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.getMyUnAnwerQuestion(pageNum,pageSize);
        return R.success(pageInfo);
    }

    @PostMapping("/findMyUnSolveQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取未解决的问题列表")
    public R<PageInfo<Question>> findMyUnSolveQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.getMyUnSolveQuestion(pageNum,pageSize);
        return R.success(pageInfo);
    }

    @PostMapping("/findMySolvedQuestion")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("老师获取已解决的问题列表")
    public R<PageInfo<Question>> findMySolvedQuestion(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageInfo<Question> pageInfo =questionService.getMySolvedQuestion(pageNum,pageSize);
        return R.success(pageInfo);
    }

    @GetMapping("/setQuestionSolved/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为已解决")
    public R setQuestionSolved(@PathVariable("id")Integer id){
        Question question=new Question();
        question.setId(id).setStatus(QuestionStatusEnum.SOLVED.getStatus());
        return toAjax(questionService.updateById(question));

    }
    @PostMapping("/setQuestionSolved")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("提问设置为已解决")
    public R setQuestionSolved(@RequestParam("ids[]") Integer[] ids){
        Question question=new Question();
        //问题设置为已解决
        question.setStatus(QuestionStatusEnum.SOLVED.getStatus());
        questionService.updateQuestion(ids,question);
        return R.success();

    }

    @PostMapping("/transferToTeacher")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ApiOperation("将问题转发给其他老师")
    public R transferToTeacher(@RequestParam("teacherIds[]") Integer[] teacherIds, @RequestParam("questionIds[]")Integer[] questionIds){
       questionService.transferToTeacher(teacherIds,questionIds);
       return R.success();
    }

    @GetMapping("/tag_question.html")
    @ApiOperation("访问标签页问题")
    public String toTagQuestionPge(@RequestParam("tagId")Integer tagId,Model model){
        if(tagId==0){
            Tag tag=new Tag();
            tag.setId(0).setName("全部");
            model.addAttribute("tag",tag);
        }else {
            Tag tag = tagService.getById(tagId);
            if(tag==null){throw  new PageNotExistException("该标签不存在");
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
        //只能修改本用户提出的问题
        Question question = questionService.getById(id);
        if(question==null){
            throw new PageNotExistException("不存在该问题记录！");
        }
        if(question.getUserId().intValue()!=getUseId().intValue()){
            throw new AccessDeniedException("抱歉,没有该权限！");
        }
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
       questionService.updateQuestion(question);
        return R.success();

    }

    @GetMapping("/delete/{id}")
    @ApiOperation("删除提问")
    @ResponseBody
    public R deleteQuestion(@PathVariable("id")Integer id){
        if(id==null){
            return R.failed("id参数不能为空");
        }
        Question question = new Question();
        question.setId(id).setDeleteStatus(true);
        return toAjax(questionService.updateById(question));
    }

    @GetMapping("/collect/{id}")
    @ApiOperation("收藏问题")
    @ResponseBody
    public R collectQuetion(@PathVariable("id")Integer id){
        if(id==null){
            return R.failed("id参数不能为空");
        }
        //先判断该问题是否已收藏
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", getUseId());
        queryWrapper.eq("question_id", id);
        List<UserCollect> userCollect2 = userCollectService.list(queryWrapper);
        //只有该收藏不存在时才处理
        if (!CollectionUtils.isEmpty(userCollect2)) {
            return R.failed("该问题已收藏");
        }
        UserCollect userCollect = new UserCollect(getUseId(), id, new Date());
        return toAjax(userCollectService.save(userCollect));

    }

    @GetMapping("/cancelCollect/{id}")
    @ApiOperation("取消收藏")
    @ResponseBody
    public R cancelCollect(@PathVariable("id")Integer id){
        if(id==null){
            return R.failed("id参数不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", getUseId());
        queryWrapper.eq("question_id", id);
        //删除该收藏
        return toAjax(userCollectService.remove(queryWrapper));
    }

    @GetMapping("/checkCollectStatus/{id}")
    @ApiOperation("查看问题是否已收藏")
    @ResponseBody
    public R checkCollectStatus(@PathVariable("id")Integer id){
       return toAjax(questionService.checkCollectStatus(id));
    }

}
