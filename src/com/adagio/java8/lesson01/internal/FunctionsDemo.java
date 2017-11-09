package com.adagio.java8.lesson01.internal;

import java.util.function.Function;

/**
 * Function接口接收一个参数，并返回单一的结果。默认方法可以将多个函数串在一起（compse, andThen）
 *
 */
public class FunctionsDemo {

	public static void main(String[] args) {
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		System.out.println(backToString.apply("123"));     // "123"
	}
}
