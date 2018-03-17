package com.adagio.designpatterns.factory.abstr;

import com.adagio.designpatterns.factory.Car;

public class DefaultFactory extends AbsractFactory {

	private BenzFactory defaultFactory = new BenzFactory();
	
	@Override
	protected Car getCar() {
		return defaultFactory.getCar();
	}

}
