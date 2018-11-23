/**
 * 
 */
package com.gsoft.framework.context;


/**
 * 模块配置文件
 * @author LiuPeng
 *
 */
public class ModuleConfig {

	/**
	 * @Fields code : 模型编码
	 */
	private String code;
	
	/**
	 * @Fields name : 模型名
	 */
	private String name;
	
	/**
	 * @Fields caption : 模型描述
	 */
	private String caption;
	
	private String basePackage;
	
	/**
	 * @Fields daoPackages : daoPackage
	 */
	private String[] daoPackages;
	
	private String[] entityPackages;
	
	/**
	 * @Fields entityManagerFactory : 模块使用的entityManagerFactory
	 */
	private String entityManagerFactory;
	
	/**
	 * @Fields projectPath : 模块项目路径
	 * 代码生成配置项
	 */
	private String projectPath;
	
	/**
	 * @Fields mainSrcPath : java源文件路径 
	 */
	private String mainSrcPath;
	
	/**
	 * @Fields mainResourcePath : java配置文件路径
	 */
	private String mainResourcePath;
	
	/**
	 * @Fields testSrcPath : java源文件路径
	 */
	private String testSrcPath;
	
	/**
	 * @Fields testResourcePath : java配置文件路径
	 */
	private String testResourcePath;
	
	/**
	 * @Fields webAppPath : web文件路径
	 */
	private String webAppPath;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	

	public String getMainSrcPath() {
		return mainSrcPath;
	}

	public void setMainSrcPath(String mainSrcPath) {
		this.mainSrcPath = mainSrcPath;
	}

	public String getMainResourcePath() {
		return mainResourcePath;
	}

	public void setMainResourcePath(String mainResourcePath) {
		this.mainResourcePath = mainResourcePath;
	}

	public String getWebAppPath() {
		return webAppPath;
	}

	public void setWebAppPath(String webAppPath) {
		this.webAppPath = webAppPath;
	}

	public String getTestSrcPath() {
		return testSrcPath;
	}

	public void setTestSrcPath(String testSrcPath) {
		this.testSrcPath = testSrcPath;
	}

	public String getTestResourcePath() {
		return testResourcePath;
	}

	public void setTestResourcePath(String testResourcePath) {
		this.testResourcePath = testResourcePath;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String[] getDaoPackages() {
		return daoPackages;
	}

	public void setDaoPackages(String[] daoPackages) {
		this.daoPackages = daoPackages;
	}

	public String[] getEntityPackages() {
		return entityPackages;
	}

	public void setEntityPackages(String[] entityPackages) {
		this.entityPackages = entityPackages;
	}

	public String getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(String entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
}
