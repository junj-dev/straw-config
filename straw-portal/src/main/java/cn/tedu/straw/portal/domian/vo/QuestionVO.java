package cn.tedu.straw.portal.domian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 编辑问题参数封装
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/16$ 11:28$
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {

    @Size(min = 3,max = 50,message = "标题字数必须控制在3到50字之间！")
    private  String title;
    @NotEmpty(message = "标签不能为空")
    private List<String> tagNames=new ArrayList<>();
    @NotEmpty(message = "回答问题的老师不能为空")
    private  List<String> teacherNames=new ArrayList<>();
    @NotEmpty(message = "问题的内容不能为空!")
    private  String content;
}
