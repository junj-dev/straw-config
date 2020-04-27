package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IRecommendQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 推荐相关信息控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/17$ 21:39$
 * @Version: 1.0
 */
@RestController
@RequestMapping("/recommend")
@Api(value = "推荐问题控制器",tags = "推荐问题控制器")
public class RecommendQuestionController {


    @Resource
    private IRecommendQuestionService recommendQuestionService;

    @GetMapping("/getHotspotQuestion")
    @ApiOperation("获取热点问题")
    public StrawResult<List<Question>> getHotspotQuestion(){
      List<Question> questions= recommendQuestionService.getHotspotQuestion();
      return new StrawResult<List<Question>>().success(questions);
    }


//    @GetMapping("/getSimilarQuestion")
//    @ApiOperation("获取相似问题")
//    public StrawResult getSimilarQuestion(){
//       List<Question> questions= recommendQuestionService.getSimilarQuestion();
//        return new StrawResult<List<Question>>().success(questions);
//    }


}
