# Spring boot 日志

## 日志框架 Log4j
* Log4j API
	* 日志对象（org.apache.log4j.Logger）
	* 日志级别（org.apache.log4j.Level）
	* 日志管理器（org.apache.log4j.LogManager）
	* 日志仓储（org.apache.log4j.spi.LoggerRepository）
	* 日志附加器（org.apache.log4j.Appender）
	* 日志过滤器（org.apache.log4j.spi.Filter）
	* 日志格式布局（org.apache.log4j.Layout）
	* 日志事件（org.apache.log4j.LoggingEvent）
	* 日志配置器（org.apache.log4j.spi.Configurator）
	* 日志诊断上下文（org.apache.log4j.NDC、org.apache.log4j.MDC）
* 日志对象（org.apache.log4j.Logger）是最核心的API
* 日志级别（org.apache.log4j.Level）
	* OFF
	* FATAL
	* ERROR
	* INFO
	* DEBUG
	* TRACE
	* ALL
* API 层次
	* -org.apache.log4j.Priority
			* -org.apache.log4j.Level
* 日志附加器（org.apache.log4j.Appender）
	* 日志附加器是日志事件（org.apache.log4j.LoggingEvent）具体输出的介质，如：控制台、文件系统、网络套接字等。
	* 日志附加器（org.apache.log4j.Appender）关联零个或多个日志过滤器（org.apache.log4j.Filter），这些过滤器形成过滤链。
	* 主要职责
		* 附加日志事件（org.apache.log4j.LoggingEvent）
		* 关联日志布局（org.apache.log4j.Layout）
		* 关联日志过滤器（org.apache.log4j.Filter）
		* 关联错误处理器（org.apache.log4j.spi.ErrorHandler）
* 日志附加器（org.apache.log4j.Appender）
	* 控制台实现：org.apache.log4j.ConsoleAppender
	* 文件实现
		* 普通方式：org.apache.log4j.FileAppender
		* 滚动方式：org.apache.log4j.RollingFileAppender
		* 每日规定方式：org.apache.log4j.DailyRollingFileAppender
	* 网络实现
		* Socket方式：org.apache.log4j.net.SocketAppender
		* JMS方式：org.apache.log4j.net.JMSAppender
		* SMTP方式：org.apache.log4j.net.SMTPAppender
	* 异步实现：org.apache.log4j.AsyncAppender
* 日志过滤器（org.apache.log4j.spi.Filter）
	* 日志过滤器用于决策当前日志事件（org.apache.log4j.spi.LoggingEvent）是否需要在执行所关联的日志附加器（org.apache.log4j.Appender）中执行。
	* 决策结果有三种：
		* DENY：日志事件跳过日志附加器的执行
		* ACCEPT：日志附加器立即执行日志事件
		* NEUTRAL：跳过当前过滤器，让下一个过滤器决策
* 日志格式布局（org.apache.log4j.Layout）
	* 日志格式布局用于格式化日志事件（org.apache.log4j.spi.LoggingEvent）为可读性的文本内容。
	* 内建实现
		* 简单格式：org.apache.log4j.SimpleLayout
		* 模式格式：org.apache.log4j.PatternLayout
		* 提升模式格式：org.apache.log4j.EnhancedPatternLayout
		* HTML格式：org.apache.log4j.HTMLLayout
		* XML格式：org.apache.log4j.xml.XMLLayout
		* TTCC格式：org.apache.log4j.TTCCLayout
* 日志事件（org.apache.log4j.LoggingEvent）
	* 日志事件是用于承载日志信息的对象，其中包括：
		* 日志名称
		* 日志内容
		* 日志级别
		* 异常信息（可选）
		* 当前线程名称
		* 时间戳
		* 嵌套诊断上下文（NDC）
		* 映射诊断上下文（MDC）
* 日志配置器（org.apache.log4j.spi.Configurator）
	* 日志配置器提供外部配置文件配置log4j行为的API，log4j 内建了两种实现：
		* Properties 文件方式（org.apache.log4j.PropertyConfigurator）
		* XML 文件方式（org.apache.log4j.xml.DOMConfigurator）
* 日志诊断上下文
	* 日志诊断上下文作为日志内容的一部分，为其提供辅助性信息，如当前 HTTP 请求 URL
	* log4j 有两种类型的日志诊断上下文，分别是映射诊断上下文和嵌套诊断上下文：
		* 映射诊断上下文（org.apache.log4j.MDC）
		* 嵌套诊断上下文（org.apache.log4j.NDC）
		


> 网上的文章是靠不住的，人呢是不靠谱的
人呢是有主观记忆，代码是很好的解释方式
多看下英语国家的代码命名规则

