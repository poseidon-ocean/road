package com.adagio.thread.lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 */
public class ReadWriteLockDemo {

	public static void main(String[] args) {
		final Queue q = new Queue();
		for(int i=0;i<3;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						q.get();
					}
					
				}
			}).start();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						q.put(new Random().nextInt(100));
					}
				}
			}).start();
		}
	}
	
	
}

class Queue {
	private Object data = null; 	//共享数据，只能有一个线程能写数据，但可以有多个线程同时读写该数据
	
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public void get() {
		rwl.readLock().lock();
		
		try {
			System.out.println(Thread.currentThread().getName() + " be ready to read data !");
			Thread.sleep((long)(Math.random() * 1000));
			System.out.println(Thread.currentThread().getName() + " have read data: " + data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public void put(Object data) {
		rwl.writeLock().lock();
		
		try {
			System.out.println(Thread.currentThread().getName() + " be ready to write data !");
			Thread.sleep((long)(Math.random() * 1000));
			this.data = data;
			System.out.println(Thread.currentThread().getName() + " have write data: " + data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwl.writeLock().unlock();
		}
		
	}
	
	
}