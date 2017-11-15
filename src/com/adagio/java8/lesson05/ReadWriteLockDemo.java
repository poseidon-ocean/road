package com.adagio.java8.lesson05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的理念是，只要没有任何线程写入变量，并发读取可变变量通常是安全的
 *
 */
public class ReadWriteLockDemo {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		ReadWriteLock lock = new ReentrantReadWriteLock();

		executor.submit(() -> {
		    lock.writeLock().lock();
		    try {
		    	TimeUnit.SECONDS.sleep(1);
		        map.put("foo", "bar");
		    } catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
		        lock.writeLock().unlock();
		    }
		});
		
		Runnable readTask = () -> {
		    lock.readLock().lock();
		    try {
		        System.out.println(map.get("foo"));
		        TimeUnit.SECONDS.sleep(1);
		    } catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
		        lock.readLock().unlock();
		    }
		};

		executor.submit(readTask);
		executor.submit(readTask);

		stop(executor);
	}
	
	
	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
}
