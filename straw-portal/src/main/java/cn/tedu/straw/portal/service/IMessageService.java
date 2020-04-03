package cn.tedu.straw.portal.service;

import cn.tedu.straw.common.StrawResult;
import com.aliyuncs.CommonResponse;

/**
 * @Description: 短信发送业务接口
 * @Author: ChenHaiBao
 * @CreateDate: 2020/3/28$ 0:33$
 * @Version: 1.0
 */
public interface IMessageService {
    StrawResult sendRegisterCode(String phone,String inviteCode);

    StrawResult sendResetPasswordCode(String phone);
}
