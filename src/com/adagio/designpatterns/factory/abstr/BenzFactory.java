package com.adagio.designpatterns.factory.abstr;

import com.adagio.designpatterns.factory.Benz;
import com.adagio.designpatterns.factory.Car;

public class BenzFactory extends AbsractFactory {

	@Override
	protected Car getCar() {
		return new Benz();
	}

}
