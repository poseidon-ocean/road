# 构建Spring Web应用程序
* 映射请求到Spring控制器
* 透明地绑定表单参数
* 校验表单提交

## 跟踪Spring MVC的请求
* 发起请求->携带请求URL和表单信息到前端控制器(DispatcherServlet)->查询处理器映射(handler mapping)->
	发送给选中的控制器->将请求连同模型和视图名发送回DispatcherServlet ->使用视图解析器(view resolver)来将逻辑视图名
	匹配为一个特定的视图实现->视图的实现(可能是JSP)，使用模型数据渲染输出返回客户端
* DispatcherServlet的任务是将请求发送给Spring MVC控制器(controller)，是Spring MVC的核心
* 处理器映射会根据请求所携带的URL信息来进行决策
* 控制器处理用户提交的信息，并打包模型数据输出到视图
http://sishuok.com/forum/upload/2012/7/14/529024df9d2b0d1e62d8054a86d866c9__1.JPG
http://sishuok.com/forum/upload/2012/7/14/57ea9e7edeebd5ee2ec0cf27313c5fb6__2.JPG

```
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 配置DispatcherServlet
 */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 根配置定义
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class };
	}

	/**
	 * 指定配置类
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class};
	}


	/**
	 * 将DispatcherServlet映射到 "/"
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{ "/" };
	}

}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration									//开启配置
@EnableWebMvc									//启用Spring MVC
@ComponentScan("com.leaf.u_spring.chapter05")	//扫描的包
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 配置JSP视图解析器
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	/**
	 * 配置静态资源的处理,DispatcherServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	

}

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={"com.leaf.u_spring"},
		excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
public class RootConfig {

}
```

## 搭建Spring MVC
* 配置DispatcherServlet:在web.xml中配置或Java代码配置（SpittrWebAppInitializer）
* Spring MVC允许多种方式将客户端中的数据传送到控制器的处理器方法中：
	* 查询参数(Query Parameter)
	* 表单参数(Form Parameter)
	* 路径参数(Path Parameter)
* Java校验API所提供的校验注解
	* @AssertFalse  所注解的元素必须是Boolean类型，并且值为false
	* @AssertTrue	所注解的元素必须是Boolean类型，并且值为true
	* @DecimalMax 	所注解的元素必须是数字，并且它的值要小于或等于给定的BigDecimalString值
	* @DecimalMin	所注解的元素比谁数字，并且它的值要大于或等于给定BigDecimalString值
	* @Digits		所注解的元素必须是数字，并且它的值必须有指定的位数
	* @Future		所注解的元素的值必须是一个将来的日期
	* @Max			所注解的元素必须是数字，并且它的值要小于或等于给定的值
	* @Min			所注解的元素必须数数字，并且它的值要大于或等于给定的值
	* @NotNull		所注解的元素的值不能为null
	* @Null			所注解的元素的值必须为null
	* @Past			所注解的元素的值必须是一个已经过去的日期
	* @Pattern		所注解的元素的值必须匹配给定的正则表达式
	* @Size			所注解的元素的值必须是String、集合或数组，并且它的长度要符合给定的范围
	



