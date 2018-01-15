package com.adagio.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
	//共享缓存区
	private BlockingQueue<DataInfo> queue;
	
	//随机对象
	private static Random r = new Random();

	public Consumer(BlockingQueue<DataInfo> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				//获取数据
				DataInfo dataInfo = this.queue.take();
				//进行数据处理，休眠模拟耗时
				Thread.sleep(r.nextInt(1000));
				
				System.out.println("当前消费线程：" + Thread.currentThread().getName() + ",消费了数据：" + dataInfo.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
