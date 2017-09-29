# Spring Boot WebSocket

## 现实中的挑战
* 双向通讯的需求
* 案例：
	* 股票交易系统
	* 社交网络
	* 线上游戏
* 为什么不用HTTP？
	* HTTP起初设计为文本传输协议
	* HTTP是半双工，一次单向通讯
	* HTTP的限制，实现实时，双向Web通讯非常麻烦
	* HTTP请求头和响应头信息造成不必要的通讯负载
* HTTP做的努力
	* AJAX(Asynchronous JavaScript and XML)，现在大多使用的是JSON
		* 特征：页面修改无需整体刷新，用户体验感觉低延迟
		* 不足：采用拉的模式，无法避免HTTP头负载的问题
	* Polling
		* 特征：接近于实时，使用AJAX应用模拟实施通讯，浏览器发送规律的间隔请求
		* 不足：在消息低频的情况，太多连接没必要地打开和关闭
	* Long Polling
		* 特征：又异步Polling，浏览器发送请求到服务器，浏览器保持请求打开一段时间
		* 不足：HTTP头通讯通常造成大量的网络堵塞，消息高频情况，导致连续的拉循环
	* Stream	
		* 特征：效率更高
		* 不足：时常问题频发，代理和防火墙可能引起的复杂性，响应必须周期性的构建和flush，浏览器跨域连接限制
* Comet
	* 一种长期持有HTTP请求Web应用模型，允许Web服务器向浏览器推送数据
	* Comet是一个整体的术语(Umbrella term)，包括多种技术完成交互，包括AJAX Push、Reverse Ajax、Two-way-web、HTTP Streaming and HTTP server push

## 介绍
* WebSocket是一种通讯协议，通过单个TCP连接提供完全多工(full-duplex)通讯管道。
* 协议握手
	* 建立连接：客户端发送握手请求，服务器返回握手的响应
* 术语：
	* 端点 Endpoint
	* 连接 Connection
	* 对点 Peer
	* 会话 Session
	* 客户端端点、服务器端点
* 端点生命周期(Endpoint Lifecycle)
	* 打开连接
		* Endpoint#onOpen(Session.EndpointConfig)
		* @OnOpen
	* 关闭连接
		* Endpoint#onClose(Session.CloseReason)
		* @OnClose
	* 错误
		* Endpoint#onError(Session,Throwable)
		* @OnError
* 会话 (Sessions)
	* API:Javax.websocket.Session
	* 接受消息：javax.websocket.MessageHandler  部分、整体
	* 发送消息：javax.websocket.RemoteEndpoint
* 配置(Configuration)
	* 服务端配置(javax.websocket.ServerEndpointConfig)
		* URI映射
		* 子协议协商
		* 扩展点修改
		* Origin检测
		* 握手修改
		* 自定义端点创建
	* 客户端配置(javax.websocket.ClientEndpointConfig)
		* 子协议
		* 扩展点
		* 客户端配置修改 
		
		
		
## Java WebSocket API(JSR-356)
## Spring WebSocket 抽象
## WebSocket Spring Boot整合





