package com.adagio.designpatterns.observer.mouse;

import java.lang.reflect.Method;

import com.adagio.designpatterns.observer.core.Event;

public class MouseTest {

	public static void main(String[] args) {
		
		try {
			MouseEventCallback callback = new MouseEventCallback();
			Method onClick = MouseEventCallback.class.getMethod("onClick", Event.class);
			
			
			Mouse mouse = new Mouse();
			mouse.addLisenter(MouseEventType.ON_CLICK, callback, onClick);
			
			mouse.click();
		} catch (Exception e) {
			
		}
		
	}
	
}
