package com.adagio.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁机制
 * 
 * 替代之前的synchronized
 * 更体现面向对象
 *
 */
public class LockDemo {
	
	public static void main(String[] args) {
		new LockDemo().init();
	}
	
	//两个线程完整打印，互相不能有影响
	private void init() {
		final Outputer outputer = new Outputer();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
					outputer.output("三傻大闹宝莱坞");
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
					outputer.output2("阿米尔·汗");
				}
			}
		}).start();
		
	}
	
	static class Outputer {
		Lock lock = new ReentrantLock();
		//实现原子性 
		public void output(String name) {
			int len = name.length();
			
			lock.lock();
			
			try {
				for(int i=0;i<len;i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			} finally {
				lock.unlock();
			}
		}
		
		//实现原子性 
		public void output2(String name) {
			int len = name.length();
			lock.lock();
			try {
				for(int i=0;i<len;i++) {
					System.out.print(name.charAt(i));
				}
			} finally {
				lock.unlock();
			}
		}
		
	}
}
