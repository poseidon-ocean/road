package com.adagio.java8.lesson01.internal;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Predicate��һ���������͵ĺ������ú���ֻ��һ���������
 * ���ڴ����ӵ��߼����ʣ�and, or��negate��
 *
 */
public class PredicatesDemo {

	public static void main(String[] args) {
		
		Predicate<String> predicate = (s) -> s.length() > 0;
		
		System.out.println(predicate.test("foo"));
		System.out.println(predicate.negate().test("foo"));
		
		Predicate<Boolean> nonNull = Objects :: nonNull;
		Predicate<Boolean> isNull = Objects :: isNull;
		
		System.out.println(nonNull.test(false));
		System.out.println(isNull.test(null));
		
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();
		
		System.out.println(isEmpty.test(""));
		System.out.println(isNotEmpty.test(""));
	}
	
}
