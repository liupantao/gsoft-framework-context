/**
 *    Copyright 2010-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.gsoft.framework.context.annotation;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import com.gsoft.framework.context.ModuleComponentScanCallback;
import com.gsoft.framework.context.ModuleFactory;
import com.gsoft.framework.context.ModuleScanHelper;

/**
 * ModuleScanner模块加载
 * 
 * @author LiuPeng
 * 
 */
public class ModuleScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	private static final String BEAN = EntityScanPackages.class.getName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		if(ModuleFactory.getInstance().isComplete()){
			return;
		}
		AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata
				.getAnnotationAttributes(ModuleScan.class.getName()));
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, true);
		scanner.setResourceLoader(resourceLoader);

		ModuleComponentScanCallback callback = new ModuleComponentScanCallback();

		ModuleScanHelper.scanModuleConfig(callback);
		
		ModuleFactory.getInstance().setComplete(true);
		
		Set<String> basePackages = new LinkedHashSet<String>();

		if (annoAttrs.getBoolean(ModuleScan.SCAN_WEB)) {
			basePackages.addAll(callback.getModuleWebPackage());
		}
		if (annoAttrs.getBoolean(ModuleScan.SCAN_FRAMEWORKWWEB)) {
			basePackages.add("com.gsoft.framework.core.web");
		}

		basePackages.addAll(callback.getModuleBasePackage());

		// 扫描basePackages
		if (basePackages.size() > 0) {
			scanner.scan(StringUtils.toStringArray(basePackages));
		}

		if (annoAttrs.getBoolean(ModuleScan.SCAN_ENTITY)) {
			scanEntity(registry, callback.getModuleEntityPackage());
		}
		
		//TODO exclude
	}

	/**
	 * 扫描实体
	 * 
	 * @param registry
	 * @param entityPackages
	 */
	private void scanEntity(BeanDefinitionRegistry registry, Set<String> entityPackages) {
		if (entityPackages.size() > 0) {
			if (registry.containsBeanDefinition(BEAN)) {
				BeanDefinition beanDefinition = registry.getBeanDefinition(BEAN);
				ConstructorArgumentValues constructorArguments = beanDefinition.getConstructorArgumentValues();
				constructorArguments.addIndexedArgumentValue(0, addPackageNames(constructorArguments, entityPackages));
			} else {
				GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
				beanDefinition.setBeanClass(EntityScanPackages.class);
				beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,
						entityPackages.toArray(new String[entityPackages.size()]));
				beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				registry.registerBeanDefinition(BEAN, beanDefinition);
			}
		}
	}

	private static String[] addPackageNames(ConstructorArgumentValues constructorArguments,
			Collection<String> packageNames) {
		String[] existing = (String[]) constructorArguments.getIndexedArgumentValue(0, String[].class).getValue();
		Set<String> merged = new LinkedHashSet<String>();
		merged.addAll(Arrays.asList(existing));
		merged.addAll(packageNames);
		return merged.toArray(new String[merged.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
