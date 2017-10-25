# 自定义启动器

## Spring Boot Starter
* Spring Boot启动器，是Spring Boot框架中最核心的组件
* 自动装配模块(Autoconfigure Module)
	* 配置键的定义 @ConfigurationProperties
	* 自定义已初始化组件的回调接口 EmbeddedServletContainerCustomizer
	* 自动装配类型
		* 自动装配Bean(Auto-Configuration Beans)
			* Spring配置(@Configuration)
			* Spring Boot管理上下文配置(@ManagementContextConfiguration)
		* Spring Boot组件
			* FailureAnalysisReporter
			* SpringApplicationRunListener
			* AutoConfigurationImportListener
	* 前缀条件(@Conditional)
		* Bean装配前的前缀条件，基于Spring4 @Conditional,判断当前Bean是否适合或者需要装配
	* Spring Boot派生前缀条件注解
		* 类条件：用于判断指定的Class对象或者其全名称存在与否
			*	存在判断：@ConditionalOnClass
			*	缺失判断：@ConditionalOnMissingBean
		* Bean条件:用于判断指定的Spring Bean是否在指定的Spring应用上下文中存在与否
			*	存在判断：@ConditionalOnBean
			*	缺失判断：@ConditionalOnMissingBean
		* 配置属性条件
		* 资源条件
		* Web应用条件
		* Spring 表达式条件
* Annotation具有等价性、合并性等特点










