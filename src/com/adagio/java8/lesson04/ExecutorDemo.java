package com.adagio.java8.lesson04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 */
public class ExecutorDemo {

	public static void main(String[] args) {
		
//		testSingleThread();
		
		testShutdwon();
	}

	/**
	 * 关闭线程
	 * shutdwon()会等待正在执行的任务执行完
	 * shutdownNow()会终止所有正在执行的任务并立即关闭execuotr
	 */
	public static void testShutdwon() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
		String threadName = Thread.currentThread().getName();
		System.out.println("Hello " + threadName);
		});
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		    }
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
		
	}

	public static void testSingleThread() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
		String threadName = Thread.currentThread().getName();
		System.out.println("Hello " + threadName);
		});
	}
}
