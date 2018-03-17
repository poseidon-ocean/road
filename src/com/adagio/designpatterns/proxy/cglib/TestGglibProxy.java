package com.adagio.designpatterns.proxy.cglib;

public class TestGglibProxy {

	public static void main(String[] args) throws Exception {
		ZhangSan obj = (ZhangSan) new HuangNiu().getInstance(ZhangSan.class);
//		System.out.println(obj.getClass());
		obj.buyTicket();
	}
}
