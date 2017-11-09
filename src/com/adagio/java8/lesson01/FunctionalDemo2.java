package com.adagio.java8.lesson01;

/**
 * ����ʽ�ӿ�
 * ���ʹ��::�ؼ������ù��캯��
 */
public class FunctionalDemo2 {

	public static void main(String[] args) {
		//ͨ��Person::new������һ��Person�๹�캯��������
		PersonFactory<Person> personFactory = Person::new;
		//Java���������Զ���ѡ����ʵĹ��캯����ƥ��PersonFactory.create������ǩ������ѡ����ȷ�Ĺ��캯����ʽ
		Person person = personFactory.create("Peter", "Parker");
		System.out.println(person.firstName);
	}
}

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}

