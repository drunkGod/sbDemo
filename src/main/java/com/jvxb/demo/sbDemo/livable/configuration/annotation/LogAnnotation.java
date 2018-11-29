package com.jvxb.demo.sbDemo.livable.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志切面注解
 * 
 * @author 抓娃小兵
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LogAnnotation {
	/** 操作描述 */
	String operate();

	/** 是否存入数据库 */
	boolean isSave() default true;
}
