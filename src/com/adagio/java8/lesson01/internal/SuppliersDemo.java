package com.adagio.java8.lesson01.internal;

import java.util.function.Supplier;

import com.adagio.java8.lesson01.Person;

/**
 * Supplier接口产生一个给定类型的结果。与Function不同的是，Supplier没有输入参数。
 *
 */
public class SuppliersDemo {

	public static void main(String[] args) {
		Supplier<Person> personSupplier = Person::new;
		System.out.println(personSupplier.get());		// new Person
		
	}
}
