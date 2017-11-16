package com.adagio.java8.lesson07;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 处理字符串
 *
 */
public class StringDemo {

	public static void main(String[] args) {
		//join使用指定的分隔符，将任何数量的字符串连接为一个字符串
		System.out.println(String.join(":", "foobar", "foo", "bar"));
		
		//方法chars从字符串所有字符创建数据流，所以你可以在这些字符上使用流式操作
		System.out.println("foobar:foo:bar"
	    .chars()
	    .distinct()
	    .mapToObj(c -> String.valueOf((char)c))
	    .sorted()
	    .collect(Collectors.joining()));
		
		//创建数据流来处理正则表达式模式串
		System.out.println(Pattern.compile(":")
	    .splitAsStream("foobar:foo:bar")
	    .filter(s -> s.contains("bar"))
	    .sorted()
	    .collect(Collectors.joining(":")));
		
		//过滤字符串流
		Pattern pattern = Pattern.compile(".*@gmail\\.com");
		System.out.println(Stream.of("bob@gmail.com", "alice@hotmail.com")
		    .filter(pattern.asPredicate())
		    .count());
	}
}
