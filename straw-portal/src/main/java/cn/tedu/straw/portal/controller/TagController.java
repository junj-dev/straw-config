package cn.tedu.straw.portal.controller;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.base.BaseController;
import cn.tedu.straw.portal.domian.vo.TagVO;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/findAllTagNames")
    @ResponseBody
    public StrawResult<List<String>> findAllTagNames(){
        List<Tag> tags = tagService.list();
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());
        return new StrawResult().success(tagNames);
    }

    @GetMapping("/findAllTagVos")
    @ResponseBody
    public StrawResult<List<TagVO>> loadAllTagVos(){
        List<Tag> tags = tagService.list();
        if(!CollectionUtils.isEmpty(tags)){
            List<TagVO> tagVOS = tags.stream().map(t -> {
                TagVO vo = new TagVO();
                vo.setText(t.getName());
                vo.setValue(t.getId());
                return vo;
            }).collect(Collectors.toList());
            return new StrawResult().success(tagVOS);
        }
        return new StrawResult().success(null);
    }
}
