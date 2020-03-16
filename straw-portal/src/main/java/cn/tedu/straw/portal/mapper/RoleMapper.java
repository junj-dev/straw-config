package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-13
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findAllRoleByUserId(Integer userId);

}
