package cn.tedu.straw.portal.domian.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 问题封装的相关信息
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/9$ 23:08$
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoVO {
    //问题是否是本人提出的
    private boolean myQuestion;
    //用户id
    private Integer userId;
    //角色
    private String role;
    //问题id
    private Integer questionId;
    //问题状态
    private Integer questionStatus;

}
