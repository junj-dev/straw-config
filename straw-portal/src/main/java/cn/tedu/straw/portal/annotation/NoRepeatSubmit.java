package cn.tedu.straw.portal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交注解
 */
//作用到方法上
@Target(ElementType.METHOD)
//运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {
}
