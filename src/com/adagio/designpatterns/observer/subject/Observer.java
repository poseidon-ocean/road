package com.adagio.designpatterns.observer.subject;

import com.adagio.designpatterns.observer.core.Event;

public class Observer {

	public void advice(Event e) {
		System.out.println("触发事件，打印日志=======" + e);
	}
	
}
