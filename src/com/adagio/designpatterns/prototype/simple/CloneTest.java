package com.adagio.designpatterns.prototype.simple;

public class CloneTest {

	public static void main(String[] args) {
		ConcretePrototype cp = new ConcretePrototype();
		cp.setAge(20);
		cp.setName("pipi");
		
		try {
			ConcretePrototype copy = (ConcretePrototype)cp.clone();
			
			System.out.println(copy.list == cp.list);
			System.out.println(copy.getAge() + "," + copy.getName());
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
}
