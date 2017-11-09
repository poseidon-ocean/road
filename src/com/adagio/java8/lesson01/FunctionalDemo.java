package com.adagio.java8.lesson01;

/**
 * 函数式接口
 *
 */
public class FunctionalDemo {
	
	public static void main(String[] args) {
//		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
//		Integer converted = converter.convert("123");
//		System.out.println(converted);    // 123
		
		//方法引用
//		Converter<String, Integer> converter = Integer::valueOf;
//		Integer converted = converter.convert("123");
//		System.out.println(converted);   // 123
		
		Something something = new Something();
		Converter<String, String> converter = something::startsWith;
		String converted = converter.convert("Java");
		System.out.println(converted);    // "J"
	}
}

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}
