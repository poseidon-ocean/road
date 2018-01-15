package com.adagio.thread.traditional;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 传统定时线程
 * 开源定时框架 - quartz
 *
 */
public class TraditionalTimerThread {

	private static boolean isNotEnd = true;
	public static void main(String[] args) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("delay 10s bombing...");
//				isNotEnd = false;
			}
			//延迟10s执行
		}, 10000);
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("period 3s delay 10s bombing...");
//				isNotEnd = false;
			}
			//延迟10s执行，然后每隔3s周期性执行一次
		}, 10000, 3000);
		
		while(isNotEnd) {
			
			System.out.println(new GregorianCalendar().get(Calendar.SECOND));
			
//			System.out.println(new Date().getSeconds());
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
