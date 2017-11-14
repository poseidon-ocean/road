package com.adagio.java8.lesson03;

import java.util.Arrays;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * 在JavaScript中调用Java方法
 *
 */
public class JavaScriptDemo {

	static String fun1(String name) {
	    System.out.format("Hi there from Java, %s", name);
	    return "greetings from java";
	}
	
	static void fun2(Object object) {
	    System.out.println(object.getClass());
	}
	
	static void fun3(ScriptObjectMirror mirror) {
	    System.out.println(mirror.getClassName() + ": " +
	        Arrays.toString(mirror.getOwnKeys(true)));
	}
	
	static void fun4(ScriptObjectMirror person) {
	    System.out.println("Full Name is: " + person.callMember("getFullName"));
	}
	
}
