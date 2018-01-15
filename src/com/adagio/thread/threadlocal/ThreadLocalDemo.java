package com.adagio.thread.threadlocal;

import java.util.Random;

/**
 * 线程范围内的数据共享
 * 
 * 使用JDK提供的API - ThreadLocal
 *
 */
public class ThreadLocalDemo {

	private final static ThreadLocal<Integer>  dataMap = new ThreadLocal<>();
	
	public static void main(String[] args) {
		for(int i=0; i<2;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int data = new Random().nextInt(100);
					System.out.println(Thread.currentThread().getName() + " has put data :" + data);
					dataMap.set(data);
					
					ThreadScopeData.getThreadInstance().setName("name " + data);
					ThreadScopeData.getThreadInstance().setAge(data);
					
					new A().get();
					new B().get();
					
					
				}
			}).start();
		}
	}
	
	static class A {
		
		public void get() {
			int data = dataMap.get();
			System.out.println("A from " + Thread.currentThread().getName() + " get data:" + data);
			
			ThreadScopeData threadScopeData = ThreadScopeData.getThreadInstance();
			System.out.println("A from " + Thread.currentThread().getName() + " get threadData:" + threadScopeData.getName() 
			+ ","  + threadScopeData.getAge());
		}
	}
	
	static class B {
		
		public void get() {
			int data = dataMap.get();
			System.out.println("B from " + Thread.currentThread().getName() + " get data:" + data);
			
			ThreadScopeData threadScopeData = ThreadScopeData.getThreadInstance();
			System.out.println("B from " + Thread.currentThread().getName() + " get threadData:" + threadScopeData.getName() 
			+ ","  + threadScopeData.getAge());
		}
	}
}

/**
 * 
 * 多结合生活实际理解设计思想
 * 先理解设计思想，再去看源码会轻松很多
 *
 */
class ThreadScopeData {
	//私有化
	private ThreadScopeData() {}
	
	//存放当前线程信息
	private static ThreadLocal<ThreadScopeData> map = new ThreadLocal<>();
	
	//懒汉式单例
	public static ThreadScopeData getThreadInstance() {
		ThreadScopeData instance = map.get();
		if(instance == null) {
			instance = new ThreadScopeData();
			map.set(instance);
		}
		return instance;
	}
	
	private String name;
	private int age;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}


