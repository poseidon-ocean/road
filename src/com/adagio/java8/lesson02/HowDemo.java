package com.adagio.java8.lesson02;

import java.util.Arrays;
import java.util.List;

/**
 * 数据流如何工作
 * 数据流表示元素的序列，并支持不同种类的操作来执行元素上的计算
 *
 */
public class HowDemo {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("a1", "a2", "b1", "c2", "c1");
		
		list
			.stream()
			.filter(s -> s.startsWith("c"))
			.map(String::toUpperCase)
			.sorted()
			.forEach(System.out::println);
		
	}
}
