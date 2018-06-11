# 高性能框架Netty

> Netty is a NIO client server framework which enables quick and easy development of network applications such as protocol servers and clients. It greatly simplifies and streamlines network programming such as TCP and UDP socket server.

> Netty 是一个高性能、异步事件驱动的 NIO 框架，它提供了对 TCP、UDP 和文件传输的支持，
作为一个异步 NIO 框架，Netty 的所有 IO 操作都是异步非阻塞的，通过 Future-Listener
机制，用户可以方便的主动获取或者通过通知机制获得 IO 操作结果

## RPC的性能模型分析
* RPC Remote Procedure Call 远程过程调用

### 传统RPC
* 网络传输方式问题：
	* 传统的RPC框架或者基于RMI等方式的远程服务（过程）调用采用了同步阻塞IO
	* 当客户端的并发压力或者网络时延增大之后，同步阻塞IO会由于频繁的wait导致IO线程经常性的阻塞
	* 由于线程无法高效的工作，IO处理能力自然下降
* 序列化问题
	* Java序列化机制是Java内部的一直对象编码技术，无法跨语言使用
	* 对于其他开源框架，Java序列化后的码流太大，无论是网络传输还是持久化到磁盘，都会导致额外的资源占用
	* 序列化性能差（CPU资源占用高）
* 线程模型问题
	* 由于采用同步阻塞IO，这会导致每个TCP连接都占用1个线程
	* 由于线程资源是JVM虚拟机非常宝贵的资源，当IO读写导致无法及时释放时，
		* 会导致系统性能急剧下降，严重的甚至会导致虚拟机无法创建新的线程
	
### 高性能的三个主题
* 传输：用什么样的通道将数据发送给对方，BIO、NIO或AIO
	* IO模型在很大程度上决定了框架的性能
* 协议：采用什么样的通信协议，HTTP或者内部私有协议
	* 协议的选择不同，性能模型也不同
	* 相比于共有协议，内部私有协议的性能通常可以被设计的更优
* 线程：数据报如何读取
	* 读取之后的编解码在哪个线程进行
	* 编解码后的消息如何派发
	* Reactor线程模型的不同，对性能的影响也非常大

## Netty高性能之道

### 异步非阻塞通信
* 在IO编程过程中，当需要同时处理多个客户端接入请求时，可以利用多线程或者IO多路复用技术进行处理
	* IO多路复用技术通过把多个IO的阻塞复用到同一个select的阻塞上，
		* 从而使得系统在单线程的情况下可以同时处理多个客户端请求
	* 与传统的多线程/多进程模型比
		* IO多路复用的最大优势开销小
		* 系统不需要创建新的额外进程或者线程
		* 也不需要维护这些进程和线程的运行
		* 降低了系统的维护工作量，节省了系统资源
* Netty框架按照Reactor模式设计和实现的
	* Netty的IO线程NioEventLoop由于聚合了多路复用器Selector，可以同时并发处理成百上千个客户端Channel
	* 由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效率，避免由于频繁IO阻塞导致的线程挂起
	* 由于采用了异步通信模式，一个IO线程可以并发处理N个客户端连接和读写操作
	* 解决了同步阻塞IO一连接一线程模型，架构的性能、弹性伸缩能力和可靠性得到了极大的提升

### 零拷贝
* 接收和发送ByteBuffer采用DIRECT BUFFERS，使用堆外直接内存进行Socket读写，
	* 不需要进行字节缓冲区的二次拷贝	
* 提供了组合Buffer对象
	* 可以聚合多个ByteBuffer对象
	* 可以像操作一个Buffer那样方便的对组合Buffer进行操作
	* 避免了传统通过内存拷贝的方式将几个小Buffer合并成一个大的Buffer
* 文件传输采用了transferTo方法
	* 可以直接将文件缓冲区的数据发送到目标Channel
	* 避免了传统通过循环write方式导致的内存拷贝问题
	



http://netty.io/