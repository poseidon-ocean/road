# DAO support
1.介绍

	在Spring中数据访问对象(DAO)旨在使JDBC,Hibernate,JPA或JDO等数据访问技术有
	一致的处理方法，并且访问尽可能简单。
	
2.一致的异常层级

	Spring为技术特定异常提供了一个适当的转化，例如:SQLException所属异常类层级用
	DataAccessException作为根异常，这些异常包括了原来的异常，所以不会有丢失可能
	出错信息的风险
	
	除了JDBC异常，Spring也包含了Hibernate特定异常，将他们转换为一组集中的运行时异常
	(对JDO和JPA异常仍然也是如此)，在合适的层次上处理多数不可恢复的持久化异常，而不会在dao
	上产生繁琐的catch-throw块和异常声明(仍然可以在认为合适的地方捕获和处理异常)。向上面
	提到的一样，JDBC异常(包括数据方言)也都转化为相同的层级结构，意味着在一个统一的项目模型中
	你也可以执行一些JDBC操作。
	
3.配置DAO或Repository类的注解

	使用@Repository注解是数据访问对象(DAOs)或库能提供异常转换的最好方式，这个注解还允许组件扫描，
	查找并配置你的DAOs和库，并且不需要为他们提供XML配置文件
	@Repository
	public class SomeMovieFinder implements MovieFinder{}
	
	任何DAO或库实现都需要访问持久的源，依赖于持久化技术的使用；例如一个基于JDBC的库需要访问
	JDBC DatatSource，一个基于JPA的库需要访问一个EntityManager，最简单的方式就是使用
	@Autowired,@Inject,@Resource,或@PersistenceContext这些注解中的一个完成资源的依赖注入，
	这是一个JPA库的例子：
	@Repository
	public class JpaMovieFinder implements MovieFinder{
		@PersistenceContext
		private EntityManager entityManager;
	}
	
	典型的JDBC支持：
	@Repository
	public class JdbcMovieFinder implements MovieFinder{
		
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		public void init(DataSource dataSource){
			this.jdbcTemplate = new JdbcTempalte(dataSource);
		}
	}
	





引：
	http://ifeve.com/spring-14-dao-support/