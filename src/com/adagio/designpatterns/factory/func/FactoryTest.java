package com.adagio.designpatterns.factory.func;

public class FactoryTest {

	public static void main(String[] args) {
		Factory factory = new AudiFactory();
		System.out.println(factory.getCar().getName());
		
		factory = new BenzFactory();
		System.out.println(factory.getCar().getName());
	}
	
}
