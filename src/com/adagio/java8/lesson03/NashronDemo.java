package com.adagio.java8.lesson03;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 引擎nashorn演示
 *
 */
public class NashronDemo {

	public static void main(String[] args) {
		
//		testString();
		
		testFile();
		
	}
	
	
	public static void testFile() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			engine.eval(new FileReader("resource/java8/lesson03/demo1.js"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
	}


	public static void testString() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			engine.eval("print('Hello World!');");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
	}


	
}
