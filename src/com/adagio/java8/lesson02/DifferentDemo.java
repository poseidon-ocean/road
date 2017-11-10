package com.adagio.java8.lesson02;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 数据流的不同类型
 * 
 *
 */
public class DifferentDemo {

	public static void main(String[] args) {
		Arrays
		.asList("a1", "a2", "a3")
		.stream()
		.findFirst()
		.ifPresent(System.out::println);
		
		Arrays
		.asList("a1", "a2", "a3")
		.parallelStream()
		.findFirst()
		.ifPresent(System.out::println);
		
		//使用Stream.of()，就可以从一系列对象引用中创建数据流
		Stream
		.of("a1", "a2", "a3")
		.findFirst()
		.ifPresent(System.out::println);
		
		//特殊种类的流
		//IntStream、LongStream 、DoubleStream
		IntStream.range(1, 4)
			.forEach(System.out::println);
		
		//基本数据流支持额外的聚合终止操作sum()和average()
		Arrays.stream(new int[] {1, 2, 3})
			.map(n -> 2 * n + 1)
			.average()
			.ifPresent(System.out::println);
		
		//对象数据流支持特殊的映射操作mapToInt()、mapToLong() 和 mapToDouble()
		Stream.of("a1", "a2", "a3")
			.map(s -> s.substring(1))
			.mapToInt(Integer::parseInt)
			.max()
			.ifPresent(System.out::println);
		
		//浮点数据流首先映射为整数数据流，之后映射为字符串的对象数据流
		Stream.of(1.0, 2.0, 3.0)
			.mapToInt(Double::intValue)
			.mapToObj(i -> "a" + i)
			.forEach(System.out::println);
			
		
		
	}
}
