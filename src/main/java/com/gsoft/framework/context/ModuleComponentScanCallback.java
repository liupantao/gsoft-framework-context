package com.gsoft.framework.context;

import java.util.LinkedHashSet;
import java.util.Set;

import com.gsoft.framework.context.annotation.Module;

/**
 * 模块扫描回调类
 * 
 * @author LiuPeng
 * 
 */
public class ModuleComponentScanCallback implements ModuleScanCallback {

	private Set<String> moduleBasePackage = new LinkedHashSet<String>();

	private Set<String> moduleWebPackage = new LinkedHashSet<String>();

	private Set<String> moduleEntityPackage = new LinkedHashSet<String>();

	private Set<String> connectPackageScan(String basePackage, String[] scanPackages) {
		Set<String> packageSet = new LinkedHashSet<String>();
		for (String scanPackage : scanPackages) {
			packageSet.add(basePackage + "." + scanPackage);
		}
		return packageSet;
	}

	@Override
	public void doScan(Module module, String basePackage) {

		moduleBasePackage.addAll(connectPackageScan(basePackage, module.scanPackages()));
		moduleWebPackage.addAll(connectPackageScan(basePackage, module.mvcPackages()));
		moduleEntityPackage.addAll(connectPackageScan(basePackage, module.entityPackages()));

		ModuleConfig config = new ModuleConfig();
		config.setCaption(module.caption());
		config.setCode(module.name());
		config.setBasePackage(basePackage);
		config.setDaoPackages(module.daoPackages());
		config.setEntityPackages(module.entityPackages());
		config.setEntityManagerFactory(module.entityManagerFactory());
		ModuleFactory.getInstance().regesiterModule(config);
	}

	public Set<String> getModuleBasePackage() {
		return moduleBasePackage;
	}

	public Set<String> getModuleWebPackage() {
		return moduleWebPackage;
	}

	public Set<String> getModuleEntityPackage() {
		return moduleEntityPackage;
	}
}
