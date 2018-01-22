package com.adagio.thread.lock;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 交换数据
 * 
 * 商品交换
 *
 */
public class ExchangerDemo {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		final Exchanger<String> exchanger = new Exchanger<>();
		
		service.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String goods = "羊";
					System.out.println("线程 " + Thread.currentThread().getName() + " 正准备交换物 " + goods + " 卖钱");
					
					Thread.sleep((long)Math.random() * 10000);
					
					String money = exchanger.exchange(goods);
					System.out.println("线程 " + Thread.currentThread().getName() + " 换回来的钱  " + money);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
		});
		
		service.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String money = "500大洋";
					System.out.println("线程 " + Thread.currentThread().getName() + " 正准备买东西的钱  " + money);
					
					Thread.sleep((long)Math.random() * 10000);
					
					String goods = exchanger.exchange(money);
					System.out.println("线程 " + Thread.currentThread().getName() + " 买回来的东西  " + goods);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
		});
		
		
	}
}
