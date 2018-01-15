package com.adagio.thread.traditional;

/**
 * 
 * demo:线程的互斥与同步通信
 * 
 * case:线程安全问题可以用银行转账来解释
 * 
 * way : 
 * Synchronized代码块及其原理
 * wait与notify实现线程间的通信
 *
 */
public class TraditionalThreadSynchronized {

	public static void main(String[] args) {
		
		new TraditionalThreadSynchronized().init();
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
					outputer.output2("三傻大闹宝莱坞");
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
		
		//实现原子性 
		public void output(String name) {
			int len = name.length();
			synchronized (this){
				for(int i=0;i<len;i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		//实现原子性 
		public synchronized void output2(String name) {
			int len = name.length();
			for(int i=0;i<len;i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		/**
		 * output3和output4上的是同一把锁  可以互斥
		 */
		//实现原子性 
		public void output3(String name) {
			int len = name.length();
			synchronized (Outputer.class){
				for(int i=0;i<len;i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		//实现原子性 
		public static synchronized void output4(String name) {
			int len = name.length();
			for(int i=0;i<len;i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
	
}
