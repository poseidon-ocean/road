package com.adagio.designpatterns.delegate;

public class ExectorA implements IExector {

	@Override
	public void doing() {
		System.out.println("员工A开始执行任务");
	}

}
