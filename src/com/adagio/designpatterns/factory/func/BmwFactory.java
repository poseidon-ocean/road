package com.adagio.designpatterns.factory.func;

import com.adagio.designpatterns.factory.Bmw;
import com.adagio.designpatterns.factory.Car;

public class BmwFactory implements Factory {

	@Override
	public Car getCar() {
		return new Bmw();
	}

}
