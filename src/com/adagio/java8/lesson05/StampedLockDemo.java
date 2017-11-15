package com.adagio.java8.lesson05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock, 同样支持读写锁
 * 与ReadWriteLock不同的是，StampedLock的锁方法会返回表示为long的标记。
 * 你可以使用这些标记来释放锁，或者检查锁是否有效
 * 
 */
public class StampedLockDemo {

	public static void main(String[] args) {
		
//		testStampedLock();
		
//		testStampedLock2();
		
		testStampedLock3();
		
		
	}
	
	/**
	 * tryConvertToWriteLock
	 * 将读锁转换为写锁而不用再次解锁和加锁
	 */
	private static void testStampedLock3() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
			int count = 0;
		    long stamp = lock.readLock();
		    try {
		        if (count == 0) {
		            stamp = lock.tryConvertToWriteLock(stamp);
		            if (stamp == 0L) {
		                System.out.println("Could not convert to write lock");
		                stamp = lock.writeLock();
		            }
		            count = 23;
		        }
		        System.out.println(count);
		    } finally {
		        lock.unlock(stamp);
		    }
		});

		stop(executor);
		
	}

	/**
	 * 乐观锁
	 */
	public static void testStampedLock2() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
		    long stamp = lock.tryOptimisticRead();
		    try {
		        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
		        sleep(1);
		        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
		        sleep(2);
		        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
		    } finally {
		        lock.unlock(stamp);
		    }
		});

		executor.submit(() -> {
		    long stamp = lock.writeLock();
		    try {
		        System.out.println("Write Lock acquired");
		        sleep(2);
		    } finally {
		        lock.unlock(stamp);
		        System.out.println("Write done");
		    }
		});

		stop(executor);
	}

	/**
	 * 两个读任务都需要等待写锁释放。之后两个读任务同时向控制台打印信息，
	 * 因为多个读操作不会相互阻塞，只要没有线程拿到写锁。
	 */
	public static void testStampedLock() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
		    long stamp = lock.writeLock();
		    try {
		        sleep(1);
		        map.put("foo", "bar");
		    } finally {
		        lock.unlockWrite(stamp);
		    }
		});

		Runnable readTask = () -> {
		    long stamp = lock.readLock();
		    try {
		        System.out.println(map.get("foo"));
		        sleep(1);
		    } finally {
		        lock.unlockRead(stamp);
		    }
		};

		executor.submit(readTask);
		executor.submit(readTask);

		stop(executor);
		
	}

	private static void sleep(int i) {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
}
