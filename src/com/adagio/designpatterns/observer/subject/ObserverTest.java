package com.adagio.designpatterns.observer.subject;

import java.lang.reflect.Method;

import com.adagio.designpatterns.observer.core.Event;

public class ObserverTest {

	public static void main(String[] args) {
		try {
			
			//观察者
			Observer observer = new Observer();
			Method advice = Observer.class.getMethod("advice", new Class<?>[] {Event.class});
			
			//被观察者
			Subject subject = new Subject();
			subject.addLisenter(SubjectType.ON_ADD, observer, advice);
			subject.addLisenter(SubjectType.ON_QUERY, observer, advice);
			
			subject.add();
			subject.remove();
			subject.edit();
			subject.query();
			
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
}
