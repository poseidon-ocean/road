# Spring boot Web

## 静态Web内容
* 基本解释
	* HTTP请求内容由Web服务器文件系统提供，常见静态Web内容：HTML、CSS、JS、JPEG、Flash等
* 特点：
	* 计算类型：I/O类型；
	* 交互方式：单一；
	* 资源内容：相同；
	* 资源路径：物理路径(文件、目录)；
	* 请求方法：GET(主体)
*　文件目录就是静态资源：http://docs.spring.io/spring-boot/docs/2.0.0.M3/reference/
* 浏览器是否解析由Content-Type决定
* 常见Web服务器：Apache HTTP Server、Nginx、IIS、GWS
* 对比服务器或语言：各有千秋，需要量化，指标
* 为什么Java Web Server不是常用Web Server
	* 内存占用：类型、分配
	* 垃圾回收：被动回收、停顿
	* 并发处理：线程池、线程开销
* 标准优化技术：主流浏览器都实现了
	* 资源变化：
		* 响应头：last-modified:Mon, 14 Mar 2016 18:08:11 GMT
		* 请求头：if-modified-since:Mon, 14 Mar 2016 18:08:11 GMT
	* 资源缓存：
		* 响应头：etag:"97a-52e0629e8cf09"
		* 请求头：if-none-match:"97a-52e0629e8cf09"
	
## 动态Web内容
* 基本解释：请求内容通过服务器计算而来
* 特点
	* 计算类型：混合类型(I/O、CPU、内存等)
	* 交互方式：丰富(用户输入、客户端特征等)
	* 资源内容：多样性，不同用户可能计算返回的内容不一样
	* 资源路径：逻辑路径(虚拟)
	* 请求方式：GET、HEAD、PUT、POST等
* 使用场景
	* 页面渲染、表单交换、AJAX、JSON/JSONP
	* Web Services(SOAP、WSDL)，太过笨重、WebSocket
* Java Web服务器：Servlet容器(Tomcat、Jetty)、非Servlet容器(Undertow)
* 请求
	* 资源定位URI
	* 请求协议Protocol
	* 请求方法Method
	* 请求参数Parameter
	* 请求主体Body
	* 请求头Header
	* Cookie
* 响应
	* 响应头Header
	* 响应主体Body
* Model2 架构 VS  MVC
	* model数据和处理
	* Model2为面向Web服务的架构，MVC则是面向所有应用场景
* 页面渲染：模版语言和JS的区别？
	* 模板语言是服务端语言，需要服务端计算
	* JS在客户端(浏览器)上执行
	* 服务端处理数据，前端处理动态效果
* 模板引擎
	*　JSP是翻译式语言 JSP-.java-.class
	*　JSP扩展的是标签
	* ThymeLeaf扩展的是标签和属性

## REST理论基础
* 基本概念
	REST = RESTful = Representational State Transfer，is one way of providing interoperability between computer systems on the Internet.
* 架构属性
	* 性能Performance：重>维护成本高，难以理解 轻>rest数据轻
	* 可伸缩性Scalability
	* 统一接口简化性：REST和WebService都提供统一的URI
	* 组件可修改性
	* 组件通讯可见性：组件和组件之间是透明的
	* 组件可移植性：
	* 可靠性：粗泛
* 架构约束
	* C/S架构(Client-Server)
	* 无状态Stateless：服务端和客户端不是一直保存连接的，用户信息或缓存是有状态的
	* 可缓存：服务端缓存
	* 分层系统Layered System
	* 按需代码Code on demand
	* 统一接口Uniform interface
* 统一接口
	* 资源识别
	* 资源操作
		* HTTP verbs:GRT、PUT、POST、DELETE
		* POST是幂等的，其它的是非幂等
	* 自描述信息
		* Content-Type
		* MIME-Type
		* Media Type:application/javascript、text/html
	* 超媒体(HATEOAS):Hypermedia As The Engine Of Application State
* Spring Boot REST
	* 核心接口
		* 定义相关：@Controller、@RestController
		* 映射相关：@RequestMapping、@PathVariable
		* 请求相关：@RequestParam、@RequestHeader、@CookieValue、ReuqestEntity
		* 响应相关：@ResponseBody ReqponseEntity
	* Rest的响应：XML、HTML、JSON		

## 传统Servlet
* 什么是Servlet
	* 是一种基于Java技术的Web组件，用于生成动态内容，由容器管理
* 什么是Servlet容器
	* Servlet引擎，作为WEB服务器或应用服务器的一部分，通过请求和响应对话，提供web客户端与servlet交互的能力
* Servlet生命周期
	* init(), service(), destroy()
	* init(),  destroy():lifecycle of a servlet 只被调用一次
	* init() 当服务器启动或第一次访问的时候被调用
* 九大隐式对象
	* request对象，客户端的请求信息：头信息、系统信息、请求方式、请求参数
	* response对象，客户端的响应
	* session对象，服务器自动创建的与用户请求相关的对象
	* application对象，整个应用级别的信息
	* out对象，输出信息
	* pageContext对象，可以获取任何范围的对象
	* config对象，获取服务器的配置信息
	* page对象，当前jsp页面有效
	* exception对象，异常信息
	
## 核心接口
* Servlet3.0后时代
	* 组件声明注解
		* @javax.servlet.annotation.WebServlet
		* @javax.servlet.annotation.WebFilter
		* @javax.servlet.annotation.WebListener
		* @javax.servlet.annotation.ServletSecurity
		* @javax.servlet.annotation.HttpMethodConstraint
		* @javax.servlet.annotation.HttpConstraint
	* 配置声明
		* @javax.servlet.annotation.WebInitParam
	* 上下文
		* javax.servlet.AsyncContext
	* 事件
		* javax.servlet.AsyncEvent
	* 监听器
		* javax.servlet.AsyncListener
	* Servlet组件注册
		* javax.servlet.ServletContext#addServlet()
		* javax.servlet.ServletRegistration
	* Filter组件注册
		* javax.servlet.FilterContext#addFilter()
		* javax.servlet.FilterRegistration
	* Listener组件注册
		* javax.servlet.ListenerContext#addListener()
	* 自动装配
		* 初始器 javax.servlet.ServletContainerInitializer
		* 类型过滤 @javax.servlet.annotation.HandlesTypes
## Servlet on Spring Boot	


引：https://segmentfault.com/l/1500000009659111?c=b767dc30116d45fb916ed48e6ae27d84
https://segmentfault.com/l/1500000009767025
https://segmentfault.com/l/1500000009830944/play

> 高纬度，哲学的思维
带着问题去研究源码，不要为了学习而去学习
想要了解透彻，就需要知道它的全貌
大胆的猜测，谨慎的验证
猜的越多，判断越来越准
悟性达到一定的高度就OK了
技术也是一脉相承发展过来的，不是突然转变的








