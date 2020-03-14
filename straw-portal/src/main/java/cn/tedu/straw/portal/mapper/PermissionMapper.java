package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChenHaiBao
 * @since 2020-03-13
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<GrantedAuthority> getPermissionsByRoleId(Integer roleId);

}
