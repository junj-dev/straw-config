package cn.tedu.straw.portal.base;

import cn.tedu.straw.portal.security.StrawUserDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
			StrawUserDetails user = (StrawUserDetails)authentication.getPrincipal();
			return user.getNickName();
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
			StrawUserDetails user = (StrawUserDetails)authentication.getPrincipal();
			return user.getId();
		}
		throw  new RuntimeException("服务繁忙，请稍后再试!");
	}

	/**
	 * 获取登录用户的角色
	 * @return
	 */
	protected String getUserRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			StrawUserDetails user = (StrawUserDetails)authentication.getPrincipal();
			return user.getRole();
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
