package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-03
 */
public interface UserMapper extends BaseMapper<User> {

    User findUserWithRoleByUserName(String username);

    int updatePasswordByUsername(String username,String password);

    UserInfoVO findById(Integer useId);
}
