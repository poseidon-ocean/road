package com.adagio.java8.lesson05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 支持计数的信号量
 * 
 * 不过锁通常用于变量或资源的互斥访问，信号量可以维护整体的准入许可
 *
 */
public class SemaphoreDemo {

	public static void main(String[] args) {
		
		//执行器可能同时运行10个任务，但是我们使用了大小为5的信号量，所以将并发访问限制为5
		ExecutorService executor = Executors.newFixedThreadPool(10);

		Semaphore semaphore = new Semaphore(5);

		Runnable longRunningTask = () -> {
		    boolean permit = false;
		    try {
		        permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
		        if (permit) {
		            System.out.println("Semaphore acquired");
		            sleep(5);
		        } else {
		            System.out.println("Could not acquire semaphore");
		        }
		    } catch (InterruptedException e) {
		        throw new IllegalStateException(e);
		    } finally {
		        if (permit) {
		            semaphore.release();
		        }
		    }
		};

		IntStream.range(0, 10)
		    .forEach(i -> executor.submit(longRunningTask));

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
