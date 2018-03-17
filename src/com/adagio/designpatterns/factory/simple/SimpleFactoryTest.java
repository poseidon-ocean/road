package com.adagio.designpatterns.factory.simple;

import com.adagio.designpatterns.factory.Car;

public class SimpleFactoryTest {
	
	public static void main(String[] args) {
		Car car = new SimpleFactory().getCar("bmw");
		System.out.println(car.getName());
		System.out.println(car);
	}

}
