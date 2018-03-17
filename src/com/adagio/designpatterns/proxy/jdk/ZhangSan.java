package com.adagio.designpatterns.proxy.jdk;

public class ZhangSan implements Person {
	
	private String age = "26";
	private String name = "张三";

	@Override
	public void buyTicket() {
		System.out.println("我叫：" + this.name + " 今年：" + this.age + " 岁了");
		System.out.println("回家过年，买不到票，需要找黄牛");
		System.out.println("买一张回家的票，成人票");
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
