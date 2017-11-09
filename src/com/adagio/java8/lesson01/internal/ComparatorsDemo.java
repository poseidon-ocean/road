package com.adagio.java8.lesson01.internal;

import java.util.Comparator;

import com.adagio.java8.lesson01.Person;

/**
 * �ȽϺ���
 * Java 8 Ϊ����ӿ�����˲�ͬ��Ĭ�Ϸ���
 *
 */
public class ComparatorsDemo {

	public static void main(String[] args) {
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
		
		Person p1 = new Person("Josh", "Bloch");
		Person p2 = new Person("Neal", "Gafter");
		
		System.out.println(comparator.compare(p1, p2));
		System.out.println(comparator.reversed().compare(p1, p2));
	}
}
