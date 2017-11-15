package com.adagio.java8.lesson06;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ConcurrentMap
 * Java8通过将新的方法添加到这个接口，引入了函数式编程
 *
 */
public class ConcurrentMapDemo {

	public static void main(String[] args) {
		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
		map.put("foo", "bar");
		map.put("han", "solo");
		map.put("r2", "d2");
		map.put("c3", "p0");
		
		map.forEach((key, value) -> System.out.printf("%s = %s\n", key, value));
		
		//新方法putIfAbsent()只在提供的键不存在时，将新的值添加到映射中
		String value = map.putIfAbsent("c3", "p1");
		System.out.println(value);
		
		//getOrDefault()方法返回指定键的值
		String value1 = map.getOrDefault("hi", "there");
		System.out.println(value1); 
		
		//replaceAll()接受类型为BiFunction的lambda表达式
		map.replaceAll((key, v) -> "r2".equals(key) ? "d3" : v);
		System.out.println(map.get("r2")); 
		
		//compute()允许我们转换单个元素，而不是替换映射中的所有值
		//两个变体：computeIfAbsent() 和 computeIfPresent()
		map.compute("foo", (key, val) -> val + val);
		System.out.println(map.get("foo"));  
		
		//merge()方法可以用于以映射中的现有值来统一新的值
		map.merge("foo", "boo", (oldVal, newVal) -> newVal + " was " + oldVal);
		System.out.println(map.get("foo")); 
	}
}
