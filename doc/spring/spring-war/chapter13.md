# 缓存数据
* 缓存(Caching)可以存储经常会使用到的信息，这样每次需要的时候，这些信息都是立即可用的

## 启用对缓存的支持
* Spring对缓存的支持有两种
	* 注解驱动的缓存
	*　XML声明的缓存
* Spring3.1内置了五个缓存管理器
	* SimpleCacheManager
	* NoOpCacheManager
	* ConcurrentMapCacheManager
	* CompositeCacheManager
	* EhCacheCacheManager
* Spring 3.2引入了另一个缓存管理器，用在基于JCache的缓存提供商之中
* Spring Data提供了两个缓存管理器
	* RedisCacheManager(来自于Spring Data Redis项目)
	* GemfireCacheManager(来自于Spring Data GemFire项目)
*　使用Redis缓存
	*　缓存的条目不过是键值对(key-value pair),Redis作为key-value存储适合于存储缓存
	* Spring Data Redis提供了RedisCacheManager
* 使用多个缓存管理器 CompositeCacheManager

## 为方法添加注解以支持缓存
* Spring的缓存抽象在很大程度上是围绕切面构建的
* 在Spring中启用缓存时，会创建一个切面，它触发一个或更多的Spring的缓存注解
* Spring提供了四个注解来声明缓存规则，可以作用在方法或类上
	* @Cacheable 表明Spring在调用方法之前，首先应该在缓存中查找方法的返回值。如果这个值能够找到，就会返回缓存的值。否则的话，这个方法就会被调用，返回值会放到缓存之中。
	* @CachePut 表明Spring应该将方法的返回值放到缓存中，在方法的调用前并不会检查缓存，方法始终都会被调用
	* @CacheEvict 表明Spring应该在缓存中清除一个或多个条目
	* @Caching 这是一个分组的注解，能够同时应用多个其它的缓存注解
*　填充缓存
	* @Cacheable("spitterCache") Spitter findOne(long id); 
	* 当findOne被调用时，缓存切面会拦截调用并在缓存中查找名为spitterCache存储的返回值，缓存的key为id
	* 接口方法上添加注解，所有实现都会应用相同的缓存规则
*　将值放到缓存之中
	* @CachePut("spitterCache")	Spitter save(Spitter spitter);
	* 当save()方法被调用时，先保存，再返回Spitter存储到缓存之中
	* 这里需要指定一个key @CachePut("spitterCache",key="#spitter.id")
* 自定义缓存key
	* Spring提供了多个用来定义缓存规则的SpEL扩展
* 条件化缓存
	* @Cacheable("spitterCache", unless="#result.message.contains('NoCahce')", condition="#id >= 10") Spitter findOne(long id); 
	* 上面的缓存意思是id小于10的不会被缓存
* 移除缓存条目
	* @CacheEvict("spitterCache") void remove(long spittleId);
	* 数据被删除之后，对应的缓存应该移除











