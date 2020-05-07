package cn.tedu.straw.portal.domian.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description: 修改问题参数封装
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 11:28$
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionUpdateParam {

    @NotNull(message = "id不能为空")
    private Integer id;
    @Size(min = 3,max = 50,message = "标题字数必须控制在3到50字之间！")
    private  String title;
    @NotEmpty(message = "标签不能为空")
    private  String[] tagNames;
    @NotEmpty(message = "回答问题的老师不能为空")
    private  String[] teacherNames;
    @NotEmpty(message = "问题的内容不能为空!")
    private  String content;
}
