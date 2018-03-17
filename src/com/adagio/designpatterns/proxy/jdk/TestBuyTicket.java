package com.adagio.designpatterns.proxy.jdk;

import java.io.FileOutputStream;

import sun.misc.ProxyGenerator;

public class TestBuyTicket {

	public static void main(String[] args) {
//		new ZhangSan().buyTicket();
		
		
		Person obj = (Person) new HuangNiu().getInstance(new ZhangSan());
		System.out.println(obj.getClass());
		obj.buyTicket();
//		obj.getName();
		
		
		try {
			//获取字节码内容
			byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[] {Person.class});
			FileOutputStream os = new FileOutputStream("D:/test/$Proxy0.class");
			os.write(data);
			os.close();
		} catch (Exception e) {
			
		}
		
	}
}
