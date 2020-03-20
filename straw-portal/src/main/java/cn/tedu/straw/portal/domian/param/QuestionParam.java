package cn.tedu.straw.portal.domian.param;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description: 创建问题参数封装
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 11:28$
 * @Version: 1.0
 */
@Data
public class QuestionParam {

    @Size(min = 1,max = 50,message = "标题字数必须在50个字以内！")
    private  String title;
    @NotEmpty(message = "标签不能为空")
    private  Integer[] tags;
    @NotEmpty(message = "问题的内容不能为空!")
    private  String content;
}
