package com.adagio.java8.lesson01;

public interface Formula {

	double calculate(int a);
	
	//允许在接口中有默认方法实现
	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}
