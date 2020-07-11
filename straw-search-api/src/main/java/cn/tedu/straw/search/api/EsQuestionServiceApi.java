package cn.tedu.straw.search.api;

import cn.tedu.straw.common.CommonPage;
import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.model.EsQuestion;
import cn.tedu.straw.search.api.fallback.EsQuestionServiceClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/17$ 16:44$
 * @Version: 1.0
 */
@FeignClient(name = "straw-search",
            fallback = EsQuestionServiceClientFallBack.class)
public interface EsQuestionServiceApi {

    /**
     * 把数据库中的问题全部导入搜索引擎
     *
     * @return
     */
    @GetMapping("/importAllQuestion")
    public R importAllQuestionFromDB();


    /**
     * 关键字搜索所有的问题
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
     @RequestMapping("/esQuestion/search")
     R<CommonPage<EsQuestion>> search(@RequestParam(required = false) String keyword,
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
      R<CommonPage<EsQuestion>> searchOpenQuestion(@RequestParam(required = false,value = "keyword") String keyword,
                                                                   @RequestParam(required = false, defaultValue = "1",value = "pageNum") Integer pageNum,
                                                                   @RequestParam(required = false, defaultValue = "5",value = "pageSize") Integer pageSize,
                                                                   @RequestParam(required = true,value = "userId")Integer userId,
                                                                   @RequestParam(required = true,value = "publicStatus")Integer publicStatus);


    /**
     * 保存一个问题到es
     * @param question
     * @return
     */
    @PostMapping("/esQuestion/save")
    boolean saveQuestion(@RequestBody  EsQuestion question);

    @GetMapping("/esQuestion/delete")
    boolean deleteQuestion(@RequestParam("id")Integer id);


}

