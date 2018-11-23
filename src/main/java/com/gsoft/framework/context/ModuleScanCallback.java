package com.gsoft.framework.context;

import com.gsoft.framework.context.annotation.Module;

/**
 * 模块扫描回调接口
 * @author LiuPeng
 * @date 2017年10月17日
 *  
 */
public interface ModuleScanCallback {

	/** 
	 * 执行扫描
	 * @param moduleConfig
	 * @param moduleBasePackage 
	 */
	void doScan(Module moduleConfig,String moduleBasePackage);

}
