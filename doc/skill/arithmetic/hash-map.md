# 什么是HashMap
* Java常见数据结构
* HashMap算法设计目的：快速定位到元素的位置(存或取)只经过一个时间复杂度和操作度 O(1)

## HashMap的底层原理
* HashMap是一个用于存储Key-Value键值对集合，每个键值对叫Entry
* 键值对分散在一个数组中，数组的每个元素的初始值是null，这个数组是HashMap的主干
* 常用方法：Get和Put

### Put方法的原理
* 调用Put方法的时候发生了什么呢？
	* 比如调用 hashMap.put("apple", 0) ，插入一个Key为“apple"的元素。
	*　这时候我们需要利用一个哈希函数来确定Entry的插入位置（index）：index =  Hash（“apple”）
	* 假定最后计算出的index是2，那么这个Entry存放在这个位置
* 因为HashMap的长度是有限的，当插入的Entry越来越多时，再完美的Hash函数也难免会出现index冲突的情况
* 这时候该怎么办呢？我们可以利用链表来解决。
	* HashMap数组的每一个元素不止是一个Entry对象，也是一个链表的头节点。
	* 每一个Entry对象通过Next指针指向它的下一个Entry节点。
	* 当新来的Entry映射到冲突的数组位置时，只需要插入到对应的链表即可：
* 需要注意的是，新来的Entry节点插入链表时，使用的是“头插法”。至于为什么不插入链表尾部，后面会有解释。

### Get方法的原理
* 使用Get方法根据Key来查找Value的时候，发生了什么呢？
	* 首先会把输入的Key做一次Hash映射，得到对应的index：
	* index =  Hash（“apple”）
	* 由于刚才所说的Hash冲突，同一个位置有可能匹配到多个Entry，这时候就需要顺着对应链表的头节点，一个一个向下来查找。
* 假设我们要查找的Key是“apple”：
	* 第一步，我们查看的是头节点Entry6，Entry6的Key是banana，显然不是我们要找的结果。
	* 第二步，我们查看的是Next节点Entry1，Entry1的Key是apple，正是我们要找的结果。
* 之所以把Entry6放在头节点，是因为HashMap的发明者认为，后插入的Entry被查找的可能性更大。

## HashMap默认的初始长度是多少？为什么这么规定
* HashMap默认的初始长度是16，并且每次自动扩展或是手动初始化时，长度必须是2的幂
* 之所以是16，是为了服务于从key映射到index的hash算法
* 之前说过，从Key映射到HashMap数组的对应位置，会用到一个Hash函数：index =  Hash（“apple”）
* 如何实现一个尽量均匀分布的Hash函数呢？我们通过利用Key的HashCode值来做某种运算。
* 取模运算方法简单，效率低；HashMap采用的是位运算
* 如何进行位运算呢？有如下的公式（Length是HashMap的长度）：
	* index =  HashCode（Key） &  （Length - 1） 
	* 下面我们以值为“book”的Key来演示整个过程：
		* 1.计算book的hashcode，结果为十进制的3029737，二进制的101110001110101110 1001。
		* 2.假定HashMap长度是默认的16，计算Length-1的结果为十进制的15，二进制的1111。
		* 3.把以上两个结果做与运算，101110001110101110 1001 & 1111 = 1001，十进制是9，所以 index=9。
	* 可以说，Hash算法最终得到的index结果，完全取决于Key的Hashcode值的最后几位。
* 这样做不但效果上等同于取模，而且大大提高了效率
* 为什么长度是16而不是10

## JDK建议 数组长度尽量 2^n？数组的格子数的数量和hash有关联？	
* ===> 思路推导
* JDK数组长度是length = 2^4 = 16 length -1 = 15
* H:		21 ===>2进展 ===>1 0 1 0 1
*										&
* length-1:15==>2进展====>0 1  1 1 1 
* INDEX:				   0 0	1 0	1 < 01111(推出好处之一：散列值不会出乌龙事件，不会散列出范围)
* 总结：length=2^n 原因是参与hash算法运算的是length-1，换算成2进展 所有权位 都有效值 1
* 散列越分散，length -1 换算成二进制权位O越多，那么会造成冲突概率越多

## 高并发下，为什么HashMap可能会出现死锁
## Java8中，HashMap的结构有什么样的优化









http://mp.weixin.qq.com/s/HzRH9ZJYmidzW5jrMvEi4w














