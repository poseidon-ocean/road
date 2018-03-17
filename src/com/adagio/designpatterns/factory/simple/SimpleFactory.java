package com.adagio.designpatterns.factory.simple;

import com.adagio.designpatterns.factory.Audi;
import com.adagio.designpatterns.factory.Benz;
import com.adagio.designpatterns.factory.Bmw;
import com.adagio.designpatterns.factory.Car;

public class SimpleFactory {
	
	public Car getCar(String name) {
		if("BMW".equalsIgnoreCase(name)) {
			return new Bmw();
		} else if("BENZ".equalsIgnoreCase(name)) {
			return new Benz();
		} else if("AUDI".equalsIgnoreCase(name)) {
			return new Audi();
		} else {
			System.out.println("不能生产的品牌");
			return null;
		}
	}

}
