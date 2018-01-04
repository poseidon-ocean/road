# 使用Spring Web Flow
* 创建会话式的Web应用程序
* 定义流程状态和行为
* 包含Web流程

## 在Spring中配置Web Flow
* Spring现在还不支持在Java中配置Spring Web Flow，只能在XML中对其进行配置
* 装配流程执行器(flow executor)
	* 当用户进入一个流程时，流程执行器会为用户创建并启动一个流程执行实例。
	* 当流程暂停时(如为用户展示视图时)，流程执行器会在用户执行操作后恢复流程
* 配置流程注册表
	* 流程注册表(flow registry)的工作是加载流程定义并让流程执行器能够使用它们
* 处理流程请求
	* DispatcherServlet一般将请求分发给控制器
	* 对于流程而言，需要一个FlowHandlerMapping来帮助DispatcherServlet将流程请求发送给Spring Web Flow
* 响应请求 FlowHandlerAdapter

## 流程的组件
* 在Spring Web Flow中，流程是由三个主要元素定义的：状态、转移和流程数据
* 状态(Sate)是流程中事件发生的地点，流程中的状态是业务逻辑执行、做出决策或将页面展现给用户的地方
	* 行为(Action)：行为状态是流程逻辑发生的地方
	* 决策(Decision)：决策状态将流程分成两个方向，它会基于流程数据的评估结果确定流程方向
	* 结束(End)：结束状态是流程的最后一战，一旦进入End状态，流程就会终止
	* 子流程(Subflow)：子流程状态会在当前正在运行的流程上下文中启动一个新的流程
	* 视图(View)：视图状态会暂停流程并邀请用户参与流程
* 视图状态：用于为用户展现信息并使用户在流程中发挥作用
	* 视图实现可以是DSpring支持是任意视图类型，通常是JSP
	* 流程定义在XML文件中，<view-state id="welcome">,id是这个视图的标识和视图名，也可以单独定义是视图名 view="greeting"
	* <view-state id="takePayment" model="flowScope.paymentDetails">	指明表单绑定的对象
* 行为状态：应用程序自身在执行任务
	* 一般会触发Spring所管理bean的一些方法并根据方法调用的执行结果转移到另一个状态
	* <action-state id="saveOrder"><evaluate expression=""/><transition for="" /></action-state>
	* <evaluate>元素给出了行为状态要做的事情，expression属性指定了进入状态时要评估的表达式
* 决策状态：可能线性执行，一个状态到另一个状态，更多的是流程在某一个节点根据流程的当前情况进入不同的分支
	* <decision-state id="checkDeliveryArea"><if test="" then="" else="" /></decision-state>
* 子流程状态：允许一个正在执行的流程调用另一个流程
	* <subflow-state id="" subflow=""><input name="" value="" /><transition on="" to=""/></subflow-state>
* 结束状态：<end-state id="" />
	* 如果一个结束的流程是一个子流程，那调用它的流程将会从<subflow-state>处继续执行
		* <end-state>的ID将会用做事件触发从<subflow-state>开始的转移
	* 如果<end-state>设置了view属性，指定的视图将会被渲染。
		* 添加"externalRedirect:"前缀：会重定向到流程外部的页面
		* 添加"flowRedirect:"前缀：重定向到另一个流程
	* 如果的结束的流程不是子流程，也没有指定view属性，那这个流程只会结束而已
		* 浏览器最后将会加载的基本URL地址，当前已没有活动的流程，所以会开始一个新的流程实例
* 转移：连接了流程中的状态<transition on="" to=""/>
	* 属性to用于指定流程的下一个状态
	* 属性on指定触发转移的事件
	* 属性on-exception类似于on，指定要发生转移的异常
	* 全局转移：<global-transition><transition on="" to=""/></global-transition>
* 流程数据，在整个流程中传递并使用的数据
	* 声明变量		
		* 简单的形式 <var name="" class=""/> 
		* 行为状态的一部分 <evaluate result="" expression="" />
		* 设置变量的值 <set name="" value=""/>
	* 五种不同的作用域
		* Conversation：最高层级的流程开始时创建，在最高层级的流程结束时销毁。被最高层级的流程和其所有的子流程所共享
		* Flow：当流程开始时创建，在流程结束时销毁。只有在创建它的流程中可见的
		* Request：当一个请求进入流程时创建，在流程返回时销毁
		* Flash：当流程开始时创建，在流程结束时销毁。在视图状态渲染后，它也会被清除。
		* View：当进入视图状态时创建，当这个状态退出时销毁。只在视图状态内是可见的
	* 使用<var>元素声明的变量，在定义变量的流程内有效
	* 使用<set>或<evaluate>，作用域通过name或result属性前缀指定

## 组合起来：披萨流程
* 流程定义的组成部分是流程的状态
* flow元素的start-state属性可以将任意状态指定为开始状态
* 复杂活动(识别顾客、构造披萨订单、支付)，不适合当作一个状态，单独定义为一个流程

## demo源码
* 链接：http://pan.baidu.com/s/1dFIj2BR 密码：9ifl








