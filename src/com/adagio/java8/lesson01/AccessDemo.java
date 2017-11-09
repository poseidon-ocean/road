package com.adagio.java8.lesson01;

/**
 * 访问局部对应的外部区域的局部final变量，以及成员变量和静态变量
 *
 */
public class AccessDemo {

	public static void main(String[] args) {
		//访问局部变量
//		final int num = 1;
//		Converter<Integer, String> stringConverter =
//		        (from) -> String.valueOf(from + num);
//
//		System.out.println(stringConverter.convert(2));     // 3
		
		//变量num并不需要一定是final。下面的代码依然是合法的
//		int num = 1;
//		Converter<Integer, String> stringConverter =
//		        (from) -> String.valueOf(from + num);
//
//		stringConverter.convert(2);     // 3
		
		//num在编译的时候被隐式地当做final变量来处理
		int num = 1;
		Converter<Integer, String> stringConverter =
				(from) -> String.valueOf(from + num);
//		num = 3;		//不被允许
		System.out.println(stringConverter.convert(5));
				
		Lambda4 l = new Lambda4();
		l.testScopes();
		
		
		//访问默认接口方法
		//默认方法无法在lambda表达式内部被访问，下面的代码是无法通过编译
//		Formula formula = (a) -> sqrt( a * 100);
	}
	
}

//访问成员变量和静态变量
class Lambda4 {
    static int outerStaticNum;
    int outerNum;

    void testScopes() {
    	//在lambda表达式的内部能获取到对成员变量或静态变量的读写权
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
        
        System.out.println(stringConverter1.convert(6) + "==" + stringConverter2.convert(7));
    }
}
