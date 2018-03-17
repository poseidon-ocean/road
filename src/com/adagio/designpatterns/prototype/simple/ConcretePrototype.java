package com.adagio.designpatterns.prototype.simple;

import java.util.ArrayList;

public class ConcretePrototype implements Cloneable {

	private int age;
	private String name;
	
	public ArrayList<String> list = new ArrayList<>();
	
	protected Object clone() throws CloneNotSupportedException {
		ConcretePrototype prototype = null;
		
		try {
			prototype = (ConcretePrototype)super.clone();
			prototype.list = (ArrayList<String>) list.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prototype;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
