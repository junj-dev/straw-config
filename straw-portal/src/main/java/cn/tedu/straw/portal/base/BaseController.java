package cn.tedu.straw.portal.base;

import cn.tedu.straw.common.R;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class BaseController {

	@Resource
	private IUserService userService;



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
	protected User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = (User)authentication.getPrincipal();
			return user;
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
	 * 获取在职的老师
	 * @return
	 */
	protected List<User> getAvalibleTeachers() {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("enabled", true);
		//type为true代表回答问题的老师
		queryWrapper.eq("type", true);
		return userService.list(queryWrapper);
	}


	protected R toAjax(int rows)
	{
		return rows > 0 ? R.success() : R.failed();
	}

	protected R toAjax(boolean result)
	{
		return result ? R.success() : R.failed();
	}


}
