package cn.tedu.straw.portal.domian.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @Description: 创建老师表单封装类
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/30$ 13:05$
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherCreateForm {
    @Size(min = 3,max = 20,message = "用户名字数控制在3到20之间")
    private  String username;
    @Size(min=6,max = 30,message = "密码位数控制在6到30之间")
    private  String password;
    @Size(min=3,max = 30,message = "昵称字数控制在3到30之间")
    private  String nickname;

}
