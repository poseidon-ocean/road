# 深入剖析NIO原理

## 几个概念
* 阻塞(Block)和非阻塞(Non-Block)
	* 阻塞和非阻塞是进程在访问数据的时候，数据是否准备就绪的一种处理方式
	* 当数据没有准备的时候，阻塞：往往需要等待缓冲区中的数据准备好之后才处理其他的事情，否则一直等待在那里
	* 非阻塞:当我们的进程访问我们的数据缓冲区的时候，如果数据没有准备好则直接返回，不会等待。如果数据已经准备好，也直接返回
* 同步(Synchronization)和异步(Asynchronous)
	* 同步和异步都是基于应用程序和操作系统处理 IO 事件所采用的方式。
	* 同步：是应用程序要直接参与 IO 读写的操作。
	* 异步：所有的 IO 读写交给操作系统去处理，应用程序只需要等待通知。
	* 同步:阻塞到 IO 事件，阻塞到 read 或则 write。这个时候我们就完全不能做自己的事情。
	
## 面向流与面向缓冲
* IO 是面向流的，NIO 是面向缓冲区的
* Java IO面向流意味着每次从流中读一个或多个字节，直至读取所有字节，它们没有被缓存在任何地方
* NIO--数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区中前后移动

## 阻塞与非阻塞 IO
* Java IO 的各种流是阻塞的
	* 当一个线程调用 read() 或 write()时，该线程被阻塞，直到有一些数据被读取，或数据完全写入
* Java NIO 的非阻塞模式，使一个线程从某通道发送请求读取数据
	* 它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取
	* 不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情
	* 一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情
* 线程通常将非阻塞 IO的空闲时间用于在其它通道上执行 IO操作，所以一个单独的线程现在可以管理多个输入和输出通道（channel）

## 选择器（Selector）
* Java NIO的选择器允许一个单独的线程来监视多个输入通道
	* 可以注册多个通道使用一个选择器，然后失意一个单独的线程来“选择”通道
	* 这些通道可以处理输入，或者选择已准备写入的通道

## NIO 和 IO 如何影响应用程序的设计
1.对 NIO 或 IO 类的 API 调用。
2.数据处理。
3.用来处理数据的线程数。

## API 调用
使用 NIO 的 API 调用时看起来与使用 IO 时有所不同，并不是仅从一个 InputStream 逐字节读取，而是数据必须先读入缓冲区再处理

## Java NIO（Non-Block IO）
* 缓冲区（Buffer）、通道（Channel）、选择器（Selector）

### 缓存区Buffer
* Buffer操作基本API
	* 缓存区实际上是一个容器对象，其实就是一个数组
	* 在NIO库中，所有数据都是缓存区处理的
	* 在读取数据时，它是直接读到缓冲区中的； 
	* 在写入数据时，它也是写入到缓冲区中的；任何时候访问 NIO 中的数据，都是将它放到缓冲区中
	* 在面向流 I/O系统中，所有数据都是直接写入或者直接将数据读取到 Stream 对象中
* 深入剖析Buffer
	* 缓冲区对象是一个特殊的数组
		* 内置了一些机制，能够跟踪和记录缓冲区的状态变化情况
	* position：指定了下一个将要被写入或者读取的元素索引，它的值由 get()/put()方法自动更新，
		* 在新创建一个 Buffer 对象时，position 被初始化为 0
	* limit：指定还有多少数据需要取出(在从缓冲区写入通道时)，
		* 或者还有多少空间可以放入数据(在从通道读入缓冲区时)。
	* capacity：指定了可以存储在缓冲区中的最大数据容量，实际上，它指定了底层数组的大小，
		* 或者至少是指定了准许我们使用的底层数组的容量。
	* mark <= position <= limit <= capacity
* 缓存区的分配
	* 在创建一个缓冲区对象时，会调用静态方法 allocate()来指定缓冲区的容量，
	* 其实调用 allocate()相当于创建了一个指定大小的数组，并把它包装为缓冲区对象。
	* 或者我们也可以直接将一个现有的数组，包装为缓冲区对象
* 缓冲区分片
	* 可以根据现有的缓冲区对象来创建一个子缓冲区，
	* 即在现有缓冲区上切出一片来作为一个新的缓冲区，
	* 但现有的缓冲区与创建的子缓冲区在底层数组层面上是数据共享的，
	* 也就是说，子缓冲区相当于是现有缓冲区的一个视图窗口。
	* 调用 slice()方法可以创建一个子缓冲区
* 只读缓冲区
	* 通过调用缓冲区的asReadOnlyBuffer()方法，将任何常规缓冲区转 换为只读缓冲区
	* 如果尝试修改只读缓冲区的内容，则会报 ReadOnlyBufferException 异常
* 直接缓冲区
	* 直接缓冲区是为加快 I/O 速度，使用一种特殊方式为其分配内存的缓冲区
	* JDK 文档中的描述为：给定一个直接字节缓冲区，Java 虚拟机将尽最大努力直接对它执行本机 I/O 操作
	* 会在每一次调用底层操作系统的本机 I/O 操作之前(或之后)，尝试避免将缓冲区的内容拷贝到一个中间缓冲区中或者从一个中间缓冲区中拷贝数据。
	* 要分配直接缓冲区，需要调用 allocateDirect()方法，而不是 allocate()方法，使用方式与普通缓冲区并无区别
* 内存映射文件 I/O
	* 内存映射文件 I/O 是一种读和写文件数据的方法
	* 比常规的基于流或者基于通道的 I/O 快的多
	* 内存映射文件 I/O 是通过使文件中的数据出现为 内存数组的内容来完成的
	* 只有文件中实际读取或者写入的部分才会映射到内存中

### 通道 Channel
* 通道是一个对象，通过它可以读取和写入数据
* 所有数据都通过 Buffer 对象来处理
	* 永远不会将字节直接写入通道中，是将数据写入包含一个或者多个字节的缓冲区
	* 不会直接从通道中读取字节，而是将数据从通道读入缓冲区，再从缓冲区获取这个字节
* 用 NIO 读取数据可以分为下面三个步骤
	* 1. 从 FileInputStream 获取 Channel
	* 2. 创建 Buffer
	* 3. 将数据从 Channel 读取到 Buffer 中
* 用 NIO 写入数据
	* 1. 从 FileInputStream 获取 Channel
	* 2. 创建 Buffer
	* 3. 将数据从 Channel 写入到 Buffer 中

### 反应堆 Reactor
* 阻塞 I/O 通信模型
	* 道阻塞 I/O 在调用 InputStream.read()方法 时 是 阻 塞 的 
	* 在 调 用ServerSocket.accept()方法时，也会一直阻塞到有客户端连接才会返回
	* 缺点：
		* 当客户端多时，会创建大量的处理线程。且每个线程都要占用栈空间和一些 CPU 时间
		* 阻塞可能带来频繁的上下文切换，且大部分上下文切换可能是无意义的
* Java NIO 原理及通信模型
	* 1. 由一个专门的线程来处理所有的 IO 事件，并负责分发。
	* 2. 事件驱动机制：事件到的时候触发，而不是同步的去监视事件。
	* 3. 线程通讯：线程之间通过 wait,notify 等方式通讯。保证每次上下文切换都是有意义的。减少无谓的线程切换。
* 选择器 Selector
	* 传统的 Server/Client 模式会基于 TPR（Thread per Request）,服务器会为每个客户端请求建立一个线程
	* NIO 中非阻塞 I/O 采用了基于 Reactor 模式的工作方式，I/O 调用不会被阻塞
	* Selector 就是注册各种 I/O 事件地 方
	* 使用 NIO 中非阻塞 I/O 编写服务器处理程序
		* 1. 向 Selector 对象注册感兴趣的事件
		* 2. 从 Selector 中获取感兴趣的事件
		* 3. 根据不同的事件进行相应的处理

## Java AIO（Asynchronous IO）
* 服务端:AsynchronousServerSocketChannel
* 客服端:AsynchronousSocketChannel	
* 用户处理器:CompletionHandler 接口,这个接口实现应用程序向操作系统发起 IO 请求,当完成后处理具体逻辑，否则做自己该做的事情
```
“真正”的异步IO需要操作系统更强的支持。在IO多路复用模型中，事件循环将文件句柄
的状态事件通知给用户线程，由用户线程自行读取数据、处理数据。而在异步IO模型中，
当用户线程收到通知时，数据已经被内核读取完毕，并放在了用户线程指定的缓冲区内，
内核在IO完成后通知用户线程直接使用即可。异步IO模型使用了Proactor设计模式实现了
这一机制
```



	