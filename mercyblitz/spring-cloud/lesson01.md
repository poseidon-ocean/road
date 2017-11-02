# Spring Cloud - 云原生应用

## 什么是Spring Cloud
* Spring Cloud为开发人员提供快速构建分布式系统的一些通用模式，其中包括：配置管理、服务发现、服务短路、智能路由、微型网关、控制总线、一次性令牌、全局锁、领导选举、分布式会话和集群状态。分布式系统间的协调导向样板模式，并且使Spring Cloud的开发人员能够快速地构建实现这些模式的服务和应用。这些服务和应用也将在任何环境下工作良好，无论是开发者的笔记本、还是数据中心裸机或者管控平台。
* http://cloud.spring.io/spring-cloud-static/Dalston.SR3/

## 12-Factor 应用
* https://12factor.net/zh_cn/
* Codebase：一份基准代码，多份部署
* Dependencies：显式声明依赖关系
* Config：在环境中存储配置
* Backing services：把后端服务当作附加资源
* Build,release,run：严格分离构建和运行
* Processes：以一个或多个无状态进程运行应用
* Port binding：通过端口绑定提供服务
* Concurrency：通过进程模型进行扩展
* Disposability：快速启动和优雅终止可最大化健壮性
* Dev/prod parity：尽可能的保持开发，预发布，线上环境相同
* Logs：把日志当作事件流
* Admin processes：后台管理任务当作一次性进程运行

## Bootstrap上下文
* Bootstrap上下文是ConfigurableApplicationContext实例
	* 由BootstrapApplicationListener在监听ApplicationEnvironmentPreparedEvent 时创建
* Spring事件/监听模式：ApplicationEvent/ApplicationListener
* SpringApplication是Spring Boot引导启动类
	* 与Spring上下文、事件、监听器以及环境等组件关系紧密
	* 提供了控制Spring Boot应用特征的行为方法
* Spring Boot应用运行监听器：SpringApplicationRunListener
* Spring Boot事件
	* 事件触发器：EventPublishingRunListener
	* ApplicationStartingEvent
	* ApplicationEnvironmentPreparedEvent
	* ApplicationPreparedEvent
	* ApplicationReadyEvent / ApplicationFailedEvent
```
package com.adagio.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring事件/监听器 演示
 *
 */
public class SpringEventListenerDemo {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		
		//增加监听器
		//context.addApplicationListener(new MyApplicationListener());
		
		//注册 Configuration 的方式
		context.register(MyApplicationListener.class);
		
		//上下文启动
		context.refresh();
		
		
		//发布事件
		context.publishEvent(new MyApplicationEvent("您有新的信息，请注意查收！"));
		context.publishEvent(new MyApplicationEvent(1));
		context.publishEvent(new MyApplicationEvent(1.1));
		
	}
	
	private static class MyApplicationListener implements ApplicationListener<MyApplicationEvent>{

		@Override
		public void onApplicationEvent(MyApplicationEvent event) {
			System.out.printf("MyApplicationListener receives event source: %s \n", event.getSource());
		}
		
	}
	
	
	private static class MyApplicationEvent extends ApplicationEvent {

		public MyApplicationEvent(Object source) {
			super(source);
		}
		
	}
	
}
```
*  Spring Boot / Spring Cloud上下文层次关系
	* Spring Boot上下文
		* 非Web应用：AnnotationConfigApplicationContext
		* Web应用：AnnotationConfigEmbeddedWebApplicationContext
	* Spring Cloud上下文：Bootstrap （⽗）

## Actuator Endpoints
* Actuator直译：传动装置，场景表示为生产而准备的特性(Production-ready features)
* 这些特性通过HTTP端口的形式，帮助开发人员管理和监控应用：
	* 监控类：端点信息、应用信息、外部化配置信息、指标信息、健康检查、Bean管理、Web URL映射管理、Web URL跟踪
	* 管理类：外部化配置、日志配置、线程dump、堆dump、关闭应用
* Spring Cloud扩展Actuator Endpoints
	* 上下文重启：/restart
	* 暂停：/pause
	* 恢复：/resume


> 参考 ：https://segmentfault.com/l/1500000011384570?_ea=2730888
























