package cn.tedu.straw.portal.service;

import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.domian.param.RegisterParam;
import cn.tedu.straw.portal.domian.param.ResetPasswordParam;
import cn.tedu.straw.portal.domian.param.TeacherCreateForm;
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

    void register(RegisterParam param);

    void resetPassword(ResetPasswordParam param);

    void createTeacher(TeacherCreateForm teacher);
}
