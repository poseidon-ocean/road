package com.adagio.thread.traditional;

/**
 * 传统线程demo
 *
 */
public class TraditionalThread {

	public static void main(String[] args) {
		
		//创建线程一、创建Thread的子类
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("1:" + Thread.currentThread().getName());
					System.out.println("2:" + this.getName());
				}
			}
		};
		
		thread1.start();
		
		//创建线程二、传入Runnable
		Thread thred2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("1:" + Thread.currentThread().getName());
				}
				
			}
		});
		
		thred2.start();
		
		//创建线程三、传入Runnable，也实现自己的子类  子类的会覆盖父类的run方法
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Runnable:" + Thread.currentThread().getName());
				
			}
		}) {
			@Override
			public void run() {
				
				System.out.println("Thread:" + Thread.currentThread().getName());
			}
		}.start();
	}
}
