/**
 * 
 */
package com.gsoft.framework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author LiuPeng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Module {

	String name();// 模块名

	String caption();// 模块描述
	
	String[] daoPackages() default { "dao" };// dao包

	String[] entityPackages() default { "entity" };// 模块实体包
	
	String entityManagerFactory() default "entityManagerFactory";
	
	String[] mvcPackages() default { "web" };// mvc 扫描的包

	String[] scanPackages() default { "conf", "service", "web.attr" };// 自动扫描的包

}
