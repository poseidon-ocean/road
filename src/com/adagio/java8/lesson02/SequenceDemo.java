package com.adagio.java8.lesson02;

import java.util.stream.Stream;

/**
 * 处理顺序
 *
 */
public class SequenceDemo {

	public static void main(String[] args) {
		
//		test1();
		
//		test2();
		
//		test3();
		
		test4();
		
		
		
		
		
	}

	//通过添加额外的方法sorted来扩展test3的例子
	public static void test4() {
		//排序是一类特殊的衔接操作。它是有状态的操作，因为你需要在处理中保存状态来对集合中的元素排序
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .sorted((s1, s2) -> {
	        System.out.printf("sort: %s; %s\n", s1, s2);
	        return s1.compareTo(s2);
	    })
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("a");
	    })
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
		
		System.out.println("==================");
//		通过重排调用链来优化性能
		//sorted永远不会调用，因为filter把输入集合减少至只有一个元素。所以对于更大的输入集合会极大提升性能
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("a");
	    })
	    .sorted((s1, s2) -> {
	        System.out.printf("sort: %s; %s\n", s1, s2);
	        return s1.compareTo(s2);
	    })
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
	}

	/**
	 * 为什么顺序如此重要
	 */
	public static void test3() {
		//例子由两个衔接操作map和filter，以及一个终止操作forEach组成
		
		//map和filter会对底层集合的每个字符串调用五次，而forEach只会调用一次
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("A");
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
		
		System.out.println("==================");
		
		
		//调整操作顺序，将filter移动到调用链的顶端，就可以极大减少操作的执行次数
		//map只会调用一次，所以操作流水线对于更多的输入元素会执行更快。在整合复杂的方法链时，要记住这一点
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("a");
	    })
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
	}

	public static void test2() {
		//通过添加终止操作forEach
		//第一个字符串"d2"首先经过filter然后是forEach，执行完后才开始处理第二个字符串"a2"
		Stream.of("d2", "a2", "b1", "b3", "c")
		.filter(s -> {
			System.out.println("filter: " + s);
			return true;
		})
		.forEach(s -> System.out.println("forEach: " + s));
		
		//这种行为可以减少每个元素上所执行的实际操作数量
		Stream.of("d2", "a2", "b1", "b3", "c")
		.map(s -> {
			System.out.println("map: " + s);
			return s.toUpperCase();
		})
		.anyMatch(s -> {
			System.out.println("anyMatch: " + s);
			return s.startsWith("A");
		});
	}

	public static void test1() {
		//衔接操作的一个重要特性就是延迟性
		Stream.of("d2", "a2", "b1", "b3", "c")
		.filter(s -> {
			//执行这段代码时，不向控制台打印任何东西。这是因为衔接操作只在终止操作调用时被执行
			System.out.println("filter: " + s);
			return true;
		});
	}
	
}
