package com.adagio.java8.lesson06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * LongAccumulator是LongAdder的更通用的版本
 *
 */
public class LongAccumulatorDemo {

	public static void main(String[] args) {
		LongBinaryOperator op = (x, y) -> 2 * x + y;
		LongAccumulator accumulator = new LongAccumulator(op, 1L);

		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10)
		    .forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

		stop(executor);

		System.out.println(accumulator.getThenReset());  
	}
	
	private static void stop(ExecutorService executor) {
//		executor.shutdownNow();
		executor.shutdown();
	}
}
