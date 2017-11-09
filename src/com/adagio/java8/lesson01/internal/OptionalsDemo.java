package com.adagio.java8.lesson01.internal;

import java.util.Optional;

/**
 * Optional不是一个函数式接口，而是一个精巧的工具接口，用来防止NullPointerException产生
 * 
 * Optional是一个简单的值容器，这个值可以是null，也可以是non-null。
 * 考虑到一个方法可能会返回一个non-null的值，也可能返回一个空值。
 * 为了不直接返回null，我们在Java 8中就返回一个Optional.
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
