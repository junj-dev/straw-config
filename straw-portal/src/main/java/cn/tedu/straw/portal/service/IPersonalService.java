package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.domian.vo.MyInfo;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserInfoVO;

/**
 * @Description: 关于个人有关的业务类接口
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/10$ 22:37$
 * @Version: 1.0
 */
public interface IPersonalService {
    MyInfo getMyInfo();

    UserInfoVO getUserInfo();

    boolean resetMyInfo(UserInfoVO userInfo);

    boolean resetpasswd(String newpasswd);

    boolean checkPasswd(String oldpasswd);
}
