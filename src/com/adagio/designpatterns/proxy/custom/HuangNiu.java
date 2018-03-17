package com.adagio.designpatterns.proxy.custom;

import java.lang.reflect.Method;

import com.adagio.designpatterns.proxy.jdk.Person;


/**
 * 使用jdk API实现动态代理
 *
 */
public class HuangNiu implements MyInvocationHandler {
	
	private Person target;
	
	public Object getInstance(Person target) {
		this.target = target;
		
		Class<? extends Person> clazz = target.getClass();
		System.out.println("被代理的class是：" + clazz);
		return MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		System.out.println(this.target);
		System.out.println("执行前");
//		System.out.println("姓名：" + this.target.getName() + " 年龄： " + this.target.getAge());
//		this.target.buyTicket();
		method.invoke(target, args);
		
		System.out.println("执行后");
		return null;
	}
	

}
