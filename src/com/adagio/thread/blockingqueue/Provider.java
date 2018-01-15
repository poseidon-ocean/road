package com.adagio.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Provider implements Runnable {
	
	//共享缓存区
	private BlockingQueue<DataInfo> queue;

	//线程是否运行标志
	private volatile boolean isRunning = true;
	
	//id生成器
	private static AtomicInteger count = new AtomicInteger();
	
	//随机对象
	private static Random r = new Random();
	
	
	public Provider(BlockingQueue<DataInfo> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(isRunning) {
			try {
				//随机休眠，表示获取数据生产的时间
				Thread.sleep(r.nextInt(1000));
				
				//获取数据进行累计
				int id = count.incrementAndGet();
				
				//生产数据
				DataInfo dataInfo = new DataInfo(Integer.toString(id), "数据："+ id);
				
				System.out.println("当前线程：" + Thread.currentThread().getName() + ",生产了数据，id为：" + id + "，并存放到缓存区");
				
				if(!this.queue.offer(dataInfo, 2, TimeUnit.SECONDS)) {
					System.out.println("提交缓存数据失败");
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	//停止生产
	public void stop() {
		this.isRunning = false;
	}

}
