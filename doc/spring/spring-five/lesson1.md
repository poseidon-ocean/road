# 《Spring 5 官方文档》 Spring入门指南
1.开始，创建项目  http://start.spring.io/
2.Spring框架介绍
	* 一个Java平台，为开发Java应用提供全面的基础架构支持
	* 益处：
		写一个Java方法执行数据库事务，而无需处理具体事务的APIs。
		写一个本地Java方法去远程调用，而不必处理远程调用的APIs。
		写一个本地Java方法实现管理操作，而不必处理JMX APIs。
		写一个本地Java方法实现消息处理，而不必处理JMS APIs。
	* 依赖注入和控制反转
		http://jinnianshilongnian.iteye.com/blog/1413846
		Dependency Injection、Inversion of Control
		一种设计思想：由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找
3.模块
	Spring框架的功能被有组织的分散到约20个模块中，这些模块分布在核心容器，数据访问/集成，
	Web，AOP(面向切面的编程)，植入(Instrumentation)，消息传输和测试
	* 核心容器
		spring-core和spring-beans模块提供了框架的基础功能，包括IOC和依赖注入。BeanFactory是一个成熟的工厂模式实现，是单例模式
		spring-context（上下文）模块建立在core和beans的基础上：提供了一个框架式的数据访问方式。国际化、事件传播、资源负载、Servlet容器。
			ApplicationContext接口是Context模块的焦点。Spring-context-support支持整合普通第三方库到Spring应用程序上下文，
			特别是用于告诉缓存(ehcache,JCache)和调度(CommonJ,Quartz)的支持
		spring-expression模块提供了强大的表达式语言去支持查询和操作运行时对象图。支持设置和获取属性值，属性分配，方法调用，访问数组，
			集合和索引器的内容，逻辑和算术运算，变量命名以及从Spring的IOC容器中以名称检索对象，还支持列表投影和选择常见的列表聚合
	* AOP和Instrumentation	
		spring-aop模块提供了一个符合AOP联盟（要求）的面向切面的编程实现，例如，允许您定义方法拦截器和切入点(pointcuts),以便于干净地解耦
		单独的spring-aspects模块，提供了与AspectJ的集成
		spring-instrument模块提供了类植入(instrumentation)支持和类加载器的实现，spring-instrument-tomcat模块包含支持Tomcat的植入代理
	* 消息
		spring-messaging(消息传递模块)
	* 数据访问/集成
		由JDBC,ORM,OXM,JMS和事务模块组成
		spring-jdbc模块提供了一个JDBC抽象层，消除了需要的繁琐的JDBC编码和数据库厂商特有的错误代码
		spring-tx模块支持用于实现特殊接口和所有POJO（普通java对象）的类的编程和声明式事务管理
		spring-orm模块为流行的对象关系映射(object-relational mapping)API提供集成层，包括JPA和Hibernate
			可以将O/R映射框架和Spring提供的所有功能结合使用，如简单声明式事务管理
		spring-oxm模块提供了一个支持对象/XML映射实现的抽象层，如JAXB\Castor\JiBX和XStream
		spring-jms(Java Messaging Service)模块包含用于生产和消费消息的功能
	* Web
		由spring-web、spring-webmvc、spring-websocket模块组成
		spring-web模块提供基本的面向Web的集成功能，例如多文件上传功能，以及初始化一个使用了Sevlet侦听器和面向Web的应用程序上下文的IOC容器
			还包含一个HTTP客户端和Spring的远程支持的Web相关部分
		spring-webmvc模块，也称Web-servlet模块，包含用于WEB应用程序的Spring的模型-视图-控制器(MVC)和REST Web Services实现
	* 测试
		spring-test模块支持使用JUnit或TestNG对Spring组件进行单元测试和集成测试。提供了Spring ApplicationContexts的一致加载和这些上下文
		的缓存，提供可用于独立测试代码的模仿(mock)对象
	* 使用场景
		Spring的声明式事务管理功能使Web应用程序完全事务性
			Spring的ORM支持与JPA和Hibernate集成
			使用第三方web框架的Spring中间层，Struts，Tapestry，JSF或其他UI框架构
			远程使用场景
		依赖管理和命名约定
			发布版本：http://repo.spring.io/release/org/springframework/spring/
			里程碑：http://repo.spring.io/milestone/org/springframework/spring
			快照：http://repo.spring.io/snapshot/org/springframework/spring
	* 日志
		日志是Spring非常重要的依赖，是唯一的强制性外部依赖关系，开发者都喜欢看到日志的输出，Spring集成的许多工具都具有日志依赖
		不使用Commons Logging，存在很多问题
		使用SLF4J,两个实现：Log4j2和logback





引：
http://ifeve.com/overview-getting-started-with-spring/
