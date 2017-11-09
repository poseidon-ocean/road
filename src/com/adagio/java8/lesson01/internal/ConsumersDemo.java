package com.adagio.java8.lesson01.internal;

import java.util.function.Consumer;

import com.adagio.java8.lesson01.Person;

/**
 * Consumer代表了在一个输入参数上需要进行的操作
 *
 */
public class ConsumersDemo {

	public static void main(String[] args) {
		Consumer<Person> greeter = (p) -> System.out.println("Hello," + p.firstName);
		greeter.accept(new Person("Andy", "Wilkinson"));
	}
}
