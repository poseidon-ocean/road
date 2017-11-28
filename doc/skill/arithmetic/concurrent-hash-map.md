# 什么是ConcurrentHashMap

* HashMap不是线程安全的。在并发插入元素时，有可能出现带环链表，让下一次读操作出现死循环
* 避免HashMap的线程安全问题，改用HashTable或者Collections.synchronizedMap
* 但是这两种方式都有性能问题，读写操作都会给整个集合加锁，导致同一时间其它操作阻塞
* 在并发环境下，ConcurrentHashMap能够兼顾线程安全和运行效率

## ConcurrentHashMap是怎么保证线程安全的
* 学习ConcurrentHashMap，最关键是要理解一个概念：Segment
* Segment是什么呢？Segment本身就相当于一个HashMap对象
* 同HashMap一样，Segment包含一个HashEntry数组，数组中的每一个HashEntry既是一个键值对，也是一个链表的头节点
* 在ConcurrentHashMap集合中有2的N次方个Segment对象，共同保存在一个名为segments的数组当中
*　ConcurrentHashMap是一个二级哈希表。在一个总的哈希表下面，有若干个子哈希表
* ConcurrentHashMap优势就是采用了锁分段技术，Segment直接互不影响
*　不同Segment的并发写入:不同Segment的写入是可以并发执行的
* 同一Segment的一写一读:同一Segment的写和读是可以并发执行的
* 同一Segment的并发写入:Segment的写入是需要上锁的，因此对同一Segment的并发写入会被阻塞
* ConcurrentHashMap当中每个Segment各自持有一把锁。在保证线程安全的同时降低了锁的粒度，让并发操作效率更高

## ConcurrentHashMap是怎么实现高性能读写的

### Get方法：
* 1.为输入的Key做Hash运算，得到hash值。
* 2.通过hash值，定位到对应的Segment对象
* 3.再次通过hash值，定位到Segment当中数组的具体位置。

### Put方法：
* 1.为输入的Key做Hash运算，得到hash值。
* 2.通过hash值，定位到对应的Segment对象
* 3.获取可重入锁
* 4.再次通过hash值，定位到Segment当中数组的具体位置。
* 5.插入或覆盖HashEntry对象。
* 6.释放锁。

## Segment详解
* 每一个Segment都各自加锁，那么在调用Size方法的时候，怎么解决一致性的问题
* Size方法的目的是统计ConcurrentHashMap的总元素数量， 自然需要把各个Segment内部的元素数量汇总起来
* 如果在统计Segment元素数量的过程中，已统计过的Segment瞬间插入新的元素，这时候该怎么办呢
* ConcurrentHashMap的Size方法是一个嵌套循环，大体逻辑如下
	* 1.遍历所有的Segment。
	* 2.把Segment的元素数量累加起来。
	* 3.把Segment的修改次数累加起来。
	* 4.判断所有Segment的总修改次数是否大于上一次的总修改次数。如果大于，说明统计过程中有修改，重新统计，尝试次数+1；如果不是。说明没有修改，统计结束。
	* 5.如果尝试次数超过阈值，则对每一个Segment加锁，再重新统计。
	* 6.再次判断所有Segment的总修改次数是否大于上一次的总修改次数。由于已经加锁，次数一定和上次相等。
	* 7.释放锁，统计结束。
* 官方源代码如下
```
public int size() {
    // Try a few times to get accurate count. On failure due to
   // continuous async changes in table, resort to locking.
   final Segment<K,V>[] segments = this.segments;
    int size;
    boolean overflow; // true if size overflows 32 bits
    long sum;         // sum of modCounts
    long last = 0L;   // previous sum
    int retries = -1; // first iteration isn't retry
    try {
        for (;;) {
            if (retries++ == RETRIES_BEFORE_LOCK) {
                for (int j = 0; j < segments.length; ++j)
                    ensureSegment(j).lock(); // force creation
            }
            sum = 0L;
            size = 0;
            overflow = false;
            for (int j = 0; j < segments.length; ++j) {
                Segment<K,V> seg = segmentAt(segments, j);
                if (seg != null) {
                    sum += seg.modCount;
                    int c = seg.count;
                    if (c < 0 || (size += c) < 0)
                        overflow = true;
                }
            }
            if (sum == last)
                break;
            last = sum;
        }
    } finally {
        if (retries > RETRIES_BEFORE_LOCK) {
            for (int j = 0; j < segments.length; ++j)
                segmentAt(segments, j).unlock();
        }
    }
    return overflow ? Integer.MAX_VALUE : size;
}
```
* 为什么这样设计呢？这种思想和乐观锁悲观锁的思想如出一辙
* 为了尽量不锁住所有Segment，首先乐观地假设Size过程中不会有修改。当尝试一定次数，才无奈转为悲观锁，锁住所有Segment保证强一致性
*　这里介绍的ConcurrentHashMap原理和代码，都是基于Java1.7的



> http://mp.weixin.qq.com/s/1yWSfdz0j-PprGkDgOomhQ

