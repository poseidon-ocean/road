package com.adagio.java8.lesson04;

import java.util.concurrent.TimeUnit;

/**
 * java8 Runnable演示
 *
 */
public class RunnableDemo {

	public static void main(String[] args) {
		//Runnable是一个函数接口
		Runnable task = () -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello " + threadName);
		};
		
		task.run();
	
		Thread thread = new Thread(task);
		thread.start();
		
		System.out.println("Done !");
		
		
		
		Runnable runnable = () -> {
		    try {
		        String name = Thread.currentThread().getName();
		        System.out.println("Foo " + name);
		        TimeUnit.SECONDS.sleep(1);
		        System.out.println("Bar " + name);
		    }
		    catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		};

		Thread t = new Thread(runnable);
		t.start();
	}
}
