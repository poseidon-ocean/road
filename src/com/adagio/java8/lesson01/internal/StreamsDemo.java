package com.adagio.java8.lesson01.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * java.util.Stream表示了某一种元素的序列，在这些元素上可以进行各种操作
 * Stream操作可以是中间操作，也可以是完结操作
 * 完结操作会返回一个某种类型的值，而中间操作会返回流对象本身，并且你可以通过多次调用同一个流操作方法来将操作结果串起来
 * 
 */
public class StreamsDemo {

	public static void main(String[] args) {
		//流操作可以是顺序的，也可以是并行的
		//顺序操作通过单线程执行，而并行操作则通过多线程执行
		
		/*********	顺序		***********/
		//序列流
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		
		
		
		
//		filter(stringCollection);
		
//		sorted(stringCollection);
		
//		map(stringCollection);
		
//		match(stringCollection);
		
//		count(stringCollection);
		
//		reduce(stringCollection);
		/*********	顺序		***********/
		
		
		/*******	并行	Parallel Streams	*********/
		//测量一下对这个集合进行排序所使用的时间
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
		    UUID uuid = UUID.randomUUID();
		    values.add(uuid.toString());
		}
		
		
		//注意：说parallelStream比stream这个快，但是测试发现好像更慢
		sequence(values);		//sequential sort took: 3 ms 
		parallel(values);		//parallel sort took: 772 ms 
		
		/*******	并行		*********/
	}

	
	/**
	 * 并行排序
	 * @param values
	 */
	public static void parallel(List<String> values) {
		long t1 = System.nanoTime();
		
		long count = values.parallelStream().sorted().count();
		System.out.println(count);
		
		long t2 = System.nanoTime();
		
		long millis = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
		System.out.printf("parallel sort took: %d ms \n", millis);
	}


	/**
	 * 顺序排序
	 * @param values
	 */
	public static void sequence(List<String> values) {
		long t1 = System.nanoTime();
		
		long count = values.stream().sorted().count();
		System.out.println(count);
		
		long t2 = System.nanoTime();
		
		long millis = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
		System.out.printf("sequential sort took: %d ms \n", millis);
	}



	/**
	 * 该操作是一个终结操作，它能够通过某一个方法，对元素进行削减操作
	 * @param stringCollection
	 */
	public static void reduce(List<String> stringCollection) {
		Optional<String> reduce = stringCollection
			.stream()
			.sorted()
			.reduce((s1,s2) -> s1 + "##" + s2);
		
		reduce.ifPresent(System.out::println);
	}

	/**
	 * Count是一个终结操作，它的作用是返回一个数值，用来标识当前流对象中包含的元素数量
	 * @param stringCollection
	 */
	public static void count(List<String> stringCollection) {
		long count = stringCollection
			.stream()
			.filter((s) -> s.startsWith("a"))
			.count();
		System.out.println(count);
	}

	/**
	 * 匹配操作有多种不同的类型，都是用来判断某一种规则是否与流对象相互吻合的
	 * @param stringCollection
	 */
	public static void match(List<String> stringCollection) {
		boolean any = stringCollection
			.stream()
			.anyMatch((s) -> s.startsWith("a"));
		
		boolean all = stringCollection
			.stream()
			.allMatch((s) -> s.startsWith("a"));
		
		boolean none = stringCollection
			.stream()
			.noneMatch((s) -> s.startsWith("a"));
		
		System.out.println(any + "==" + all + "==" + none);
	}

	/**
	 * map是一个对于流对象的中间操作，通过给定的方法，它能够把流对象中的每一个元素对应到另外一个对象上
	 * @param stringCollection
	 */
	public static void map(List<String> stringCollection) {
		stringCollection
			.stream()
			.map(String::toUpperCase)
			.sorted((a, b) -> b.compareTo(a))
			.forEach(System.out::println);
		
	}

	/**
	 * Sorted是一个中间操作，能够返回一个排过序的流对象的视图
	 * 
	 * 流对象中的元素会默认按照自然顺序进行排序
	 * 除非你自己指定一个Comparator接口来改变排序规则
	 * 
	 * sorted只是创建一个流对象排序的视图，而不会改变原来集合中元素的顺序
	 * @param stringCollection
	 */
	public static void sorted(List<String> stringCollection) {
		stringCollection
			.stream()
			.sorted()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);
		
	}

	/**
	 * Filter接受一个predicate接口类型的变量，并将所有流对象中的元素进行过滤
	 * 该操作是一个中间操作
	 * @param stringCollection
	 */
	public static void filter(List<String> stringCollection) {
		stringCollection
			.stream()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);
		
	}
	
}
