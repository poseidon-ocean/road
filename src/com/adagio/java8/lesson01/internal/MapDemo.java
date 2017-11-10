package com.adagio.java8.lesson01.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * map是不支持流操作的
 * 而更新后的map现在则支持多种实用的新方法，来完成常规的任务
 *
 */
public class MapDemo {

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();
		
		for(int i=0; i<10;i++) {
			//putIfAbsent避免我们将null写入
			map.putIfAbsent(i, "value" + i);
		}
		// forEach接受一个消费者对象，从而将操作实施到每一个map中的值上
//		map.forEach((key, val) -> System.out.println(key + "==" + val));
		
		//如何使用函数来计算map的编码
		map.computeIfPresent(3, (key, val) -> val + key);
//		System.out.println(map.get(3));
		
		map.computeIfPresent(9, (key, val) -> null);
//		System.out.println(map.get(9));
		
		map.computeIfAbsent(23, key -> "value" + key);
//		System.out.println(map.get(23));
		
		map.computeIfAbsent(3, key -> "bam");
//		System.out.println(map.get(3));
		
		//当给定一个key值时，如何把一个实例从对应的key中移除
		map.remove(3, "val33");
		System.out.println(map.get(3));
		
		map.remove(3, "value33");
		System.out.println(map.get(3));
		
		System.out.println(map.getOrDefault(42, "not found"));
		
		
		//将map中的实例合并
		map.merge(9, "value9", (val, newVal) -> val.concat(newVal));
		System.out.println(map.get(9));
		
		//合并操作先看map中是否没有特定的key/value存在，如果是，则把key/value存入map，
		//否则merging函数就会被调用，对现有的数值进行修改
		map.merge(9, "newValue9", (val, newVal) -> val.concat(newVal));
		System.out.println(map.get(9));
	}
	
	
}
