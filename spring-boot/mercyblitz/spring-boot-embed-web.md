# 嵌入式Web容器

## 传统Servlet容器
* Apache Tomcat
	* 来源
		* Apache美国陆军武装直升机
		* Tomcat美国海军雄猫战斗机
		* Catalina美国远程轰炸机
	* 下载core包开发使用，下载Source包 查看源码
	* 核心组件(Components)
		* web.xml 标准的配置
		* javax.servlet.http.HttpServletResponse
	* 静态资源处理
		* org.apache.catalina.servlets.DefaultServlet
		* listings设置为true和false有区别
	* 欢迎页面(Welcome file list)
		* 默认页面的设置
	* JSP处理	
		* org.apache.jasper.servlet.JspServlet
		* development 配置是否开发模式  默认是true
	* 类加载(ClassLoading)
		* Bootstrap ClassLoader
		* System ClassLoader
		* Common ClassLoader
		* Webapp ClassLoader
		* 双亲委派
			*　
			 	  Bootstrap
			          |
			       System
			          |
			       Common
			       /     \
			  Webapp1   Webapp2 ...
			* 
	* 连接器(Connectors)
	* JDBC数据源(DataSource)
	* JNDI(Java Naming and Directory Interface)



> 系统的学习演练
怎么记：先猜、再验证、看API、记单词、有规律
公说公有理，婆说婆有理，用代码说话
习惯数据库关键字大写
