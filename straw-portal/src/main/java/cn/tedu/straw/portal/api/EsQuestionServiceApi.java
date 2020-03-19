package cn.tedu.straw.portal.api;

import cn.tedu.straw.commom.CommonPage;
import cn.tedu.straw.commom.StrawResult;
import cn.tedu.straw.portal.fallback.EsQuestionServiceClientFallBack;
import cn.tedu.straw.portal.model.EsQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 16:44$
 * @Version: 1.0
 */
@FeignClient(name = "straw-search",
            fallback = EsQuestionServiceClientFallBack.class)
public interface EsQuestionServiceApi {

     @RequestMapping("/esQuestion/search")
     StrawResult<CommonPage<EsQuestion>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize);


     /**
      * 查找开放问题，包括自己的问题
      * @param keyword
      * @param pageNum
      * @param pageSize
      * @return
      */
     @GetMapping("/esQuestion/searchOpenQuestion")
      StrawResult<CommonPage<EsQuestion>> searchOpenQuestion(@RequestParam(required = false,value = "keyword") String keyword,
                                                                   @RequestParam(required = false, defaultValue = "1",value = "pageNum") Integer pageNum,
                                                                   @RequestParam(required = false, defaultValue = "5",value = "pageSize") Integer pageSize,
                                                                   @RequestParam(required = true,value = "userId")Integer userId,
                                                                   @RequestParam(required = true,value = "publicStatus")Integer publicStatus);
}

