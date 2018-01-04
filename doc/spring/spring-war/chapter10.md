# 通过Spring和JDBC征服数据库
* 持久层，可以使用JDBC、Hibernate、Java持久化API(Java Persisitence API,JPA)

## Spring的数据访问哲学
* Spring的一个目标：支持面向对象(OO)原则中的"针对接口编程"
* SQLException常见问题：
	* 应用程序无法连接数据库
	* 要执行的查询存在语法错误
	* 查询中所使用的表和/或列不存在
	* 试图插入或更新的数据违反了数据库的约束
* Spring为读取和写入数据库的几乎所有错误都提供了异常
* Spring认为触发异常的很多问题是不能在catch代码块中修复，使用了非检查型异常，把是否要捕获异常的权利留给了开发人员
*　模板方法模式：
	* Spring的模板类处理数据访问的固定部分-事务控制、管理资源以及处理异常
	* 应用程序相关的数据访问-语句、绑定参数以及整理结果集-在回调的实现中处理
	* 只需要关心自己数据访问逻辑即可

## 配置数据源
* 使用JNDI数据源
	* 好处在于数据源完全可以在应用程序之外进行管理
	* 数据源通常以池的方式组织，从而具备更好的性能
	* 支持系统管理员对其进行热切换
	* 样例  JNDIConfig
* 使用数据源连接池：DBCP、c3p0、durid，例DBCPConfig
* 基于JDBC驱动的数据源
	* DriverManagerDataSource，在每个连接请求时都会返回一个新建的连接，提供的连接没有进行池化管理
	* SimpleDriverDataSource，直接使用JDBC驱动，来解决在特定环境下的类加载问题，这样的环境包括OSGI容器
	* SingleConnectionDataSource，在每个连接请求时都会返回同一个的连接，不是严格意义上的连接池数据源，可视为只有一个连接的池
* 使用嵌入式的数据源
	* 嵌入式数据库(embedded database)，作为应用的一部分运行
	* 适合开发和测试 例：EmbeddedConfig
* 使用profile选择数据源
	* 开发环境适合使用嵌入式数据库
	* QA环境适合DBCP
	* 生产部署环境适合<jee:jndi-lookup>
	
## 在Spring中使用JDBC
* JDBC优点：允许使用数据库的所有特性
* JDBC缺点：大量的JDBC代码都是用于创建连接和语句以及异常处理的样板代码
* Spring的JDBC框架承担了资源管理和异常处理的工作，从而简化了JDBC代码，JdbcTemplate

> 数据是应用程序的血液










