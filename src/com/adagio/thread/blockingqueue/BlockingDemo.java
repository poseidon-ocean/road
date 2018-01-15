package com.adagio.thread.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingDemo {

	public static void main(String[] args) {
		//内存缓冲区
		BlockingQueue<DataInfo> queue = new LinkedBlockingQueue<>(10);
		
		//生产者
		Provider p1 = new Provider(queue);
		Provider p2 = new Provider(queue);
		Provider p3 = new Provider(queue);
		
		//消费者
		Consumer c1= new Consumer(queue);
		Consumer c2= new Consumer(queue);
		Consumer c3= new Consumer(queue);
		
		
		//创建线程池
		ExecutorService cachePool = Executors.newCachedThreadPool();
		
		cachePool.submit(p1);
		cachePool.submit(p2);
		cachePool.submit(p3);
		
		cachePool.submit(c1);
		cachePool.submit(c2);
		cachePool.submit(c3);
		
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		p1.stop();
		p2.stop();
		p3.stop();
		
//		cachePool.shutdown();
//		cachePool.shutdownNow();
	}
}
