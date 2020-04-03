package cn.tedu.straw.portal.domian.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @Description: 忘记密码重置密码提交参数
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/31$ 9:34$
 * @Version: 1.0
 */
@Data
public class ResetPasswordParam {
    @NotEmpty
    private  String  phone;
    @Size(min = 4,max = 4,message = "验证码为4位数")
    private  String  code;
    @Size(min=6,max = 20,message = "密码字数为6-20之间")
    private  String  password;
}
