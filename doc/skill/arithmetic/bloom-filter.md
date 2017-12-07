# BloomFilter布隆算法

## 案例-网络爬虫
* 爬虫原理：通过种子URL来顺藤摸关，爬取处网站关联的所有的自子网页，存入自己的网页库中
* 爬取出来的URL有可能存在重复，需要被丢弃。如何实现URL的去重？
* URL去重方案第一版：HashSet
	* 创建一个HashSet集合，把每一个URL字符串作为HashSet的key插入到集合当中，利用HashSet的key唯一性来对URL做去重
	* 每个URL按照20个字节来算，一亿个URL就是20亿字节，大约占了1.8G以上的空间。这么大的HashSet集合显然是不可取的
* URL去重方案第二版：Bitmap
	* Bitmap是一种节省空间的数据结构
	* 获取每一个URL的HashCode，根据HashCode的值来插入到Bitmap的对应位置。如果插入位置的值是1，说明URL已重复
	* 每一个URL只占一个bit，一亿个URL占约12MB，假设整个Bitmap的空隙比较多，额外空间占90%，总空间也不过是120M
	* String的HashCode方法虽然尽可能做到均匀分布，但仍然免不了会有冲突，两个不相同的URL被误判为重复URL

## 布隆算法
* 布隆算法由BloomFilter音译而来，是一种以Bitmap集合为基础的排重算法
* 应用场景：URL的排重，垃圾邮箱地址过滤等
* 字符串的HashCode会有一定几率出现重复，那么怎样能减少这种重复呢？
	* 正常HashCode的返回值是int类型，若重新实现HashCode方法，把返回值扩大成long类型，可以减少重复的几率
	* 这样的代价太大，如果把所有URL都hash成long类型变量，再大的Bitmap集合也装不下
	* 不能扩大单一HashCode的范围，可以增加HashCode的次数
	* 实现多个不同的Hash算法，让每一个URL都计算出多个Hash值，最后让各个Hash值一一比较
		* 1.把第一个URL按照三种Hash算法，分别生成三个不同的Hash值
			* www.aaa.com - HashA + HashB + HashC
		* 2.把第二个URL也按照三种不同Hash算法，分别生成三个不同的Hash值
			* www.bbb.com - HashA + HashB + HashC
		* 3.一次比较每一个Hash结果，只有当全部结果都相等时，才判定两个URL相同
			* HashA		5		!=		10
			* HashB		17		!=		12
			* HashC		9		==		9
	* 假设标准HashCode的重复几率是0.01%，那么3个Hash结果同时重复的几率就是0.01% ^ 3 = 0.000000001%
	* 以上基本就是布隆算法的核心思想。有一点不同，布隆算法会把每一个Hash结果都映射到同一个Bitmap上
		* 1.创建一个空的Bitmap集合
		* 2.把第一个URL按照三种Hash算法，分别生成三个不同的Hash值
			* www.aaa.com -- HashA 5 + HashB 17 + HashC 9
		* 3.分别判断5,17,9在Bitmap的对应位置是否为1，就认为该URL没有重复，于是把5,17,9的对应位置设置为1
		* 4.把第二个URL按照三种Hash算法，分别生成三个不同的Hash值
			* www.bbb.com -- HashA 10 + HashB 12 + HashC 9
		* 5.分别判断10，12，9在Bitmap中的对应位置是否为1，只要不同时为1，就认为该URL没有重复，于是把10，12，9的对应位置设置为1
		* 6.把第三个URL按照三种Hash算法，分别生成三个不同的Hash值
			* www.ccc.com -- HashA 4 + HashB 16 + HashC 11
		* 7.分别判断4，16，11在Bitmap的对应位置是否为1，只要不同时为1，就认为该URL没有重复，于是把4，16，11的对应位置设置为1
		* 8.把第四个URL按照三种Hash算法，分别生成三个不同的Hash值。
			* www.aaa.com -- HashA 5 + HashB 17 + HashC 9
		* 9.分别判断5，17， 9 在Bitmap的对应位置是否为1。判断的结果是 5，17， 9 在Bitmap对应位置的值都是1，所以判定该Url是一个重复的Url
* 如果某一个新的URL经过三次Hash结果是10，12，17，那是否也会当重复呢？
	* 1.URL按照三个Hash算法得到三个结果。
		* www.ddd.com -- HashA 10 + HashB 12 + HashC 17
	* 2.分别判断10，12， 17 在Bitmap的对应位置是否为1。判断的结果是 10，12， 17 在Bitmap对应位置的值都是1，所以判定该Url是一个重复的Url。
	* 这种情况叫做误判，布隆算法虽然极力降低了Hash冲突的几率，但是仍然有一定的误判率
	* 为了减少误判的几率，可以让Bitmap的空间更大一些，单个URL所做的Hash更多一些，总之是在空间和正确率上做出取舍
* 如果使用同一个Bitmap会出现误判，为什么不让每一种Hash算法的结果对应一个独立的Bitmap？
	* 占用的空间也会相应增加几倍，反而不如用HashSet
* 如果希望完全杜绝误判的情况，应该怎么做？
	* 对于爬虫来说，可以容许极少量的URL误判为重复
	* 如果是用于垃圾邮箱的过滤，可以考虑加上一个白名单，专门存储那些被误判的正常邮箱
			


> http://mp.weixin.qq.com/s/RmR5XmLeMvk35vgjwxANFQ