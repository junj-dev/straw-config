package cn.tedu.straw.portal.controller;

import cn.tedu.straw.commom.CommonPage;
import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.portal.api.EsQuestionServiceApi;
import cn.tedu.straw.portal.model.EsQuestion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: java类作用描述
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 16:54$
 * @Version: 1.0
 */
@RestController
public class EsQuestionServiceConsumeController  implements EsQuestionServiceApi {

    @Resource
    private  EsQuestionServiceApi  questionService;


    @Override
    public StrawResult<CommonPage<EsQuestion>> search(String keyword, Integer pageNum, Integer pageSize) {
        return questionService.search(keyword,pageNum,pageSize);
    }
}
