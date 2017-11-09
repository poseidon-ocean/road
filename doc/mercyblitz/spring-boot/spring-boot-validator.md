# Spring Boot 验证

## Apache commons-validator
* 功能特性
	* 可配置的校验引擎
	* 可重用的原生校验手段
* 第三方依赖
	* commons-beanutils:1.9.2
	* commons-digester:1.8.1
	* commons-logging:1.2
	* commons-collections:3.2.2
* 设计模式
	* 单例模式GoF23
	* 校验器模式

## Spring Validator
* 实现模式
	* 实现org.springframework.validation.Validator接口
	* 实现supports方法判断是否需要支持校验
		* 当方法返回false时，视作不予校验
	* 实现validate方法，判断target参数是否校验合法
		* 当校验非法时，通过Errors对象返回
	* 实现MessageCodesResolver或重用框架实现，完成错误信息编码化
* 辅助
	* 使用工具类ValidationUtil，辅助通用校验逻辑

## Bean Validation 1.0 (JSR 303)
* 常用注解
	* @Valid
	* @NotNull
	* @Null
	* @Size
	* @Min
	* @Max
* 自定义校验


* 做技术架构需要有深度和广度，广度是需要知道有什么，深度是知道怎么做
* 不注重业界的发展的话，广度不足，造成重复造轮子
