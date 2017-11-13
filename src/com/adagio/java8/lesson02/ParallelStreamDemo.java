package com.adagio.java8.lesson02;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * 并行流
 * 
 * 
 *
 */
public class ParallelStreamDemo {

	public static void main(String[] args) {
		//ForkJoinPool底层线程池的大小最大为五个线程 -- 取决于CPU的物理核数
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		System.out.println(commonPool.getParallelism()); 
		
		//通过设置下列JVM参数来增减公共池的大小
		//-Djava.util.concurrent.ForkJoinPool.common.parallelism=5
		
		
		//打印了当前线程的信息
		Arrays.asList("a1", "a2", "b1", "c2", "c1")
	    .parallelStream()
	    .filter(s -> {
	        System.out.format("filter: %s [%s]\n",
	            s, Thread.currentThread().getName());
	        return true;
	    })
	    .map(s -> {
	        System.out.format("map: %s [%s]\n",
	            s, Thread.currentThread().getName());
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.format("forEach: %s [%s]\n",
	        s, Thread.currentThread().getName()));
		
		System.out.println("================================");
		Arrays.asList("a1", "a2", "b1", "c2", "c1")
	    .parallelStream()
	    .filter(s -> {
	        System.out.format("filter: %s [%s]\n",
	            s, Thread.currentThread().getName());
	        return true;
	    })
	    .map(s -> {
	        System.out.format("map: %s [%s]\n",
	            s, Thread.currentThread().getName());
	        return s.toUpperCase();
	    })
	    .sorted((s1, s2) -> {
	        System.out.format("sort: %s <> %s [%s]\n",
	            s1, s2, Thread.currentThread().getName());
	        return s1.compareTo(s2);
	    })
	    .forEach(s -> System.out.format("forEach: %s [%s]\n",
	        s, Thread.currentThread().getName()));
		
		//sort看起来只在主线程上串行执行
		//并行流上的sort在背后使用了Java8中新的方法Arrays.parallelSort()
		//如果指定数据的长度小于最小粒度，它使用相应的Arrays.sort方法来排序
		
		
		
		//组合器函数只在并行流中调用，而不在串行流中调用
		List<Person> persons = Arrays.asList(
			    new Person("Max", 18),
			    new Person("Peter", 23),
			    new Person("Pamela", 23),
			    new Person("David", 12));

			persons
			    .parallelStream()
			    .reduce(0,
			        (sum, p) -> {
			            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
			                sum, p, Thread.currentThread().getName());
			            return sum += p.age;
			        },
			        (sum1, sum2) -> {
			            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
			                sum1, sum2, Thread.currentThread().getName());
			            return sum1 + sum2;
			        });
	}
}
