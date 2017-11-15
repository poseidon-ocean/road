package com.adagio.java8.lesson04;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InvokeDemo {

	public static void main(String[] args) throws Exception{
//		testInvokeAll();
		
		testInvokeAny();
	}

	public static void testInvokeAny() throws Exception {
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
		callable("task1", 2),
		callable("task2", 1),
		callable("task3", 3));

		String result = executor.invokeAny(callables);
		System.out.println(result);

		
	}
	
	public static Callable<String> callable(String result, long sleepSeconds) {
	    return () -> {
	        TimeUnit.SECONDS.sleep(sleepSeconds);
	        return result;
	    };
	}

	/**
	 * 通过invokeAll()一次批量提交多个callable
	 * @throws Exception
	 */
	public static void testInvokeAll() throws Exception{
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
		        () -> "task1",
		        () -> "task2",
		        () -> "task3");

		executor.invokeAll(callables)
		    .stream()
		    .map(future -> {
		        try {
		            return future.get();
		        }
		        catch (Exception e) {
		            throw new IllegalStateException(e);
		        }
		    })
		    .forEach(System.out::println);
	}
}
