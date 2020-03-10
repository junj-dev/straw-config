package cn.tedu.straw.portal.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BaseController {

	

	public List<String> getErrorInfo(BindingResult bindingResult){
		List<String> errorList=new ArrayList<String>();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		for (ObjectError error : allErrors) {
			errorList.add(error.getDefaultMessage());
		}
		
		
		return errorList;
		
	}

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
	 * 利用反射设置shopUserId,创建者，创建时间
	 * @param object
	 */
	public void setCreateParam(Object object) {


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
