package com.adagio.designpatterns.delegate;

/**
 * 委派模式
 * 不关心过程 只关心结果
 * 
 * 如DispatcherServlet#doDispatch
 * 权衡选择，进行委派
 * 
 * 客户请求、委派者、被委派者
 * 委派者持有被委派者的引用
 * 
 * 代理模式：注重的是过程，委派模式注重的是结果
 * 策略模式注重的是可扩展，委派模式注重内部的灵活和复用
 * 
 * 委派的核心就是分发调度
 *
 */
public class Dispatcher implements IExector {

	IExector exector;
	
	//项目经理虽然也执行任务
	//但是他的工作职责是不一样的
	@Override
	public void doing() {
		exector.doing();
	}

}
