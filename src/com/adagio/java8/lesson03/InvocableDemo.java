package com.adagio.java8.lesson03;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.adagio.java8.lesson01.Person;

/**
 * Nashorn 支持从Java代码中直接调用定义在脚本文件中的JavaScript函数
 *
 */
public class InvocableDemo {

	public static void main(String[] args) throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval(new FileReader("resource/java8/lesson03/demo2.js"));

		//将脚本引擎转换为Invocable
		Invocable invocable = (Invocable) engine;

		Object result = invocable.invokeFunction("fun1", "Peter Parker");
		System.out.println(result);
		System.out.println(result.getClass());
		
		
		//由于脚本在JVM上原生运行，我们可以在Nashron上使用Java API或外部库的全部功能
		invocable.invokeFunction("fun2", new Date());

		invocable.invokeFunction("fun2", LocalDateTime.now());

		invocable.invokeFunction("fun2", new Person());
	}
}
