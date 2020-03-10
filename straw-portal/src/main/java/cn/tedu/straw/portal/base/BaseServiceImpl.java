package cn.tedu.straw.portal.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T>{

	@Autowired
	protected HttpServletRequest request;
	
	protected String getUsername() {
		String username= (String) request.getAttribute("username");
		if(StringUtils.isEmpty(username)) {
			throw new RuntimeException("账号不存在");
		}
		return username;
	}
	protected Integer getUseId() {
		Integer userId= (Integer) request.getAttribute("userId");
		return userId;
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
