package com.adagio.designpatterns.adapter.passport;

import com.adagio.designpatterns.adapter.R;

/**
 * 第三方登录
 * 
 * 
 * 适配器模式
 * 
 * 稳定的方法不去动，直接继承下来
 *
 */
public class SigninForThirdService extends SigninService {

	public R loginForQQ(String openId) {
		//openId全局唯一，可以当作是一个用户名
		//密码 默认 QQ_EMPTY
		//注册，在原有的系统上创建一个用户
		R r = super.regist(openId, null);
		//登录，调用原来的登录方法
		r = super.login(openId, null);
		return null;
	}
	
	public R loginForWebchat(String openId) {
		return null;
	}
	
	public R loginForToken(String token) {
		return null;
	}
	
	public R loginForTelphone(String telphone, String code) {
		return null;
	}
	
	public R loginForRegist(String username, String password) {
		return null;
	}
}
