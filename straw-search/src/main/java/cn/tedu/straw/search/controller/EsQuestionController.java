package cn.tedu.straw.search.controller;

import cn.tedu.straw.commom.CommonPage;
import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.search.model.EsQuestion;
import cn.tedu.straw.search.service.IEsQuestionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 问题控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:52$
 * @Version: 1.0
 */
@RestController
@RequestMapping("/esQuestion")
public class EsQuestionController {

    @Resource
    private IEsQuestionService questionService;


    @GetMapping("/importAllQuestion")
    public StrawResult importAllQuestionFromDB(){
        int count = questionService.importAllQuestionFromDB();
        return StrawResult.builder().build().success("成功导入"+count+"条数据");

    }

    /**
     * 简单搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/search")
    public StrawResult<CommonPage<EsQuestion>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize){

        Page<EsQuestion> esQuestionPage = questionService.search(keyword,pageNum,pageSize);

        return StrawResult.<CommonPage<EsQuestion>>builder().build().success(CommonPage.restPage(esQuestionPage));
    }

}
