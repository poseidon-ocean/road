package com.adagio.designpatterns.factory.func;

import com.adagio.designpatterns.factory.Car;

/**
 * 工厂接口
 * 定义了所有工厂的执行标准
 *
 */
public interface Factory {

	Car getCar();
	
}
