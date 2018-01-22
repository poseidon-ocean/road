package com.adagio.thread.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier实现集合等待
 *
 */
public class CyclicBarrierDemo {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		final CyclicBarrier cb = new CyclicBarrier(3);
		
		for(int i=0;i<3;i++) {
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep((long)Math.random() * 10000);
						System.out.println("线程 " + Thread.currentThread().getName() + " 到达集合点1，当前已有 " +
								(cb.getNumberWaiting() + 1)+"个已经到达，" + (cb.getNumberWaiting() == 2?"都到齐了，继续走" : "正在等候") );
						cb.await();
						
						Thread.sleep((long)Math.random() * 10000);
						System.out.println("线程 " + Thread.currentThread().getName() + " 到达集合点2，当前已有 " +
								(cb.getNumberWaiting() + 1)+"个已经到达，" + (cb.getNumberWaiting() == 2?"都到齐了，继续走" : "正在等候") );
						cb.await();
						
						Thread.sleep((long)Math.random() * 10000);
						System.out.println("线程 " + Thread.currentThread().getName() + " 到达集合点3，当前已有 " +
								(cb.getNumberWaiting() + 1)+"个已经到达，" + (cb.getNumberWaiting() == 2?"都到齐了，继续走" : "正在等候") );
						cb.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
		
	}
	
}
