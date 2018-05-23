package com.adagio.designpatterns.observer.core;

import java.lang.reflect.Method;

public class Event {

	//事件源
	private Object source;
	//通知目标
	private Object target;
	//回调
	private Method callback;
	//触发
	private String trigger;
	
	private long time;
	
	public Event(Object target,Method callback) {
		this.target = target;
		this.callback = callback;
	}
	
	public Object getSource() {
		return source;
	}
	Event setSource(Object source) {
		this.source = source;
		return this;
	}
	public Object getTarget() {
		return target;
	}
	
	Event setTarget(Object target) {
		this.target = target;
		return this;
	}
	public Method getCallback() {
		return callback;
	}
	public void setCallback(Method callback) {
		this.callback = callback;
	}
	public String getTrigger() {
		return trigger;
	}
	
	Event setTrigger(String trigger) {
		this.trigger = trigger;
		return this;
	}
	
	public long getTime() {
		return time;
	}

	public Event setTime(long time) {
		this.time = time;
		return this;
	}

	@Override
	public String toString() {
		return "Event {"
					+ "\n\t source=" + source + ", \n"
					+ "\n\t target=" + target + ", \n"
					+ "\n\t callback=" + callback + ", \n"
					+ "\n\t trigger=" + trigger
				+ "}";
	}

}
