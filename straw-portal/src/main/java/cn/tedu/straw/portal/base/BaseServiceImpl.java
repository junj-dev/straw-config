package cn.tedu.straw.portal.base;

import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T>{


	/**
	 * 获取登录用户名
	 * @return
	 */
	protected String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return currentUserName;
		}

		throw  new RuntimeException("服务繁忙，请稍后重试!");
	}

	/**
	 * 获取登录用户昵称
	 * @return
	 */
	protected String getUserNickname() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = (User)authentication.getPrincipal();
			return user.getNickname();
		}

		throw  new RuntimeException("服务繁忙，请稍后重试!");
	}

	/**
	 * 获取登录用户Id
	 * @return
	 */
	protected Integer getUseId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = (User)authentication.getPrincipal();
			return user.getId();
		}
		throw  new RuntimeException("服务繁忙，请稍后再试!");
	}

	/**
	 * 获取登录用户所拥有的所有角色名称
	 * @return
	 */
	protected List<String> getUserRoleNames() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = (User)authentication.getPrincipal();
			List<Role> roles= user.getRoles();
			if(CollectionUtils.isEmpty(roles)){
				log.error("用户id为:"+user.getId()+"的用户信息丢失，请检查数据库");
				throw new RuntimeException("服务繁忙，请稍后再试!");
			}
			List<String> roleNames=roles.stream().map(Role::getName).collect(Collectors.toList());
			return roleNames;
		}
		throw  new RuntimeException("服务繁忙，请稍后再试!");
	}

	
	
	/**
	 * 利用反射设置参数,创建者，创建时间
	 * @param object
	 */
	public void setCreateParam(Object object) {
		
		List<Field> fieldList = new ArrayList<>() ;
		Class<?> tempClass = object.getClass();
		while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
		      fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
		      tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
		}
		
		try {
				if(CollectionUtils.isEmpty(fieldList)) {return;}
				for (Field field : fieldList) {
					field.setAccessible(true);

					if("createby".equals(field.getName())){
						field.set(object, getUsername());
					}
					if("createtime".equals(field.getName())){
						field.set(object, new Date());
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
