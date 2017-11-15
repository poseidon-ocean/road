package com.adagio.java8.lesson04;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService支持任务调度，持续执行或者延迟一段时间后执行
 *
 */
public class ScheduledExecutorDemo {

	public static void main(String[] args) throws Exception {
		
//		testSchedule();
		
//		testScheduleAtFixedRate();
		
		testScheduleWithFixedDelay();
		
		
	}

	/**
	 * 接收一个初始化延迟，用来指定这个任务首次被执行等待的时长
	 */
	public static void testScheduleWithFixedDelay() {
		ScheduledExecutorService executor = 		Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        System.out.println("Scheduling: " + System.nanoTime());
		    }
		    catch (InterruptedException e) {
		        System.err.println("task interrupted");
		    }
		};

		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}

	/**
	 * 用来以固定频率来执行一个任务
	 */
	public static void testScheduleAtFixedRate() {
		ScheduledExecutorService executor = 	Executors.newScheduledThreadPool(1);

		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

		int initialDelay = 0;
		int period = 1;
		executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
	}

	public static void testSchedule()  throws Exception {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
		ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

		TimeUnit.MILLISECONDS.sleep(1337);

		long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
		System.out.printf("Remaining Delay: %sms", remainingDelay);
		
	}
	
}
