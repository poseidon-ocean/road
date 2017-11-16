package com.adagio.java8.lesson07;

/**
 * 处理数值
 *
 */
public class NumberDemo {

	public static void main(String[] args) {
		//int可表示最多2 ** 32个数
		//最大的有符号正整数为2 ** 31 - 1
		System.out.println(Integer.MAX_VALUE);      
		System.out.println(Integer.MAX_VALUE + 1);
		
		//Java8添加了解析无符号整数的支持
		
		//可以将最大的无符号数2 ** 32 - 1解析为整数
		long maxUnsignedInt = (1l << 32) - 1;
		String string = String.valueOf(maxUnsignedInt);
		int unsignedInt = Integer.parseUnsignedInt(string, 10);
		String string2 = Integer.toUnsignedString(unsignedInt, 10);
		System.out.println(string2);
		
		//之前不可能使用parseInt完成,因为它超出了最大范围2 ** 31 - 1
		try {
		    Integer.parseInt(string, 10);
		}
		catch (NumberFormatException e) {
		    System.err.println("could not parse signed int of " + maxUnsignedInt);
		}
	}
}
