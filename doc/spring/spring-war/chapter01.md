# Spring之旅

## 简化Java开发
* Spring的使命：简化Java开发
* 为了降低Java开发的复杂性，采取如下关键策略：
	基于POJO的轻量级和最小侵入性编程
	通过依赖注入和面向接口实现松耦合
	基于切面和惯例进行声明式编程
	通过切面和模版减少样式代码

## 依赖注入
*　耦合性具有两面性(two-headed beast).
	一方面，紧密耦合的代码难以测试、难以复用、难以理解，并且典型地表现“打地鼠”式的bug特性（修复一个bug，将会出现一个或者更多新的bug）
	另一方面，一定程度的耦合又是必须的--完全没有耦合的代码什么也做不了
*　通过DI，对象的依赖关系将由系统中负责协调各对象的第三方租件在创建对象的时候进行设定。对象无需自行创建或管理它们依赖关系，依赖关系将被自动
注入到需要它们的对象当中去
*　依赖注入会将所依赖的关系自动交给目标对象，而不是让对象自己去获取依赖
	构造器注入(constructor injection)
* 创建应用组件之间协作的行为通常称为装配(wiring)
	SlayDragonQuest\BraveKnight\PrintStream如何装配起来？
	XML(knights.xml)和Java描述配置(KnightConfig)
```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于Java的配置
 * 
 * @Configuration注解表明这个类是一个配置类，该类应该包含在Spring应用上下文中如何创建bean的细节
 */
@Configuration
public class KnightConfig {

	@Bean
	public Knight knight(){
		return new BraveKnight(quest());
	}
	
	@Bean
	public Quest quest(){
		return new SlayDragonQuest(System.out);
	}
}

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 应用上下文容器的demo
 * 
 */
public class ApplicationContextDemo {

	//在指定的文件系统中查找knight.xml文件
	ApplicationContext context_1 = new FileSystemXmlApplicationContext("c:/knight.xml");
	//从所有的类路径下查找knight.xml文件
	ApplicationContext context_2 = new ClassPathXmlApplicationContext("knight.xml");
	//从Java配置中加载应用上下文
	ApplicationContext context_3 = new AnnotationConfigApplicationContext(com.leaf.u_spring.chapter01.KnightConfig.class); 
	
	
}
```

## 应用切面
* DI能够让相互协作的软件组织保持松耦合，而面向切面编程(aspect-oriented programming, AOP)允许你把遍布应用各处的功能分离出来形成可重用的组件
* 关注点是横切面，如将安全、事务和日志关注点和核心业务逻辑相分离
	
## 使用模板消除样板式代码
* 样板式代码(boilerplate code)
```
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 样板式代码(boilerplate code)
 * 模拟JDBC操作
 * 
 */
public class BoilerplateCode {
	
	DataSource dataSource = null;
	
	String sql = "select id, firstname, lastname, salary from employee where id=?";

	//改造前
	public Employee getEmployeeById(long id){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			//查找员工
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			Employee employee = null;
			if(rs.next()){
				//根据数据创建对象
				employee = new Employee();
				employee.setId(rs.getLong("id"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setSalary(rs.getBigDecimal("salary"));
			}
			return employee;
		} catch (SQLException e) {
			// 扑捉异常，也做不了太多事
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	private JdbcTemplate jdbcTemplate;
	//改造后
	//模板能够让你的代码关注于自身的职责
	public Employee getEmployeeById_1(long id){
		//SQL查询
		return jdbcTemplate.queryForObject(sql, new RowMapper<Employee>(){
			//将结果匹配为对象
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getLong("id"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setSalary(rs.getBigDecimal("salary"));
				return employee;
			}
			//指定查询参数
		}, id);
	}
}

class Employee {
	
	private Long id;
	private String firstName;
	private String lastName;
	private BigDecimal salary;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
}
```

## Spring 容器
* 在基于Spring的应用中，你的应用对象生存于Spring容器(container)中。
* Spring容器负责创建对象，装配它们，配置它们并管理它们的整个生命周期，从生存到死亡(new到finalize)
* 应用上下文，Spring容器的一种
	* AnnotationConfigApplicationContext:从一个或多个基于Java的配置类中加载Spring应用上下文
	* AnnotationConfigWebApplicationContext:从一个或多个基于Java的配置类中加载Spring Web应用上下文
	* ClassPathXmlApplicationContext:从类路径下的一个或多个XML配置文件中加载上下文定义，把应用上下文的定义文件作为类资源
	* FileSystemXmlApplicationContext:从文件系统下的一个或多个XML配置文件中加载上下文定义
	* XmlWebApplicationContext:从Web应用下的一个或多个XML配置文件中加载上下文定义
* bean的生命周期
	* Spring对bean进行实例化
	* Spring将值和bean的引用注入到bean对应的属性中
	* 如果bean实现了BeanNameAware接口，Spring将bean的ID传递给setBeanName()方法
	* 如果bean实现了BeanFactoryAware接口，Spring将调用setBeanFactory()方法，将BeanFactory容器实例传入
	* 如果bean实现了ApplicationContextAware接口，Spring将调用setApplicationContext()方法，将bean所在的应用上下文的引用传入进来
	* 如果bean实现了BeanPostProcessor接口，Spring将调用它们的postProcessBeforeInitialization()方法
	* 如果bean实现了InitializingBean接口，Spring将调用它们的afterPropertiesSet()方法。
		类似地，如果bean使用了init-method声明了初始化方法，该方法也会被调用
	* 如果bean实现了BeanPostProcessor接口，Spring将调用它们的afterProcessBeforeInitialization()方法
	* 此时，bean已经准备就绪，可以被应用程序使用了，它们将一直驻留在应用上下文中，直到该应用上下文被销毁
	* 如果bean实现了DisposableBean接口，Spring将调用它的destroy()接口方法。
		同样，如果bean使用destory-method声明了销毁方法，该方法也会被调用

## Spring模块
* 在Spring4.0中，Spring框架的发布版包括了20个不同的模块，每个模块会有3个JAR文件(二进制类库、源码的JAR文件以及JavaDoc的JAR文件)
* Spring核心容器，bean工厂、应用上下文等
* Spring的AOP模块，AOP、Aspects
* 数据访问和集成，JDBC、Transaction、ORM、OXM、Messaging、JMS
* WEB与远程调用，WEB、WEB servlet、WEB portlet、WebSocket
* Instrumentation，为Tomcat提供了一个织入代理，能够为Tomcat传递类文件

## Spring Portfolio
* Spring Web Flow ，为基于流程的会话式Web应用提供了支持
* Spring Web Service
* Spring Security，利用Spring AOP，Spring Security为Spring应用提供了声明式的安全机制
* Spring Integration，提供了多种通用应用集成模式的Spring声明式风格实现
* Spring Batch
* Spring Data 支持关系型数据库和非关系型数据库
* Spring Social 社交网络扩展模块
* Spring Mobile移动应用
* Spring for Android
* Spring Boot大量依赖于自动配置技术

## Spring 3.1新特性
* 引入环境profile功能，解决各种环境(开发、测试、生产)选择不通配置的问题
* 添加了多个enable注解，注解启用Spring的特定功能
* 添加了Spring对声明式缓存的支持，能够使用简单的注解声明缓存边界和规则
* 新添加的用于构造器注入的c命名空间
* 开始支持Servlet3.0，包括基于Java的配置中声明Servlet和Filter
* 改善对JPA的支持
* 自动绑定路径变量到模型属性中
* 提供了@RequestMappingproduces和consumes属性，用于匹配请求中的Accept和Content-Type头部信息
* 提供了@RequestPart注解，用于将multipart请求中的某些部分绑定到处理器的方法参数中
* 支持flash属性以及用于在请求间存放flash属性的RedirectAttributes类型

## Spring 3.2新特性
* 可以使用Servlet3.0的异步请求
* 引入了Spring MVC测试框架
* 基于RestTemplate的客户端的测试支持
* @ControllerAdvice注解能够将通用的@ExceptionHandler、@InitBinder和@ModelAttributes方法集中到一个类中，并应用到所有控制器上
* 支持完整的内容协商(full content negotiation)
* @MatrixVariable注解能绑定矩阵变量到方法参数中
* 基础的抽象类AbstractDispatcherServletInitializer能够非常便利地配置DispatcherServlet
* 新增了ResponseEntityExceptionHandler
* RestTemplate和@RequestBody的参数可以支持泛型
* RestTemplate和@RequestMapping可以支持HTTP PATCH方法
* 在拦截器匹配时，支持使用URL模式将其排除在拦截器的处理功能之外
* @Autowired @Value @Bean
* @DateTimeFormat
* 提供了对JCache5.0的支持
* 支持定义全局来解析和渲染日期和时间
* 在集成测试中，能够配置和加载WebApplicationContent
* 在集成测试中，能够针对request和session作用域的bean进行测试

## Spring 4.0 新特性
* 支持WebSocket
* 提供高层次的面向消息的编程模型，基于SockJS
* 支持Java8特性
* 添加了条件化创建bean的功能
* 包含了Spring RestTemplate的一个新的异步实现

> Spring 致力于简化企业级Java开发，促进代码的松散耦合
> 依赖注入和AOP是Spring框架最核心的部分
> 引用：《Spring In Action 4》第1章



























	
	