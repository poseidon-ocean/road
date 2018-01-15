package com.adagio.thread.traditional;

/**
 * 线程间的通信
 * 
 * 子线程循环10次，接着主线程循环100，接着又到子线程循环10次
 * 接着再回到主线程又循环100，如此循环50次，请写出程序
 * 
 * wait与notify实现线程间的通信：
 * 要用到共同数据（包括同步锁）或共同算法的若干方法应该归于同一个类身上
 * 这种设计正好体现了高类聚合程序的健壮性
 * 
 * 控制台打印数据太多，配置输出到文件
 * Run - Run COnfigurations - common - Output file
 * 输入目标文件：D:\1.txt
 *
 */
public class TraditionalThreadCommunication {

	public static void main(String[] args) {
		new TraditionalThreadCommunication().init();
	}
	
	private void init() {
		final Business business = new Business();
		new Thread(
		
				new Runnable() {
					
					@Override
					public void run() {
						for(int i=0;i<50;i++) {
							business.sub(i);
						}
						
					}
				}
		).start();
		
		for(int i=0;i<50;i++) {
			business.main(i);
		}

	}
}

class Business{
	private boolean beShouldSub = true;
	
	public synchronized void sub(int i) {
		//为什么使用while
		//As in the one argument version, interrupts and spurious wakeups are
		//possible, and this method should always be used in a loop
		//有被假唤醒的可能，源码建议使用while，保证安全性
		while(!beShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int j=0;j<=10;j++) {
			System.out.println("sub Thread sequence of " + j +" loop of " + i);
		}
		
		beShouldSub= false;
		this.notify();
	}
	
	public synchronized void main(int i) {
		while(beShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int j=0;j<=100;j++) {
			System.out.println("main Thread sequence of " + j +" loop of " + i);
		}
		
		beShouldSub = true;
		this.notify();
	}
}
