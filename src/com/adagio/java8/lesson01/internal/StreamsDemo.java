package com.adagio.java8.lesson01.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * java.util.Stream��ʾ��ĳһ��Ԫ�ص����У�����ЩԪ���Ͽ��Խ��и��ֲ���
 * Stream�����������м������Ҳ������������
 * �������᷵��һ��ĳ�����͵�ֵ�����м�����᷵�������������������ͨ����ε���ͬһ�����������������������������
 * 
 */
public class StreamsDemo {

	public static void main(String[] args) {
		//������������˳��ģ�Ҳ�����ǲ��е�
		//˳�����ͨ�����߳�ִ�У������в�����ͨ�����߳�ִ��
		
		/*********	˳��		***********/
		//������
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
		/*********	˳��		***********/
		
		
		/*******	����	Parallel Streams	*********/
		//����һ�¶�������Ͻ���������ʹ�õ�ʱ��
		int max = 1000000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
		    UUID uuid = UUID.randomUUID();
		    values.add(uuid.toString());
		}
		
		sequence(values);
		parallel(values);
		
		/*******	����		*********/
	}

	
	/**
	 * ��������
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
	 * ˳������
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
	 * �ò�����һ���ս���������ܹ�ͨ��ĳһ����������Ԫ�ؽ�����������
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
	 * Count��һ���ս���������������Ƿ���һ����ֵ��������ʶ��ǰ�������а�����Ԫ������
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
	 * ƥ������ж��ֲ�ͬ�����ͣ����������ж�ĳһ�ֹ����Ƿ����������໥�Ǻϵ�
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
	 * map��һ��������������м������ͨ�������ķ��������ܹ����������е�ÿһ��Ԫ�ض�Ӧ������һ��������
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
	 * Sorted��һ���м�������ܹ�����һ���Ź�������������ͼ
	 * 
	 * �������е�Ԫ�ػ�Ĭ�ϰ�����Ȼ˳���������
	 * �������Լ�ָ��һ��Comparator�ӿ����ı��������
	 * 
	 * sortedֻ�Ǵ���һ���������������ͼ��������ı�ԭ��������Ԫ�ص�˳��
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
	 * Filter����һ��predicate�ӿ����͵ı��������������������е�Ԫ�ؽ��й���
	 * �ò�����һ���м����
	 * @param stringCollection
	 */
	public static void filter(List<String> stringCollection) {
		stringCollection
			.stream()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);
		
	}
	
}
