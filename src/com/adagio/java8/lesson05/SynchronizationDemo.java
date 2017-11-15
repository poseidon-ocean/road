package com.adagio.java8.lesson05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 同步
 *
 */
public class SynchronizationDemo {

	int count = 0;

	/**
	 * count 预期结果：10000
	 * 因是我们在不同的线程上共享可变变量，并且变量访问没有同步机制，这会产生竞争条件
	 */
	 void increment() {
	    count = count + 1;
	}
	 
	 void incrementSync() {
		 // synchronized在java9上测试，不能达到同步的效果
		 synchronized (this) {
			 count = count + 1;
		 }
	 }
	
	public static void main(String[] args) {
		SynchronizationDemo s = new SynchronizationDemo();
//		s.testIncrement();
		
		s.testIncrementSync();
	}

	
	public void testIncrementSync() {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10000)
		    .forEach(i -> executor.submit(this::incrementSync));

		stop(executor);

		System.out.println(count); 
		
	}

	public void testIncrement() {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10000)
		    .forEach(i -> executor.submit(this::increment));

		stop(executor);

		System.out.println(count);  
	}

	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
	
	
	
}
