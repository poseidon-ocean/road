package com.adagio.java8.lesson05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 互斥锁
 * 实现了重入特性
 *
 */
public class ReentrantLockDemo {

	ReentrantLock lock = new ReentrantLock();
	int count = 0;
	
	void increment() {
	    lock.lock();
	    try {
	        count++;
	    } finally {
	        lock.unlock();
	    }
	}
	
	public static void main(String[] args) throws Exception {
		ReentrantLockDemo r = new ReentrantLockDemo();
		r.testLock();
		
	}
	
	
	public void testLock() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		executor.submit(() -> {
			lock.lock();
			try {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlock();
			}
		});
		
		executor.submit(() -> {
			System.out.println("Locked: " + lock.isLocked());
			System.out.println("Held by me: " + lock.isHeldByCurrentThread());
			
			//tryLock()方法是lock()方法的替代，它尝试拿锁而不阻塞当前线程
			boolean locked = lock.tryLock();
			System.out.println("Lock acquired: " + locked);
		});
		
		stop(executor);
		
	}

	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
}
