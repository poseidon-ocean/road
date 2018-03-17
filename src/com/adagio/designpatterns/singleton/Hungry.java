package com.adagio.designpatterns.singleton;

/**
 * 饿汉式单例模式
 *
 */
public class Hungry {
	
	private Hungry() {}
	
	private static final Hungry hungry = new Hungry();
	
	public static Hungry getInstance() {
		return hungry;
	}

}
