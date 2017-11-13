package com.adagio.java8.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 高级操作
 * 
 * 更复杂的操作：collect、flatMap和reduce
 * 
 */
public class HighOperationDemo {

	public static void main(String[] args) {
//		testCollect();
		
//		testFlatMap();
		
		testReduce();
	}

	//归约操作将所有流中的元素组合为单一结果
	public static void testReduce() {
		List<Person> persons =
			    Arrays.asList(
			        new Person("Max", 18),
			        new Person("Peter", 23),
			        new Person("Pamela", 23),
			        new Person("David", 12));
		
		//第一种将流中的元素归约为流中的一个元素
		//计算出年龄最大的人
		persons
	    .stream()
	    .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
	    .ifPresent(System.out::println); 
		
		
		//第二个reduce方法接受一个初始值，和一个BinaryOperator累加器
		Person result =
			    persons
			        .stream()
			        .reduce(new Person("", 0), (p1, p2) -> {
			            p1.age += p2.age;
			            p1.name += p2.name;
			            return p1;
			        });

		System.out.format("name=%s; age=%s \n", result.name, result.age);
		
		//第三个reduce对象接受三个参数：初始值，BiFunction累加器和BinaryOperator类型的组合器函数
		Integer ageSum = persons
			    .stream()
			    .reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);

		System.out.println(ageSum); 
		
		//背后发生了什么
		Integer ageSum1 = persons
			    .stream()
			    .reduce(0,
			        (sum, p) -> {
			            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
			            return sum += p.age;
			        },
			        (sum1, sum2) -> {
			            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
			            return sum1 + sum2;
			        });
		System.out.println(ageSum1);
		
		//好像组合器从来没有调用过
		Integer ageSum2 = persons
			    .parallelStream()
			    .reduce(0,
			        (sum, p) -> {
			            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
			            return sum += p.age;
			        },
			        (sum1, sum2) -> {
			            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
			            return sum1 + sum2;
			        });
		System.out.println(ageSum2);
	}

	/**
	 * flatMap将一个对象转换为多个或零个其他对象
	 * @param persons
	 */
	public static void testFlatMap() {
		List<Foo> foos = new ArrayList<>();

		// create foos
		IntStream
		    .range(1, 4)
		    .forEach(i -> foos.add(new Foo("Foo" + i)));

		// create bars
		foos.forEach(f ->
		    IntStream
		        .range(1, 4)
		        .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));
		
		//flatMap接受返回对象流的函数
		foos.stream()
	    .flatMap(f -> f.bars.stream())
	    .forEach(b -> System.out.println(b.name));
		
		
		//简化为流式操作的单一流水线
		IntStream.range(1, 4)
	    .mapToObj(i -> new Foo("Foo" + i))
	    .peek(f -> IntStream.range(1, 4)
	        .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
	        .forEach(f.bars::add))
	    .flatMap(f -> f.bars.stream())
	    .forEach(b -> System.out.println(b.name));
		
		
		//添加多个null检查来避免潜在的NullPointerException
		Outer outer = new Outer();
		if (outer != null && outer.nested != null && outer.nested.inner != null) {
		    System.out.println(outer.nested.inner.foo);
		}
		
		//使用Optional的flatMap操作来完成相同的行为
		Optional.of(new Outer())
	    .flatMap(o -> Optional.ofNullable(o.nested))
	    .flatMap(n -> Optional.ofNullable(n.inner))
	    .flatMap(i -> Optional.ofNullable(i.foo))
	    .ifPresent(System.out::println);
	}

	/**
	 * collect是终止操作，将流中的元素存放在不同类型的结果中，例如List、Set或者Map
	 * 
	 * collect接受收集器（Collector），它由四个不同的操作组成：供应器（supplier）、
	 * 累加器（accumulator）、组合器（combiner）和终止器（finisher）
	 * @param persons
	 */
	public static void testCollect() {
		List<Person> persons =
			    Arrays.asList(
			        new Person("Max", 18),
			        new Person("Peter", 23),
			        new Person("Pamela", 23),
			        new Person("David", 12));
		List<Person> filtered =
			    persons
			        .stream()
			        .filter(p -> p.name.startsWith("P"))
			        .collect(Collectors.toList());
		
		//以Set来替代List - Collectors.toSet()

		System.out.println(filtered);
			
		//按照年龄对所有人进行分组
		Map<Integer, List<Person>> personsByAge = persons
			    .stream()
			    .collect(Collectors.groupingBy(p -> p.age));

		personsByAge
			    .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
		
		//计算所有人的平均年龄
		Double averageAge = persons
			    .stream()
			    .collect(Collectors.averagingInt(p -> p.age));

		System.out.println(averageAge);  
	
		
		//概要收集器
		//计算最小年龄、最大年龄、算术平均年龄、总和和数量
		IntSummaryStatistics ageSummary =
			    persons
			        .stream()
			        .collect(Collectors.summarizingInt(p -> p.age));

			System.out.println(ageSummary);
		
		//将所有人连接为一个字符串
		String phrase = persons
			    .stream()
			    .filter(p -> p.age >= 18)
			    .map(p -> p.name)
			    .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

		System.out.println(phrase);	
		
		//将流中的所有人转换为一个字符串，包含所有大写的名称，并以|分割
		Collector<Person, StringJoiner, String> personNameCollector =
			    Collector.of(
			        () -> new StringJoiner(" | "),          // supplier 供应器
			        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator 累加器
			        (j1, j2) -> j1.merge(j2),               // combiner 组合器
			        StringJoiner::toString);                // finisher 终止器

			String names = persons
			    .stream()
			    .collect(personNameCollector);

			System.out.println(names);
	}
	
 
}

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Foo {
    String name;
    List<Bar> bars = new ArrayList<>();

    Foo(String name) {
        this.name = name;
    }
}

class Bar {
    String name;

    Bar(String name) {
        this.name = name;
    }
}

class Outer {
    Nested nested;
}

class Nested {
    Inner inner;
}

class Inner {
    String foo;
}
