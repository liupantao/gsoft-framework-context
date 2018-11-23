package com.gsoft.framework.context.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.gsoft.framework.context.CompanyLogo;

/**
 * appconf配置文件加载
 * 
 * @author LiuPeng
 * 
 */
public class AppConfigFileListener implements EnvironmentPostProcessor, Ordered, ApplicationListener<ApplicationEvent> {

	private static final DeferredLog logger = new DeferredLog();

	private final static String LOCATION = "file:appconf/config-*.properties,classpath:/appconf/config-*.properties";

	PropertySourcesLoader propertiesLoader = new PropertySourcesLoader();

	private ConfigurableEnvironment environment;

	private ResourceLoader resourceLoader;

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

		logger.info(CompanyLogo.companyString);

		// 此处可以http方式 到配置服务器拉取一堆公共配置+本项目个性配置的json串,拼到Properties里
		this.environment = environment;
		try {
			Set<String> set = new HashSet<>();
			MutablePropertySources propertySources = this.environment.getPropertySources();
			ResourceLoader resourceLoader = application.getResourceLoader();
			resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
			this.resourceLoader = resourceLoader;
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(
					this.resourceLoader);
			String[] locations = StringUtils.commaDelimitedListToStringArray(LOCATION);
			for (String location : locations) {
				Resource[] resources = resourcePatternResolver.getResources(location);
				for (Resource resource : resources) {
					if (set.contains(resource.getFilename())) {
						continue;
					}
					PropertySource<?> propertySource = this.propertiesLoader.load(resource);
					logger.info("加载平台配置文件 - [" + propertySource.getName() + "]");
					propertySources.addLast(propertySource);
					set.add(resource.getFilename());
				}
			}
		} catch (IOException e) {
			logger.error("加载平台配置文件失败", e);
		}
	}

	@Override
	public int getOrder() {
		// +1 保证application.propertie里的内容能覆盖掉本配置文件中默认的
		// 如果不想被覆盖 可以 -1
		return ConfigFileApplicationListener.DEFAULT_ORDER + 1;
	}

	/*
	 * @param event
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			ApplicationEnvironmentPreparedEvent e = (ApplicationEnvironmentPreparedEvent) event;
			postProcessEnvironment(e.getEnvironment(), e.getSpringApplication());
		}
		if (event instanceof ApplicationPreparedEvent) {
			logger.replayTo(AppConfigFileListener.class);
		}
	}
}