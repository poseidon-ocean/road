# 高级装配
* Spring profile
* 条件化的bean
* 自动装配与歧义性
* bean的作用域
* Spring表达式语言

## 环境与profile
* profile可以为不同的环境(dev、prod)提供不同的数据库配置、加密算法等
* @Profile注解可以在类级别和方法级别，没有指定profile的bean始终都会被创建
* XML的方式配置 datasource.xml
```
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * 不同环境所需要的数据库配置
 */
public class DataSourceDemo {

	/**
	 * EmbeddedDatabaseBuilder会搭建一个嵌入式的Hypersonic数据库
	 * 模式(schema)定义在schema.sql
	 * 测试数据通过test-data.sql加载
	 */
	@Bean(destroyMethod="shutdown")
	public DataSource dataSource_1(){
		return new EmbeddedDatabaseBuilder()
				.addScript("classpath:schema.sql")
				.addScript("classpath:test-data.sql")
				.build();
	}
	
	/**
	 * 使用JNDI从容器中获取一个DataSource
	 */
	@Bean
	public DataSource dataSource_2(){
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jdbc/myDs");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
		return (DataSource) jndiObjectFactoryBean.getObject();
	}
	
	/**
	 * 配置DBCP的连接池
	 */
	@Bean(destroyMethod="close")
	public DataSource dataSource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:h2:tcp://dbserver/~/test");
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setInitialSize(20);
		dataSource.setMaxActive(30);
		return dataSource;
	}
	
}
```

## 激活profile
* 依赖属性：spring.profiles.active和spring.profiles.default
	* 作为DispatcherServlet的初始化参数
	* 作为Web应用的上下文参数
	* 作为JNDI条目
	* 作为环境变量
	* 作为JVM的系统属性
	* 在集成测试类上，使用@ActiveProfile注解设置
* 在web.xml中配置 见web.xml03
```
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * 不同环境数据源部署配置类
 *
 */
@Configuration
public class DataSourceConfig {

	/**
	 * 为dev profile装配的bean
	 * @return
	 */
	@Bean(destroyMethod="shutdown")
	@Profile("dev")
	public DataSource embeddedDataSource(){
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:schema.sql")
				.addScript("classpath:test-data.sql")
				.build();
	}
	
	/**
	 * 为prod profile装配的bean
	 * @return
	 */
	@Bean
	@Profile("prod")
	public DataSource jndiDataSource(){
		JndiObjectFactoryBean jndiObjectFactoryBean = 
				new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jdbc/myDS");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
		
		return (DataSource) jndiObjectFactoryBean.getObject();
	}
}
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * 开发环境部署配置类
 *
 */
@Configuration
@Profile("dev")
public class DevelopmentProfileConfig {

	@Bean(destroyMethod="shutdown")
	public DataSource dataSource(){
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:schema.sql")
				.addScript("classpath:test-data.sql")
				.build();
	}
}
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用profile进行测试
 * 当运行集成测试时，Spring提供了@ActiveProfiles注解，使用它来指定运行测试时要激活哪个profile
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={PersistenceConfig.class})
@ActiveProfiles("dev")
public class PersistenceTest {

}
```

## 条件化的bean
* 假设你希望一个或多个bean只有在应用的类路径下包含特定库时才创建
* 或者希望某个bean只有当另外某个特定的bean也声明了之后才会创建
* 还可能要求只有某个特定的环境变量设置之后，才会创建某个bean
* Spring4引入一个新的@Conditional注解，它可以用在带有@Bean注解的方法上
* 例如，假设有个名为MagicBean的类，我们希望只有设置了magic环境属性的时候，Spring才会实例化这个类
* 通过ConditionContext，可以：
	* 借助getRegistry()返回的BeanDefinitionRegistry检查bean定义
	* 借助getBeanFactory()返回的ConfigurableListableBeanFactory检查bean是否存在，甚至检查bean的属性
	* 借助getEnvironment()返回的Environment检查环境变量是否存在以及它的值是什么
	* 读取并探查getResourceLoader()返回的ResourceLoader所加载的资源
	* 借助getClassLoader()返回的ClassLoader加载并检查类是否存在
* @Profile这个注解本身也使用了@Conditional注解，并且引用ProfileCondition作为条件
	* ProfileCondition会检查value属性，该属性包含了bean的profile名称。检查profile是否激活
```
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MagicExistsConditional implements Condition {

	/**
	 * 方法简单功能强大
	 */
	@Override
	public boolean matches(ConditionContext ctxt, AnnotatedTypeMetadata metadata) {
		Environment env = ctxt.getEnvironment();
		//检查magic属性
		return env.containsProperty("magic");
	}

}
```	

## 处理自动装配的歧义
* 仅有一个bean匹配所需的结果时，自动装配才是有效的，如果不仅有一个bean能够匹配结果的话，这种歧义性会阻碍Spring自动装配属性、构造器参数或方法参数
* 标示首选(primary)的bean
	* @Component注解上加@Primary注解
	* 在Java配置显示声明 中@Bean注解上加@Primary注解
	* XML配置bean primary="true"
* 限定自动装配的bean
	* 不止一个限定符时，歧义性问题依然存在
	* 限定bean：在注解@Autowired上加@Qualifier("beanName")
	* 限定符和注入的bean是耦合的，类名称的任意改动都会导致限定失败
*　创建自定义的限定符
	* 在@Component注解上加@Qualifier("selfDefinedName")注解
	* 可以在@Autowired上加@Qualifier("selfDefinedName")
	* 也可以在Java配置显示定义bean时，在@Bean注解上加@Qualifier("selfDefinedName")
* 使用自定义的限定符注解
	* 如果有多个相同的限定符时，又会导致歧义性
	* 在加一个@Qualifier注解：Java不允许在同一条目上出现相同的多个注解
		(JDK8支持，注解本身在定义时带有@Repeatable注解就可以，不过Spring的@Qualifier注解上没有)
	* 定义不同的注解，在注解上加@Qualifier，例如在bean上加@Cold和@Creamy(自定义注解)

## bean的作用域
* 默认情况下，Spring应用上下文所有bean都是作为以单例(singleton)的形式创建的
* Spring定义了多种作用域
	* 单例(Singleton)：在整个应用中，只创建bean的一个实例
	* 原型(Prototype)：每次注入或者通过Spring应用上下文获取的时候，都会创建一个新的bean实例
	* 会话(Session)：在Web应用中，为每个会话创建一个bean实例
	* 请求(Request)：在Web应用中，每个请求创建一个bean实例
* 使用会话和请求作用域
	* 购物车bean，会话作用域最合适，因为它与给定的用户关联性最大 例如：ShoppingCart	
```
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * value=WebApplicationContext.SCOPE_SESSION值是session，每个会话会创建一个ShoppingCart
 * proxyMode=ScopedProxyMode.INTERFACES创建类的代理，确保当前购物车就是当前会话所对应的那一个，而不是其他用户
 * 
 * XML的配置
 * <bean id="cart" class="com.leaf.u_spring.chapter03.ShoppingCart" scope="session">
 * 		<aop:scoped-proxy />
 * </bean>
 * 
 * 使用的是CGLib生成代理类
 */
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,
		proxyMode=ScopedProxyMode.INTERFACES)
public class ShoppingCart {

	
}
```

## 运行时值注入
* Spring提供两种运行的值注入
	* 属性占位符(Property placeholder)
	* Spring表达式语言(SpEL)
* 深入学习Spring的Environment
	* String getProperty(String key)	获取属性值
	* String getProperty(String key, String defaultValue)	获取属性值，不存在时返回给定默认值
	* <T> T getProperty(String key, Class<T> targetType);	获取指定类型的属性值
	* <T> T getProperty(String key, Class<T> targetType, T defaultValue); 获取指定类型的属性值，不存在时返回默认值
	* boolean containsProperty(String key); 检查指定属性值是否存在
	* String[] getActiveProfiles(); 返回激活profile名称数组
	* String[] getDefaultProfiles(); 返回默认profile名称数组
	* boolean acceptsProfiles(String... profiles); 如果environment支持给定profile的话，就返回true
* 解析属性占位符
	* Spring一直支持将属性定义到外部的属性文件中，并使用占位符值将其插入到Spring bean中
	* 在Spring装配中，占位符的形式为使用"${...}"包装的属性名称
	* 在JavaConfig使用 @Value
	* 在XML中 <context:property-placeholder location="classpath:/conf/*.properties" />
* 使用Spring表达式语言进行装配
	* SpEl拥有特性：
		* 使用bean的ID来引用bean
		* 调用方法和访问对象的属性
		* 对值进行算术、关系和逻辑运算
		* 正则表达式匹配
		* c集合操作
		* Spring Security支持使用SpEL表达式定义安全限制规则
		* 在Thymeleaf模板视图中使用SpEL表达式引用模型数据
	* SpEL表达式要放到 "#{...}"之中：
		* #{1} 		- 1
		* #{T(System).currentTimeMillis()}	-当前毫秒数
		* #{sgtPeppers.artist}  		- ID为sgtPeppers的bean的artist属性
		* #{systemProperties['disc.title']}		-通过systemProperties对象引用系统属性
	* 表示字面值
		* 浮点数：#{3.14159}	科学计数法：#{9.87E4}		字符串：#{'hello'}	boolean类型：#{false}
	* 引入bean、属性和方法
		* #{sgtPeppers} -bean		#{sgtPeppers.artist} -属性
		* #{sgtPeppers.selectArtist()} -方法		
		* #{sgtPeppers.selectArtist()？.toUpperCase()} -?.判断非空情况调用toUpperCase方法
	* 在表达式中使用类型
		* 如果要在SpEL中访问类作用域的方法和常量的话，要依赖T()这个关键的运算符。
		* 表达Java的Math类	T(java.lang.Math)
		* 把PI属性装配待bean属性	T(java.lang.Math).PI
		* 调用T()运算符所得到的静态方法	T(java.lang.Math).random()
	* SpEL运算符
		* 算术运算、比较运算、逻辑运算、条件运算、正则表达式、三元运算符
		* #{2 * T(java.lang.Math).PI * circle.radius}		-计算圆周长
		* #{T(java.lang.Math).PI * circle.radius ^ 2}		-计算圆面积
		* #{disc.title + 'by' + disc.artist}				-String类型的连接操作
		* #{counter.total == 100}或#{counter.total eq 100} -比较运算
		* #{scoreboard.score > 1000 ? 'Winner!' : 'Losser'}	-三元运算
		* #{disc.title ?: 'Rattle and Hum'}				-检查null，使用默认值代替null
		* #{admin.email matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com'}		-匹配有效邮箱
		* #{jukebox.songs[4].title}	-计算ID为jukebox的bean的songs集合中第五个元素的title属性
		* #{jukebox.songs[T(java.lang.Math).random() * jukebox.songs.size].title}		-获取随机歌曲的title
		* #{jukebox.songs.?[artist eq 'Aerosmith']}		-用来对集合过滤查询
		* ‘.^[]’ 和 ‘.$[]’分别用来在集合中查询第一个匹配项和最后一个匹配项
		* 投影运算符 (.![])，会从集合的每个成员中选择特定的属性放到另外一个集合中

> 引用：《Spring In Action 4》第3章

