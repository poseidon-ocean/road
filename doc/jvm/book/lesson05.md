# 调优案例分析与实战

## 案例分析

### 高性能硬件上的程序部署策略
* 案例：一个15万PV/天左右的在线文档类型网站
	* 更换硬件系统，新的硬件4个CPU、16GB物理内存，操作系统64位CentOS 5.4，Resin作为Web服务器
	* 访问量不大，没有其它服务
	* 64位JDK1.5，通过-Xmx和-Xms参数将Java堆固定在12GB
	* 使用一段时间后，网站经常不定期出现长时间失去响应
* 监控服务器运行状况发现网站失去响应是由GC停顿导致的
	* 虚拟机运行在Server模式，默认使用吞吐量优先收集器，回收12G的堆，一次Full GC的停顿时间高达14秒
	* 由于程序设计的关系，访问文档要把文档从磁盘提取到内存中，导致内存中出现很多大对象，这些大对象都进入老年代
	* 没有在Minor GC中清理掉，即使有12G的堆，内存也很快被消耗殆尽，由此导致每隔十几分钟出现十几秒的停顿
* 在高性能硬件上部署程序，两种方式
	* 通过64位JDK来使用大内存
	* 使用若干个32位虚拟机建立逻辑集群来利用硬件资源
* 使用64位JDK管理大内存，可能问题：
	* 内存回收导致的长时间停顿
	* 性能普遍低于32位JDK
	* 产生堆溢出几乎无法产生堆转快照，因为要产生十几G乃至更大的Dump文件
	* 内存消耗一般比32位大，由于指针膨胀导致
* 逻辑集群部署，可能问题：
	* 节点竞争全局的资源，如磁盘竞争，各节点同时访问某磁盘文件容易导致IO异常
	* 很难最高效利用资源池，如连接池，各个节点有独立的连接池
	* 受到32位的内存限制
	* 大量使用本地缓存会造成较大内存浪费，可考虑改为集中式缓存
* 上面案例最后部署：
	* 建立5个32位JDK的逻辑集群
	* 每个进程2GB内存（固定1.5GB）
	* 另外建立一套Apache服务作为前端均衡代理访问门户
	* 由于用户对响应速度比较关心和文档服务压力集中在磁盘和内存，使用CMS收集器

### 集群间同步导致的内存溢出
* 案例：一个基于B/S的MIS系统
	* 硬件2个CPU、8GB内存的HP小型机，服务器WebLogic 9.2
	* 每台服务器启动3个WebLogic，构成一个6节点的亲合式集群
	* 由于亲合式集群，没有session共享，部分需求共享数据存放数据库
	* 由于读写频繁竞争激烈，性能影响大，后使用JBossCache构建全局缓存
	* 全局缓存启用后，使用了一段时间，不定期出现多次的内存溢出
* 分析：
	* 程序未更新或升级，添加参数-XX:+HeapDumpPnOutOfMemoryError
	* 获取最近一次溢出信息：存在大量org.jgroups.protocols.pbcast.NAKACK对象
	* JBossCache基于自带JGroups进行集群间的数据通信
		* JGroups使用协议栈的方式来实现收发数据包
		* 数据包接收和发送时要经过每层协议的up()与down()方法
		* 其中NAKACK栈用于保障各个包的有效顺序及重发
	* 由于信息传输失败需要重发，返回正确信息前，发送信息必须保存在内存中
	* MIS的服务端有安全校验的全局Filter
		* 每当接收到请求均会更新最新一次操作时间
		* 并且把这个时间同步到多余节点中
		* 使得一个用户在一段时间内不能在多台机器上登录
	* 在服务使用过程中，一个页面会产生数次乃至数十次的请求，因此这个过滤器导致各节点之间网络频繁交互
	* 当网络情况不能满足传输要求时，重发数据在内存不断堆积，很快就产生内存溢出

### 堆外内存导致的溢出错误
* 添加参数-XX:+HeapDumpPnOutOfMemoryError，抛出异常溢出没有什么文件产生
* 挂着jstat并一直盯着屏幕：GC不频繁，Eden区、Survivor区、老年代以及永久内存都“情绪稳定，压力不大”，但照样不停抛出内存溢出
* 问题原因：见lesson02 直接内存
* 场景：
	* Direct Memeory:可通过 -XX:MaxDirectMemorySize调整大小，内存不足抛OutOfMemoryError或OutOfMemoryError:Direct buffter memory
	* 线程堆栈：
		* 可通过-Xss调整大小
		* 内存不足抛StackOverflowError(纵向无法分配，即无法分配新的栈帧)
		* 或OutOfMemoryError:unable to create new native thread(横向无法分配，即无法建立新的线程)
	* Socket缓存区：
		* 每个Socket连接都Receive和Send两个缓存区，分别大约37KB和25KB内存，连接多的话这块内存占用比较可观
		* 如无法分配，可能抛出IOException:Too many open files
	* JNI代码：如果代码使用JNI调用本地库，那本地库使用的内存也不在堆中
	* 虚拟机和GC：虚拟机、GC的代码执行也要消耗一定的内存

### 外部命令导致系统缓慢
*　使用Runtime.getRuntime().exec()执行shell脚本(获取系统信息)
	* 频繁这个操作，系统消耗大
	* CPU、内存负担也重
	* 去掉shell脚本换Java的API

### 服务器JVM进程崩溃
* 异常信息：Connection reset
* 是远端断开连接的异常，调用OA服务，需要3分钟才能返回，返回的都是中断连接
* 原因：
	* 当前系统用户多，为了不被OA系统速度拖累，使用异步调用
	* 由于两边服务速度不对等，时间长积累了很多web服务没有调用，导致等待的线程和Socket连接越多
	* 超过虚拟机的承受能力后虚拟机进程崩溃
* 解决：
	* 通知OA修复无法调用的集成接口
	* 异步调用改为生产者/消费者模式的消息队列

### 不恰当数据结构导致内存占用过大
* 如使用HashMap<Long, Long>结构来存储数据文件空间效率太低
* 分析上面的空间效率：
	* 在HashMap<Long, Long>结构中，只有Key和Value所存放的两个长整型数据是有效数据，共16B(2x8B)
	* 这两个长整型数据包装成java.lang.Long对象之后，就分别具有8B的MarkWord、8B的Klass指针，在加8B存储数据的long值
	* 在这两个Long对象组成Map.Entry之后，又多了16B的对象头，然后一个8B的next字段和4B的int型的hash字段，为了对齐，还必须添加4B的空白填充
	* 还有HashMap中对Entry的8B的引用，这样增加了两个长整型数字
	* 实际耗费的内存：(Long(24B)x2) + Entry(32B) + HashMap Ref(8B) = 88B
	* 空间效率：16B / 88B = 18%，实在太低了

### 由Windows虚拟内存导致的长时间停顿
* 在Java的GUI程序中需要注意

## 实战：Eclipse运行速度调优






















> 学到的新东西，想办法在各种场景中使用
人生在最难熬的时候，谁也替不了你，只能靠你自己咬紧牙关。咬的太狠了，最后就得有一个好牙医
书痴者文必工 艺痴者技必良
