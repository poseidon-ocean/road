package com.adagio.java8.lesson01.internal;

import java.util.Optional;

/**
 * Optional����һ������ʽ�ӿڣ�����һ�����ɵĹ��߽ӿڣ�������ֹNullPointerException����
 * 
 * Optional��һ���򵥵�ֵ���������ֵ������null��Ҳ������non-null��
 * ���ǵ�һ���������ܻ᷵��һ��non-null��ֵ��Ҳ���ܷ���һ����ֵ��
 * Ϊ�˲�ֱ�ӷ���null��������Java 8�оͷ���һ��Optional.
 *
 */
public class OptionalsDemo {

	public static void main(String[] args) {
		Optional<String> optional = Optional.of("ifPresent");
		
		System.out.println(optional.isPresent());
		System.out.println(optional.get());
		System.out.println(optional.orElse("fallback"));
		
		optional.ifPresent((s) -> System.out.println(s.charAt(0)));
	}
}
