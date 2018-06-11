package com.adagio.jvm;

/**
 *	局部变量表
 *
 * 参数的调用
 * 
 *
 */
public class LocalVariable {

	public void param1(String code) {
		int a = 1;
	}
	
	public void param2() {
		int b = 2;
	}
	
	public static void param3(String name) {
		int c = 3;
	}
	
	public static void param4() {
		int d = 4;
	}
	
	public static void param5() {
		
	}
	
	public void param6() {
		
	}
	
	public static void main(String[] args) {
		
		LocalVariable l = new LocalVariable();
	}
}
