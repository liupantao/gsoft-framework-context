/**
 * 
 */
package com.gsoft.framework.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author LiuPeng
 * 
 */
public class ModuleFactory {
	private final static Log logger = LogFactory.getLog(ModuleFactory.class);

	private static final Map<String, ModuleConfig> MODULES_MAP = new HashMap<String, ModuleConfig>();

	private boolean complete = false;

	private static ModuleFactory instance = new ModuleFactory();

	private ModuleFactory() {

	}

	public static ModuleFactory getInstance() {
		return instance;
	}

	/**
	 * 注册模型
	 */
	public void regesiterModule(ModuleConfig config) {
		String moduleCode = config.getCode();
		if (!MODULES_MAP.containsKey(moduleCode)) {
			logger.info("注册模块：" + config.getCaption() + "(" + moduleCode + ")");
			MODULES_MAP.put(moduleCode, config);
		}
	}

	public Map<String, ModuleConfig> getModules() {
		return MODULES_MAP;
	}

	public String[] getEntityPackagesByEntityManagerFactory(String entityManagerFactory) {
		List<String> scanEntityPackages = new ArrayList<String>();
		for (Map.Entry<String, ModuleConfig> entry : MODULES_MAP.entrySet()) {
			if(entityManagerFactory.equals(entry.getValue().getEntityManagerFactory())){
				String basePackage = entry.getValue().getBasePackage();
				String[] entityPackages = entry.getValue().getEntityPackages();
				for(String entityPackage:entityPackages){
					scanEntityPackages.add(basePackage + "." + entityPackage);
				}
			}
		}
		if(scanEntityPackages.size()==0){
			scanEntityPackages.add("xxxx.xxxxx.xxxx.xxxx");
		}
		return scanEntityPackages.toArray(new String[scanEntityPackages.size()]);
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
