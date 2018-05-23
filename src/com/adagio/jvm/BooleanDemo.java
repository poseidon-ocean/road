package com.adagio.jvm;

public class BooleanDemo {

	/**
	 *  javap -c .\Demo02.class
	 *  运行上面命令：可以看出boolean在编译期间是用int类型表示的
	 */
	public void complieBoolean() {
		boolean a = true;
		Boolean b = new Boolean(false);
		
		int c = 1;
		float d = 1.0f;
		
	}
	
}
