package com.adagio.java8.lesson01.internal;

import java.util.function.Function;

/**
 * Function�ӿڽ���һ�������������ص�һ�Ľ����Ĭ�Ϸ������Խ������������һ��compse, andThen��
 *
 */
public class FunctionsDemo {

	public static void main(String[] args) {
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		System.out.println(backToString.apply("123"));     // "123"
	}
}
