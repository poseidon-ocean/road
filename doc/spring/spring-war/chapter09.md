# 保护Web应用

## Spring Security简介
* Spring Security是为基于Spring的应用程序提供声明式安全包含的安全性框架
* 提供了完整的安全性解决方案，能够在Web请求级别和方法调用级别处理身份认证和授权
* 基于Spring，充分利用了依赖注入和面向切面的技术

## 理解Spring Security的模块
* 3.2版本提供了11个模块
* ACL，支持通过访问控制列表(access control list,ACL)为域对象提供安全性
* 切面(Aspects)，一个很小的模块，当使用Spring Security注解时，会使用基于AspectJ的切面，而不是使用标准的Spring AOP
* CAS客户端(CAS Client)，提供与Jasing的中心认证服务(Central Authentication Service,CAS)进行集成的功能
* 配置(Configuration)，包含通过XML和Java配置Spring Security的功能支持
* 核心(Core)，提供Spring Security基本库
* 加密(Cryptography)，提供了加密和密码编码的功能
* LDAP，支持基于LDAP进行认证
* OpenID，支持使用OpenID进行集中式认证
* Remoting，提供了对Spring Remoting的支持
* 标签库(Tag Library)，Spring Security的JSP标签库
* Web，提供了Spring Security基于Filter的Web安全性支持

## 过滤Web请求
* DelegatingFilterProxy一个特殊的Servlet Filter，将工作委托给一个javax.servlet.Filter的实现类，
	这个实现类作为<bean>注册在Spring应用上下文中
* 可以在web.xml中配置，<filter>  详见web.xml-bak
* 借助WebApplicationInitializer以Java的方式来配置DelegatingFilterProxy 见SecurityWebInitializer.java

## 编写简单的安全性配置
* 在Spring应用上下文中，任何实现了WebSecurityConfigurer的bean都可以用来配置Spring Security
* 简单的方式如SecurityConfig类扩展WebSecurityConfigurerAdapter类
* @EnableWebSecurity可以启用任意Web应用的安全性功能
* 可以通过重载WebSecurityConfigurerAdapter的三个configure()方法来配置Web安全性，这个过程中会使用传递进来的参数设置行为
	* configure(HttpSecurity http)，通过重载，配置Spring Security的Filter链，默认配置
	* configure(WebSecurity web)，通过重载，配置如何通过拦截器保护请求
	* configure(AuthenticationManagerBuilder auth)，通过重载，配置user-detail服务，用户存储使用
* 为满足应用，需要添加一点配置
	* 配置用户存储
	* 指定哪些请求需要认证，哪些请求不需要认证，以及所需要的权限
	* 提供一个自定义的登录页面，替代原来简单的默认登录页

## 选择查询用户详细信息的服务
* 需要用户存储，就是用户名、密码以及其他信息存储的地方，在进行认证决策的时候，会对其进行检索
* Spring Security非常灵活，能够基于各种数据存储来认证用户，内置多种常见用户存储场景：内存、关系型数据库以及LDAP

//TODO 使用的是shiro，这一块先放下

> 所有美好的东西都不宜过度发展，都该保留在萌芽状态，将发未发，因为那是一切可能性的源头。可是事情只要一启动，就不只可能，而且必将走向衰落与凋零。




