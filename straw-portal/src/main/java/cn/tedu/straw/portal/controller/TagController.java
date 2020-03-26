package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.util.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 标签控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/20$ 16:31$
 * @Version: 1.0
 */
@Controller
@RequestMapping("/tag")
public class TagController extends BaseController {
    @Resource
    private ITagService tagService;

    @GetMapping("/findAllTags")
    @ResponseBody
    public StrawResult<List<Tag>> loadAllTags(){
        List<Tag> tags = tagService.list();
        return new StrawResult().success(tags);
    }
}
