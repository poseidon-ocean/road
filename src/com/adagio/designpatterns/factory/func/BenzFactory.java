package com.adagio.designpatterns.factory.func;

import com.adagio.designpatterns.factory.Benz;
import com.adagio.designpatterns.factory.Car;

public class BenzFactory implements Factory {

	@Override
	public Car getCar() {
		return new Benz();
	}
	
}
