package cn.tedu.straw.portal.service;

import cn.tedu.straw.common.StrawResult;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.domian.param.ResetPasswordParam;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-03
 */
public interface IUserService extends IService<User> {

    StrawResult register(RegisterParam param);

    StrawResult resetPassword(ResetPasswordParam param);
}
