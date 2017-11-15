package com.adagio.java8.lesson04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 超时
 *
 */
public class TimeoutDemo {

	public static void main(String[] args) throws Exception {
		  ExecutorService executor = Executors.newFixedThreadPool(1);

		    Future<Integer> future = executor.submit(() -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        return 123;
		    }
		    catch (InterruptedException e) {
		        throw new IllegalStateException("task interrupted", e);
		    }
		});

		    //传入一个时长来避免程序长时间没有响应
		    future.get(1, TimeUnit.SECONDS);
	}
}
