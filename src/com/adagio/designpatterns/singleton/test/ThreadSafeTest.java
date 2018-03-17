package com.adagio.designpatterns.singleton.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.adagio.designpatterns.singleton.Hungry;

public class ThreadSafeTest {

	public static void main(String[] args) {
		int count = 100;
		
		CountDownLatch latch = new CountDownLatch(count);
		
		final Set<Hungry> syncSet = Collections.synchronizedSet(new HashSet<>());
		
		for(int i=0;i<count; i++) {
			new Thread() {
				@Override
				public void run() {
					syncSet.add(Hungry.getInstance());
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				};
			}.start();
			
			latch.countDown();
		}
		
	}
}
