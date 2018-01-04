# 渲染Web视图

## 理解视图解析
* 将控制器中请求处理的逻辑和视图中的渲染实现解耦是Spring MVC的一个重要特性
* Spring自带的视图解析器
	* InternalResourceViewResolver 将视图解析为Web应用的内部资源(一般为JSP)
	* FreeMarkerViewResolver 将视图解析为FreeMarker模板
	* VelocityViewResolver 将视图解析为Velocity模板
	* BeanNameViewResolver 将视图解析为Spring应用上下文中的bean，其中bean的ID与视图的名字相同
	* BeetlSpringViewResolver	将视图解析为beetl
	* TilesViewResolver		将视图解析为Apache Tile定义，其中Tile ID与视图名称相同
	* ...
* 校验注解
	* 在POJO类上加注解,如Spittler
	* 在根路径下定义属性文件 ValidationMessages.properties
	* 页面展现 <sf:errors path="userName">
* Spring 通用标签库 <%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
* 国际化信息展示 <s:message code="user.name" />

## 使用Apache Tiles视图定义布局
* 页面定义通用头部和底部，原始方式给每个JSP页面添加头部和底部HTML，扩展性不好
* 使用布局引擎，如Apache Tiles，定义适用与所有页面的通用页面布局
* 配置Tiles视图解析器,如 com.leaf.u_spring.chapter06.ViewConfig
* 定义Tiles /WEB-INF/layout/tiles.xml

## 使用Thymeleaf
* 配置Thymeleaf视图解析器
	* ThymeleafViewResolver：将逻辑视图名称解析为Thymeleaf模板视图
	* SpringTemplateEngine：处理模板并渲染结果
	* TemplateResolver：加载Thymeleaf模板

## 使用beetl
* http://ibeetl.com/
	
	














