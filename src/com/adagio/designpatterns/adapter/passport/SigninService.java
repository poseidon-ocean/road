package com.adagio.designpatterns.adapter.passport;

import com.adagio.designpatterns.adapter.R;

public class SigninService {

	/**
	 * 注册
	 * @param username
	 * @param password
	 * @return
	 */
	public R regist(String username, String password) {
		return new R("200", "注册成功", null);
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public R login(String username, String password) {
		return new R("200", "登录成功", null);
	}
	
}
