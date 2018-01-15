package com.adagio.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 线程范围内的共享数据
 * 
 * 这种方式有问题，很容易出现异常
 *
 */
public class ThreadScopeShareData {

	private final static Map<Thread, Integer> dataMap = new HashMap<>();
	
	public static void main(String[] args) {
		for(int i=0; i<2;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int data = new Random().nextInt(100);
					System.out.println(Thread.currentThread().getName() + " has put data :" + data);
					dataMap.put(Thread.currentThread(), data);
					
					
					new A().get();
					new B().get();
					
				}
			}).start();
		}
	}
	
	static class A {
		
		public void get() {
			int data = dataMap.get(Thread.currentThread());
			System.out.println("A from " + Thread.currentThread().getName() + " get data:" + data);
		}
	}
	
	static class B {
		
		public void get() {
			int data = dataMap.get(Thread.currentThread());
			System.out.println("B from " + Thread.currentThread().getName() + " get data:" + data);
		}
	}
	
	
}


