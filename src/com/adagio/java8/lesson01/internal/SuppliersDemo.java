package com.adagio.java8.lesson01.internal;

import java.util.function.Supplier;

import com.adagio.java8.lesson01.Person;

/**
 * Supplier�ӿڲ���һ���������͵Ľ������Function��ͬ���ǣ�Supplierû�����������
 *
 */
public class SuppliersDemo {

	public static void main(String[] args) {
		Supplier<Person> personSupplier = Person::new;
		System.out.println(personSupplier.get());		// new Person
		
	}
}
