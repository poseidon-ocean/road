package com.adagio.java8.lesson06;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap
 *
 */
public class ConcurrentHashMapDemo {

	public static void main(String[] args) {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		map.put("foo", "bar");
		map.put("han", "solo");
		map.put("r2", "d2");
		map.put("c3", "p0");
		
		//forEach()方法可以并行迭代映射中的键值对
		map.forEach(1, (key, value) ->
	    System.out.printf("key: %s; value: %s; thread: %s\n",
	        key, value, Thread.currentThread().getName()));

		//search()方法接受BiFunction并为当前的键值对返回一个非空的搜索结果
		//或者在当前迭代不匹配任何搜索条件时返回null
		String result = map.search(1, (key, value) -> {
		    System.out.println(Thread.currentThread().getName());
		    if ("foo".equals(key)) {
		        return value;
		    }
		    return null;
		});
		System.out.println("Result: " + result);
		
		//仅仅搜索映射中的值
		String result1 = map.searchValues(1, value -> {
		    System.out.println(Thread.currentThread().getName());
		    if (value.length() > 3) {
		        return value;
		    }
		    return null;
		});

		System.out.println("Result1: " + result1);
		
		//第一个函数将每个键值对转换为任意类型的单一值。
		//第二个函数将所有这些转换后的值组合为单一结果，并忽略所有可能的null值
		String result2 = map.reduce(1,
			    (key, value) -> {
			        System.out.println("Transform: " + Thread.currentThread().getName());
			        return key + "=" + value;
			    },
			    (s1, s2) -> {
			        System.out.println("Reduce: " + Thread.currentThread().getName());
			        return s1 + ", " + s2;
			    });

			System.out.println("Result2: " + result2);
	}
}
