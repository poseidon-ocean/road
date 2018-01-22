package com.adagio.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

	public static void main(String[] args) {
		//创建固定大小的线程池
//		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		//创建缓存线程池
//		ExecutorService threadPool = Executors.newCachedThreadPool();
		//创建单一线程池（如何实现线程死掉后重新启动？）
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		for(int i=0;i<10;i++) {
			final int task = i;
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					for(int j=0;j<20;j++) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						System.out.println(Thread.currentThread().getName() + " is loop of " + j + " for task " + task);
					}
					
				}
			});
		}
		
		System.out.println("all of 10 taks have committed !");
//		threadPool.shutdown();
		
		
		Executors.newScheduledThreadPool(3);
	}
	
}
