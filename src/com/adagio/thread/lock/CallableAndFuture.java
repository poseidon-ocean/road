package com.adagio.thread.lock;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		Future<String> future = threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "hello";
			}
		});
		
		System.out.println("waiting result");
		
		try {
			System.out.println("get result " + future.get());
			//future.get(1, TimeUnit.SECONDS)
		} catch (Exception e) {
			e.printStackTrace();
		}
//		threadPool.shutdown();

		
		ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
		
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool2);
		
		for(int i=0; i<10; i++) {
			final int seq = i;
			completionService.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
			});
		}
		
		
		try {
			for(int i=0;i<10;i++) {
				System.out.println("take result " + completionService.take().get());
			}
		} catch (Exception e) {
			
		}
		
	}
}
