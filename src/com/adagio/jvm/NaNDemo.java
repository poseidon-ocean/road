package com.adagio.jvm;

public class NaNDemo {

	public static void main(String[] args) {
		double num = 1.0;
		
		System.out.println("num / 0.0=" + (num / 0.0));
		System.out.println("num / -0.0=" + (num / -0.0));
		System.out.println(num == Float.NaN);
		System.out.println(Double.NaN == Float.NaN);
		System.out.println(num != Float.NaN);
		System.out.println(Double.NaN != Float.NaN);
		
		System.out.println("num / 0=" + (num / 0));
	}
}
