# Spring JMS

## 介绍
* Spring提供了一个JMS的集成框架，简化了JMS API的使用，类似Spring 集成JDBC API
* JmsTemplate类用于消息生产和消息的同步接收
* org.springframework.jms.core核心包
	* JMS模版类，用来处理资源的创建与释放
	* JMS类，用来发送消息、同步使用消息以及向用户公开JMS会话和消息的生产
* org.springframework.jms.support  提供转换 JMSException 的功能
* org.springframework.jms.support.converter 提供了MessageConverter抽象，进行Java对象和JMS消息的互换
* org.springframework.jms.support.destination包提供了管理 JMS 目的地的不同策略，比如针对 JNDI 中保存的目标的服务定位器。
* org.springframework.jms.annotation包提供了支持注解驱动监听端点的必要基础架构，通过使用@JmsListener实现。
* org.springframework.jms.config包提供了 JMS 命名空间的解析实现，以及配置监听容器和创建监听端点的 java 配置支持。
* org.springframework.jms.connection包提供了适用于独立应用程序的ConnectionFactory实现。 它还包含 Spring 对 JMS 的PlatformTransactionManager实现（即JmsTransactionManager）。这将允许 JMS 作为事务性资源无缝集成到 Spring 的事务管理机制中。

## 使用
	实现 ActiveMQ RabbitMQ

### JmsTemplate
* JmsTemplate类是JMS核心包中的中心类。它简化了 JMS 的使用，因为在发送或同步接收消息时它帮我们处理了资源的创建和释放
* JMS API公开了发送方法的两种类型，一种接受交互模式、优先级和存活时间作为服务质量(QOS)参数，另一种则使用缺省值作为 QOS 参数（无需参数）方式
* 使用setReceiveTimeout属性设置用于同步接收调用的超时值

### Connections
* ConnectionFactory是 JMS 规范的一部分，并被作为使用 JMS 的入口。
* 客户端应用通常作为一个工厂配合 JMS 提供者去创建连接，并封装一系列的配置参数，其中一些是和供应商相关的，例如 SSL 的配置选项。

### 缓存消息资源
标准的API涉及创建许多中间对象。要发送消息，将执行以下“API”步骤
ConnectionFactory->Connection->Session->MessageProducer->send
在ConnectionFactory和Send操作之间，有三个中间对象被创建和销毁。 为了优化资源使用并提高性能，提供了两个ConnectionFactory的实现。

### SingleConnectionFactory
Spring 提供ConnectionFactory接口的一个实现



引：
	http://ifeve.com/spring-5-26-jms-java-message-service/





































