package com.adagio.designpatterns.factory.abstr;

import com.adagio.designpatterns.factory.Audi;
import com.adagio.designpatterns.factory.Car;

public class AudiFactory extends AbsractFactory {

	@Override
	protected Car getCar() {
		return new Audi();
	}

}
