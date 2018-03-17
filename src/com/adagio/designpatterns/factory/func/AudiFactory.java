package com.adagio.designpatterns.factory.func;

import com.adagio.designpatterns.factory.Audi;
import com.adagio.designpatterns.factory.Car;

public class AudiFactory implements Factory {

	@Override
	public Car getCar() {
		return new Audi();
	}

}
