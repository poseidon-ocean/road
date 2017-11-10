package com.adagio.java8.lesson02;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 复用数据流
 *
 */
public class ReusageDemo {
	public static void main(String[] args) {
//		Java8的数据流不能被复用。一旦你调用了任何终止操作，数据流就关闭了
		Stream<String> stream =
				Stream.of("d2", "a2", "b1", "b3", "c")
				.filter(s -> s.startsWith("a"));
		
		stream.anyMatch(s -> true);    // ok
//		stream.noneMatch(s -> true);   // exception
		
		
		//克服这个限制
//		/每次对get()的调用都构造了一个新的数据流，我们将其保存来调用终止操作
		Supplier<Stream<String>> streamSupplier =
				() -> Stream.of("d2", "a2", "b1", "b3", "c")
				.filter(s -> s.startsWith("a"));
				
				streamSupplier.get().anyMatch(s -> true);   // ok
				streamSupplier.get().noneMatch(s -> true);  // ok
				
	}
}


