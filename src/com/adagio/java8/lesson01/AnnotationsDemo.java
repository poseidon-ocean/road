package com.adagio.java8.lesson01;

import java.lang.annotation.Repeatable;

/**
 * Java 8中的注解是可重复的
 *
 */
public class AnnotationsDemo {

	public static void main(String[] args) {
		Hint hint = Human.class.getAnnotation(Hint.class);
		System.out.println(hint);
		
		Hints hints1 = Human.class.getAnnotation(Hints.class);
		System.out.println(hints1);
		
		Hint[] hints2 = Animal.class.getAnnotationsByType(Hint.class);
		System.out.println(hints2.length);          
	}
}


@interface Hints{
	Hint[] value();
}

/**
 * 加上注解名：@Repeatable，允许对同一类型使用多重注解
 *
 */
@Repeatable(Hints.class)
@interface Hint{
	String value();
}

/**
 * 使用注解容器
 *
 */
@Hints({@Hint("hint1"), @Hint("hint2")})
class Human{}

/**
 * 使用可重复注解
 *
 */
@Hint("hint1")
@Hint("hint2")
class Animal{}

