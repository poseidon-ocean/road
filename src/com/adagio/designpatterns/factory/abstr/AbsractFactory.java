package com.adagio.designpatterns.factory.abstr;

import com.adagio.designpatterns.factory.Car;

public abstract class AbsractFactory {

	protected abstract Car getCar();
	
	public Car getCar(String name) {
		
		if("BENZ".equalsIgnoreCase(name)) {
			return new BenzFactory().getCar();
		} else if("BMW".equalsIgnoreCase(name)) {
			return new BmwFactory().getCar();
		} else if("AUDI".equalsIgnoreCase(name)) {
			return new AudiFactory().getCar();
		} else {
			System.out.println("该产品不生产");
			return null;
		}
		
	}
}
