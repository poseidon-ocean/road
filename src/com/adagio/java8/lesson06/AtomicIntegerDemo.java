package com.adagio.java8.lesson06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 原子操作严重依赖于比较与交换（CAS），它是由多数现代CPU直接支持的原子指令
 *
 */
public class AtomicIntegerDemo {

	public static void main(String[] args) {
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 1000)
		    .forEach(i -> executor.submit(atomicInt::incrementAndGet));

		stop(executor);

		System.out.println(atomicInt.get());   
		
		
		
		AtomicInteger atomicInt2 = new AtomicInteger(0);

		ExecutorService executor2 = Executors.newFixedThreadPool(2);

		IntStream.range(0, 1000)
		    .forEach(i -> {
		        Runnable task = () ->
		            atomicInt2.updateAndGet(n -> n + 2);
		        executor2.submit(task);
		    });

		stop(executor2);

		System.out.println(atomicInt2.get()); 
		
		
		AtomicInteger atomicInt3 = new AtomicInteger(0);

		ExecutorService executor3 = Executors.newFixedThreadPool(2);

		IntStream.range(0, 1000)
		    .forEach(i -> {
		        Runnable task = () ->
		            atomicInt3.accumulateAndGet(i, (n, m) -> n + m);
		        executor3.submit(task);
		    });

		stop(executor3);

		System.out.println(atomicInt3.get()); 
	}
	
	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
}
