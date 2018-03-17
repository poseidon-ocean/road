package com.adagio.designpatterns.proxy.custom;

import com.adagio.designpatterns.proxy.jdk.Person;
import com.adagio.designpatterns.proxy.jdk.ZhangSan;

public class TestBuyTicket {

	public static void main(String[] args) {
		
		Person obj = (Person) new HuangNiu().getInstance(new ZhangSan());
		System.out.println(obj.getClass());
		obj.buyTicket();
		
	}
}
