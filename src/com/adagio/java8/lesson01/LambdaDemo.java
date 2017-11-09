package com.adagio.java8.lesson01;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Lambda±Ì¥Ô Ω
 *
 */
public class LambdaDemo {

	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

		// 1
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		
		// 2
		Collections.sort(names, (String a, String b) ->{
			return b.compareTo(a);
		});
		
		// 3
		Collections.sort(names, (String a, String b) -> b.compareTo(a));
		
		// 4
		Collections.sort(names, (a, b) -> b.compareTo(a));
		
		names.forEach(n -> System.out.println(n));
	}
	
}
