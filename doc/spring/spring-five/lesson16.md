# ORM和数据访问
1.介绍一下Spring中的ORM

	Spring框架在实现资源管理、数据访问对象(DAO)层，和事务策略等方面，支持对Java持久化API(JPA)
	以及原生Hibernate的集成。以Hibernate举例来说，Spring有非常赞的IoC功能,可以解决许多典型的
	Hibernate配置和集成问题。
	
	* 易于测试：Spring IoC的模式使得开发者可以轻易的替换Hibernate的SessionFactory实例，JDBC
	的dataSource实例，事务管理，以及映射对象（如果有必要）的配置和实现。利于对每个模块独立测试。
	* 泛化数据访问异常。Spring可以将ORM工具的异常封装起来，将所有异常（可以是受检测异常）封装成运行时
	的DataAccessException体系。
	
	* 通用的资源管理。Spring的应用上下文可以通过处理配置源的位置来灵活配置Hibernate的SessionFactory
	实例，JPA的EntityManagerFactory实例，JDBC的DataSource实例以及其它类似的资源。
	
	* 集成事务管理：开发者可以通过@Transactional注解或者在XML配置文件中显示配置事务AOP Advise拦截，
	将ORM代码封装在声明式的AOP方法拦截器中。事务的语义和异常处理（回滚等）都可以根据开发者自己的需求来定制。
	
2.集成ORM的注意事项

	Spring对ORM集成的主要目的是使应用层次化，可以任意选择数据访问和事务管理技术，并且为应用对象提供松耦合
	结构。不再将业务逻辑依赖于数据访问或事务策略上，不再使用基于硬编码的资源查找，不再使用难以替代的单例，
	不再自定义服务的注册。为应用提供一个简单和一致的方法来装载对象，保证他们的重用并尽可能不依赖容器。所有单独
	的数据访问功能都可以自己使用，也可以很好地与Spring的ApplicationContext集成，提供基于XML的配置和不需要
	Spring感知的普通JavaBean实例。在典型的Spring应用程序中，许多重要的对象都是JavaBean：数据访问模版，
	数据访问对象，事务管理器，使用数据访问对象和事务管理器的业务服务，Web视图解析器，使用业务服务的Web控制器。
	
	资源和事务管理，
	Spring为资源的配置提供了简单易用的解决方案，在JDBC上使用模版技术，在ORM上使用AOP拦截
	技术。
	JdbcTemplate类通过Spring事务管理器挂接到Spring事务支持，并支持JTA和JDBC事务。Spring通过Hibernate，
	JPA事务管理器和JTA的支持来提供Hibernate和JPA这类ORM技术支持。
	
	异常转义，开发者可以既基于简单的持久化技术的API和注解来实现DAO，同时还受益于Spring管理的事务，依赖注入
	和透明异常转换（如果需要）到Spring的自定义异常层次结构。
	
3. 声明式事务

	transactional 美[træn'zækʃənəl]
	
	
引：
	http://ifeve.com/spring-16-orm/