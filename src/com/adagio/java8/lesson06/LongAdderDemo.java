package com.adagio.java8.lesson06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * LongAdder是AtomicLong的替代，用于向某个数值连续添加值
 *
 */
public class LongAdderDemo {

	public static void main(String[] args) {
		
		LongAdder adder = new LongAdder();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 1000)
		    .forEach(i -> executor.submit(adder::increment));

		stop(executor);

		System.out.println(adder.sumThenReset());   
	}
	
	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
}
