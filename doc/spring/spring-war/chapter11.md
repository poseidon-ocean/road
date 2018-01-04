# 使用对象-关系映射持久化数据
* Spring对ORM框架的支持：
	* 支持集成Spring声明式事务
	* 透明的异常处理
	* 线程安全的、轻量级的模板类
	* DAO支持类
	* 资源管理

## 在Spring中集成Hibernate
* 使用Hibernate所需的主要接口是org.hibernate.Session
	* Session接口提供了基本的数据访问功能，如保存、更新、删除以及从数据库加载对象
	* 通过Session接口，应用程序的Repository能够满足所有的持久化需求
	
## Spring与Java持久化API
* Java持久化API(Java Persistence API,JPA)，基于POJO的持久化机制
* 在Spring中使用JPA的第一步是要在Spring应用上下文中将实体管理器工厂(entity manager factory)按照bean的形式来进行配置
* JPA定义了两种类型的实体管理器,都实现了接口EntityManager
	* 应用程序管理类型(Application-managed)，由EntityManagerFactory创建
	* 容器管理类型(Container-managed)，由PersistenceProvider的createEntityManagerFactory()创建
* 不管你采用何种方式得到EntityManagerFactory，一旦得到这样的对象，就可以编写Repository

## 编写基于JPA的Repository
* Spring对JPA集成提供了JpaTemplate模板以及对应的支持JpaDaoSupport
* 纯粹的JPA方式远胜于基于模板的JPA
* 构建不依赖Spring的JPA Repository -> JpaSpitterRepository

## 借助Spring Data实现自动化的JPA Repository
* Spring Data定义了一组小型的领域特定语言(domain-specific-language,DSL)
	* 持久化细节都是通过Repository方法的签名来描述的，findByUsername
	* 定义查询方法：get、read、find、count
	* 比较操作：IsAfter、After、IsBetween、Between、IsNull、Null、IsIn、IsTrue、True
	* int countProductsByDiscontinuedTrue()
	* List<Order> findByShippingDataBetween(Date start,Date end)
* 声明自定义查询 @Query
	* @Query("select s from Spitter s where s.email like '%gmail.com'")
	* List<Spitter> findAllGmailSpitter()
* 混合自定义的功能 SpitterRepositoryImpl
	





















