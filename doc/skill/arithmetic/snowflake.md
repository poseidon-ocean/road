# 什么是SnowFlake算法？

## UUID
* 通用唯一识别码(Universally Unique Identifier)，在其它语言中也叫GUID
	* 可以生成一个长度32位的全局唯一识别码
```
String uuid = UUID.randomUUID().toString();
```
* UUID虽然可以保证全局唯一，但是占用32位有些太长，而且是无序的，入库时性能比较差
* 为什么无序的UUID会导致入库性能变差呢
	* 涉及B+树索引的分裂
```
	  1	  5
1 2 3 	->	5 6 7
```
* 关系型数据库的索引大都是B+树的结构
* 如果插入完全无序，不但会导致一些中间节点产生分裂，也会白白创造出很多不饱和的节点
	* 大大降低了数据库插入的性能

## 数据库自增主键
* 每一次生成ID的时候，访问数据库，执行下面的语句
```
begin;
REPLACE INTO table ( feild )  VALUES ( 'a' );
SELECT LAST_INSERT_ID();
commit;
```
* REPLACE INTO 的含义是插入一条记录，如果表中唯一索引的值遇到冲突，则替换老数据
	* 每次都可以得到一个递增的ID
* 为了提高性能，在分布式系统中可以用DB proxy请求不同的分库，每个分库设置不同的初始值，步长和分库数量相等
	* 这样一来，DB1生成的ID是1,4,7,10,13....，DB2生成的ID是2,5,8,11,14.....
* 这样不是很好，ID的生成对数据库严重依赖，不但影响性能，而且一旦数据库挂掉，服务将变得不可用

## SnowFlake
* SnowFlake所生成的ID一共分成四部分
	* 第一位 --占用1bit，其值始终是0，没有实际作用
	* 时间戳 -- 占用41bit，精确到毫秒，总共可以容纳约69 年的时间
	* 工作机器id -- 占用10bit，其中高位5bit是数据中心
		* ID（datacenterId），低位5bit是工作节点
		* ID（workerId），做多可以容纳1024个节点
	* 占用12bit，这个值在同一毫秒同一节点上从0开始不断累加，最多可以累加到4095
* SnowFlake算法在同一毫秒内最多可以生成多少个全局唯一ID呢
	* 同一毫秒的ID数量 = 1024 X 4096 =  4194304
	* 这个数字在绝大多数并发场景下都是够用的
* 优点
	* 1.生成ID时不依赖于DB，完全在内存生成，高性能高可用。
	* 2.ID呈趋势递增，后续插入索引树的时候性能较好。
* 缺点
	* 依赖于系统时钟的一致性。如果某台机器的系统时钟回拨，有可能造成ID冲突，或者ID乱序
	











