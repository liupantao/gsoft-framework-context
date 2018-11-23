package com.gsoft.framework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 
 * @author LiuPeng 模块扫描
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ModuleScannerRegistrar.class)
public @interface ModuleScan {

	public static final String SCAN_WEB = "scanWeb";
	public static final String SCAN_ENTITY = "scanEntity";
	public static final String SCAN_FRAMEWORKWWEB = "scanFrameworkweb";
	public static final String EXCLUDE = "exclude";

	/** 
	 * 是否扫描web模块,默认值true
	 * @return 
	 */
	boolean scanWeb() default true;

	/** 
	 * 是否扫描entity模块,默认值true
	 * @return 
	 */
	boolean scanEntity() default true;

	/** 
	 * 是否扫描 框架 web模块,默认值true
	 * @return 
	 */
	boolean scanFrameworkweb() default true;

	/** 
	 * 排除package(暂未启用)
	 * @return 
	 */
	String[] exclude() default {};

}
