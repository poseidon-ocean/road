package com.adagio.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore实现信号灯
 *
 */
public class SemaphoreDemo {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		final Semaphore sp = new Semaphore(3);
		
		for(int i=0;i<20;i++) {
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						sp.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("线程 " + Thread.currentThread().getName() + " 进入，当前已有 " +
					(3- sp.availablePermits()) +"个信号灯使用中");
					
					try {
						Thread.sleep((long)Math.random() * 10000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println("线程 " + Thread.currentThread().getName() + " 即将离开");
					sp.release();
				}
			};
			
			service.execute(runnable);
		}
		service.shutdown();
		
	}
	
}
