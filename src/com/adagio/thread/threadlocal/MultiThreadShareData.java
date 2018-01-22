package com.adagio.thread.threadlocal;


/**
 * 多线程共享数据
 * 
 * 设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1
 * 写出程序
 *
 */
public class MultiThreadShareData {

	
	public static void main(String[] args) {
		
		//方式一
		final ShareData shareData = new ShareData();
		
		new Thread(new MyRunnable1(shareData)).start();
		new Thread(new MyRunnable2(shareData)).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				shareData.increment();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				shareData.decrement();
			}
		}).start();
		
		//方式二
		ShareData shareData2 = new ShareData();
		
		new Thread(new MyRunnable1(shareData2)).start();
		new Thread(new MyRunnable2(shareData2)).start();
	}
	
	
}

class MyRunnable1 implements Runnable {

	private ShareData shareData;
	
	public MyRunnable1(ShareData shareData) {
		this.shareData = shareData;
	}
	
	@Override
	public void run() {
		while(true) {
			shareData.increment();
		}
	}
	
}

class MyRunnable2 implements Runnable {

	private ShareData shareData;
	
	public MyRunnable2(ShareData shareData) {
		this.shareData = shareData;
	}
	
	@Override
	public void run() {
		while(true) {
			shareData.decrement();
		}
	}
	
}

class ShareData {
	
	private int j = 0;
	
	public void increment() {
		j ++;
		System.out.println("increment " + j);
	}
	
	public void decrement() {
		j --;
		System.out.println("decrement " + j);
	}
}


