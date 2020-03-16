package cn.tedu.straw.portal.base;

import cn.tedu.straw.portal.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BaseController {



	protected List<String> getErrorInfo(BindingResult bindingResult){
		List<String> errorList=new ArrayList<String>();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		for (ObjectError error : allErrors) {
			errorList.add(error.getDefaultMessage());
		}
		
		
		return errorList;
		
	}

	protected String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return currentUserName;
		}

		throw  new RuntimeException("服务繁忙，请稍后重试!");
	}
	protected Integer getUseId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = (User)authentication.getPrincipal();
			return user.getId();
		}
		throw  new RuntimeException("服务繁忙，请稍后再试!");
	}


	/**
	 * 利用反射设置shopUserId,创建者，创建时间
	 * @param object
	 */
	protected void setCreateParam(Object object) {


		try {
			Class<? extends Object> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			if(fields.length==0) {return;}
			for (Field field : fields) {
				field.setAccessible(true);
				if("shopUserId".equals(field.getName())){
					if(field.getType() == Integer.class) {
						field.set(object, getUseId());
					}else if(field.getType() == Long.class){
						field.set(object, (long)getUseId());
					}

				}
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
