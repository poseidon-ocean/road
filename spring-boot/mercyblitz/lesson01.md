# 初体验
* 关注技术的脉络：技术本身和技术的思想
* 云计算、微服务、软件架构

## 微服务的介绍
* 概念
	* https://en.wikipedia.org/wiki/Microservices
	* SOA架构风格的变体，将应用程序构建为松耦合服务的集合
	* 服务应该是细粒度的，协议应该是轻量级的
	* 改进了模块化，使应用程序更容易理解、开发、测试
	* 小团队可以独立开发、部署和扩展各自的服务来并行开发
	* 支持持续交付和部署
* 目的
	* 解决单体应用(Monolithic Application)规模增加时所带来的问题
	* 不可能一种方案解决所有问题
* 关键字:Monolithic、SOA、架构、分布式
* 微服务是软件架构，不单是Java语言
* Simple to 开发 develop、测试test、部署deploy、扩展scale
* 单体应用 VS 微服务
	* 优势：开发Development、稳定Stability、性能Performance、部署Deployment
	* 不足：中心化Centralization、耦合Coupling、学习成本Learning Cost、伸缩Scale、持续支持Continuous Delivery
	* 单体应用，学习成本高，因为代码都耦合在一块，后面的开发可能写重复代码
	* 水桶效应：如果瓶颈在登录，单体应用伸缩困难
	* 微服务拓扑机构，部署维护不容易
* 面向服务架构(SOA) VS 微服务
	* 软件代码产生的思想，面向过程，面向对象
	* 类同
		* 面向服务Service-Oriented，松耦合Loose-Coupling、模块化Modular
		* 分布式计算Distributed Computing、平台无关性Independent Platform
		* 平台无关性，如REST风格，JSON\XML等格式
	* 差异
		* 微服务更加 "原子性"(Atomic)
		* 微服务：领域驱动设计(DDD)，偏理想化，说的多做的人少
		* 开发运维体系(DevOps),把开发和运维都做在一起，网状结构更需要运维
* 进程间通讯
	* 近端通讯：文件File、消息队列Message、共享内存Shared Memory
	* 远端通讯：套接字(Socket)、远程过程调用(RPC)
* 分布式计算
	* 模型：Client-Server、Peer To Peer
	* 分类：同步和异步
	*　通讯协议：HTTP、自定义协议
	* 传输介质Media:文本、二进制 
		* 乱码是二进制和编码关联的

## 微服务面临的挑战
* 技术挑战
	* 注册与发现 Registry and Discovery
	* 路由 Routing
	* 可靠性 Reliability，CAP原则(一致性，数据一致更新，可用性) 
	* 延迟 Latency，多进程，远程服务之间的调用 
	* 热点 Hotspot，负载均衡 
	* 短路 Circuit Break，保护，比如QBS达到2000,实在扛不住了，有选择性的短路200 
	* 伸缩 Scale
	* 异步 Async
	* 监控 Monitoring，没有成熟的监控方案
	* 配置 Configuration，动态更改配置
	* 数据同步 Data Sync，分布式之后 数据的划分(垂直还是水平)
	* 安全 Security，WEB的前端安全(XSS)，认证和鉴权、服务与服务之间是否有权限

## Java微服务实践
* 元编程 Meta Programming：元编程是基于编程而编程
	* 注解、XML、反射、表达式、脚本、配置基本都是元数据
	* 注解驱动，通过表达式来编程
	* 反射驱动
	* 表达式驱动
	*　Lambda
	* Script On JVM(Groovy)
	* 接口编程，契约编程，契约范围包括方法名称、方法入参(类型和顺序)、方法返回值已经异常情况等
* 监控
* jconsle 多方位查找问题
* Java规范地址：https://jcp.org/en/home/index
* Spring boot 文档地址：http://docs.spring.io/spring-boot/docs/2.0.0.M3/reference/pdf/


## Spring boot
* 把开发和运维整合到一起了
* http://spring.io/projects IO模块排在boot前面
* 契约式编程和方法编程，多看源码
* 新建demo  http://start.spring.io/
* 当看到新的东西不知道哪里来的，要去思考，去搜索，直接看源码和规范

	



引：https://segmentfault.com/l/1500000009515571/play
> 处处留心皆学问
> 流氓不看岁数，技术不问出处
> 一个程序员，三成的功底是写代码，七成的功底是分析和排查问题的能力
> 蛛丝马迹定位问题
> 猜想是分析问题的过程
> 平时写代码要多记，如什么时候用Map，什么时候用HashMap
> 不仅知道有哪些功能，还要思考是使用场景和背后的逻辑
> 学习资料：下载PDF 结合不同版本看看有什么新的功能和删减
> 不同项目，不同工作，可能使用的版本不一样
> 软件升级一定要非常非常谨慎，非常非常小心，如履薄冰战战兢兢，一丝失误会造成整个项目的失败，和让人沮丧，会传达不信任的信息
> 评估技术，一定要谨慎
> 技术都是通的
> 不过分迷信新技术，大多是老的技术做了些处理，需要了解相关背景
> Spring没有创新，都是整合其它的技术
> 规范不需要看源码，tomcat等规范的实现可以看看配置
> 看源码，看类没有意义，需要从主线看起，入口 @SpringBootApplication>@SpringBootConfiguration>@Configuration>@Component
> 带着问题去看，去思考，慢慢积累
> 掌握模式之后，成千上万的类也是在这些规律之中
> 设计模式、前缀模式、后缀模式，配置模式


