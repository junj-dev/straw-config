package cn.tedu.straw.portal.domian.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @Description: 注册新用户参数
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/30$ 12:59$
 * @Version: 1.0
 */
@Data
public class RegisterParam {

    @NotEmpty
    private  String  phone;
    @Size(min = 4,max = 4,message = "验证码为4位数")
    private  String  code;
    @Size(min = 2,max = 20,message = "昵称字数必须在2-20字之间")
    private  String  nickname;
    @Size(min=6,max = 20,message = "密码字数为6-20之间")
    private  String  password;
}
