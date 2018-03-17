package com.adagio.designpatterns.adapter;

import com.adagio.designpatterns.adapter.passport.SigninForThirdService;

public class SigninForThirdServiceTest {

	public static void main(String[] args) {
		SigninForThirdService service = new SigninForThirdService();
		
		//这里可以加一层策略模式
//		loginForXXX
		
		//不改变原来代码
		service.loginForQQ("fjdsfg4515asdf4sd4f5dsf45");
		
	}
}
