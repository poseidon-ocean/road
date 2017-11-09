package com.adagio.java8.lesson01;

/**
 * 函数式接口
 * 如何使用::关键字引用构造函数
 */
public class FunctionalDemo2 {

	public static void main(String[] args) {
		//通过Person::new来创建一个Person类构造函数的引用
		PersonFactory<Person> personFactory = Person::new;
		//Java编译器会自动地选择合适的构造函数来匹配PersonFactory.create函数的签名，并选择正确的构造函数形式
		Person person = personFactory.create("Peter", "Parker");
		System.out.println(person.firstName);
	}
}

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}

