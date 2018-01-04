# 使用NoSQL数据库

## 使用MongoDB持久化文档数据
* 文档是独立的实体，文档数据库不适用于关联关系明显的数据
* Spring Data MongoDB提供了三种方式在Spring应用中使用MongoDB
	* 通过注解实现对象-文档映射
	* 使用MongoTemplate实现基于模板的数据库访问
	* 自动化的运行时Repository生成功能
* 启用MongoDB
	* 通过@EnableJpaRepositories注解启用Spring Data的自动化JPA Repository生成功能
	* @EnableMongoRepositories为MongoDB实现了相同的功能
* 为模型添加注解，实现MongoDB持久化
	* 用于对象-文档映射的Spring Data MongoDB注解
		* @Document 标示映射到MongoDB文档上的领域对象		类似JPA @Entity注解
		* @Id 标示某个域为ID域
		* @DbRef 标示某个域要引用的其它的文档，这个文档有可能位于另一个数据库中
		* @Field 为文档域指定自定义的元数据
		* @Version 标示某个属性用作版域
	* 注意：没有添加注解的属性，也会持久化为文档中域，除非设置瞬时态(transient)		
	* 注意：Order.items属性，不是 关联关系，会完全嵌入到Order中
* 使用MongoTemplate访问MongoDB
	* 配置类中配置的MongoTemplate bean,将其注入到使用的地方
	* @Autowired MongoOperations mongo;
	* MongoOperations是MongoTemplate所实现的接口
	* void save(Object objectToSave, String collectionName); 
	* save第一个参数是新创建的对象，第二个参数是要保存的文档存储的名称
* 编写MongoDB Repository
	* 使用Spring Data MongoDB来创建Repository
	* 通过@EnableMongoRepositories注解启用Spring Data MongoDB的Repository功能
	* 通过扩展MongoRepository接口，能够继承多个CRUD操作
* 查询方式：
	* 自定义查询
	* 指定查询
	* 混合定义查询
https://segmentfault.com/a/1190000010520535

## 使用Neo4j操作图数据
* 文档型数据库会存储到粗粒度的文档中
* 图形数据库会将数据存储到多个细粒度的节点中，这些节点之间通过关系建立关联
* 图形数据库的一个节点通常会对应数据库中的一个概念(concept)，具备描述节点状态的属性
* 连接两个节点的关联关系可能也会带有属性
*　Spring Data Neo4j提供了将java对象映射到节点和关联关系的注解、面向模版的Neo4j访问方式以及Repository实现的自动化生成功能
 
## 使用Redis操作key-value数据
* 连接到Redis
	* Redis连接工厂会生成Redis数据库服务器的连接
	* Spring Data Redis为四种Redis客户端实现提供了连接工厂
		* JedisConnectionFactory
		* JredisConnectionFactory
		* LettuceConnectionFactory
		* SrpConnectionFactory
* 配置Redis  RedisConfig
* 使用RedisTemplate






> 越想快一点的时候，越慢
> 越想要的，越难以得到
> 抓到越紧，越是徒劳


