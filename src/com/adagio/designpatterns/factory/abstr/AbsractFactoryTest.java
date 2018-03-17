package com.adagio.designpatterns.factory.abstr;

public class AbsractFactoryTest {

	public static void main(String[] args) {
		DefaultFactory factory = new DefaultFactory();
		System.out.println(factory.getCar("audi").getName());
		
		//设计模式的经典之处在于解决了编写代码的人和调用代码的人的双方痛处
		//解放我们的双手
	}
}
