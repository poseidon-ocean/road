package com.adagio.designpatterns.observer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventLisenter {

	protected Map<Enum, Event> events = new HashMap<>();
	
	public void addLisenter(Enum eventType, Object target, Method callback) {
		//注册事件
		//用反射调用这个方法
		events.put(eventType, new Event(target, callback));
	}
	
	private void trigger(Event e) {
//		e.setSource(source);
//		e.setTarget(target);
	}
	
	public void trigger(Enum call) {
		if(!this.events.containsKey(call)) {
			return;
		}
	}
	
}
