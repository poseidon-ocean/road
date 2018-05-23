package com.adagio.designpatterns.observer.subject;

import com.adagio.designpatterns.observer.core.EventLisenter;

public class Subject extends EventLisenter {

	public void add() {
		System.out.println("执行add方法");
		trigger(SubjectType.ON_ADD);
	}
	
	public void remove() {
		System.out.println("执行remove方法");
		trigger(SubjectType.ON_RMOVE);
	}
	
	public void edit() {
		System.out.println("执行edit方法");
		trigger(SubjectType.ON_EDIT);
	}
	
	public void query() {
		System.out.println("执行query方法");
		trigger(SubjectType.ON_QUERY);
	}
	
	
}
