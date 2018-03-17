package com.adagio.designpatterns.factory.abstr;

import com.adagio.designpatterns.factory.Bmw;
import com.adagio.designpatterns.factory.Car;

public class BmwFactory extends AbsractFactory {

	@Override
	protected Car getCar() {
		return new Bmw();
	}

}
