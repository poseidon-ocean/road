package com.adagio.java8.lesson01;

public interface Formula {

	double calculate(int a);
	
	//�����ڽӿ�����Ĭ�Ϸ���ʵ��
	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}
