package com.adagio.java8.lesson07;

/**
 * 算术运算
 * Math工具类新增了一些方法来处理数值溢出
 *
 */
public class MathDemo {

	public static void main(String[] args) {
		
		//这些方法通过抛出ArithmeticException异常来合理地处理溢出
		try {
		    Math.addExact(Integer.MAX_VALUE, 1);
		}
		catch (ArithmeticException e) {
		    System.err.println(e.getMessage());
		}
		
		try {
		    Math.toIntExact(Long.MAX_VALUE);
		}
		catch (ArithmeticException e) {
		    System.err.println(e.getMessage());
		}
	}
}
