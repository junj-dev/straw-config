package cn.tedu.straw.search.controller;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.api.EsQuestionServiceApi;
import cn.tedu.straw.search.service.IEsQuestionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: es问题控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 14:52$
 * @Version: 1.0
 */
@RestController
public class EsQuestionController implements  EsQuestionServiceApi{



    @Resource
    private IEsQuestionService questionService;


    @Override
    public StrawResult importAllQuestionFromDB(){
        int count = questionService.importAllQuestionFromDB();
        return new StrawResult().success("成功导入"+count+"条数据");

    }

    /**
     * 搜索所有问题
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public StrawResult<CommonPage<EsQuestion>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize){

        CommonPage<EsQuestion> esQuestionPage = questionService.search(keyword,pageNum,pageSize);

        return new StrawResult().success(esQuestionPage);
    }

    /**
     * 查找开放问题，包括自己的问题
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
   @Override
    public StrawResult<CommonPage<EsQuestion>> searchOpenQuestion(@RequestParam(required = false,value = "keyword") String keyword,
                                                      @RequestParam(required = false, defaultValue = "1",value = "pageNum") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5",value = "pageSize") Integer pageSize,
                                                      @RequestParam(required = true,value = "userId")Integer userId,
                                                      @RequestParam(required = true,value = "publicStatus")Integer publicStatus){

       CommonPage<EsQuestion> esQuestionPage = questionService.searchByUserIdAndPublicStatus(keyword,pageNum,pageSize,userId,publicStatus);

        return new StrawResult().success(esQuestionPage);
    }


   @Override
    public boolean saveQuestion(@RequestBody EsQuestion question){
        System.out.println("question:"+question);
         return questionService.insert(question);
    }

    @Override
    public boolean deleteQuestion(@RequestParam("id") Integer id) {

        return questionService.deleteQuestionById(id);
    }


}
