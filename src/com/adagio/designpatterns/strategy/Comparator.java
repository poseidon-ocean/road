package com.adagio.designpatterns.strategy;

/**
 * 策略模式
 * 最终执行结果是固定的
 * 执行过程和执行逻辑不一样
 * 
 * 如支付
 *
 */
public interface Comparator {
	
	int compareTo(Object obj1,Object obj2);
	
}
