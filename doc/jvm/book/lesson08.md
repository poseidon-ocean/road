# 虚拟机字节码执行引擎
* 执行引擎：输入的是字节码文件，处理过程是字节码解析的等效过程

## 运行时栈帧结构
* 栈帧(Stack Frame)是用于支持虚拟机进行方法调用和方法执行的数据结构
* 也是虚拟机运行时数据区中的虚拟机栈(Virtual Machine Stack)的栈元素
* 栈帧存储了方法的局部变量表、操作数栈、动态连接和方法返回地址等信息
* 每一个方法从调用开始至执行完成的过程，都对应着一个栈帧在虚拟机里面从入栈到出栈

### 局部变量表
* 局部变量表(Local Variable Table)是一组变量值存储空间，用于存放方法参数和方法内部定义的局部变量
 
### 操作数栈
* 操作数栈(Operand Stack)是一个后入先出(Last In First Out)栈
* 开始执行方法，这个方法的操作数栈是空的，执行过程会有字节码指令往操作数栈中写入和提取内容
* 操作数栈元素的数据类型必须与字节码指令的序列严格匹配

### 动态链接
* 每一个栈帧都包含一个指向运行时常量池中该栈帧所属方法的引用，持有这个引用是为了支持方法调用过程中的动态链接

### 方法返回地址
* 一个方法执行后怎么退出：
	* 执行引擎遇到任意一个方法返回的字节码指令
	* 执行过程遇到异常，并且没有在方法内处理
* 方法退出就是出栈，可能执行
	* 恢复上层方法的局部变量表和操作数栈
	* 把返回值压(如果有的话)入调用者栈帧的操作数栈中
	* 调整PC计数器的值以指向方法调用指令后面的一条指令

### 附加信息
* 虚拟机规范允虚拟机实现增加一些规范里没有描述的信息到栈帧之中

## 方法调用
* 在程序写好、编译器进行编译时确定的方法调用，称为解析
	* invokestatic：调用静态方法
	* invokespecial：调用实例构造器<init>方法、私有方法和父类方法
	* invokevirtual：调用所有的虚方法
	* invokeinterface：调用接口方法，会在运行时再确定一个实现此接口的对象
	* inveokdynamic：分派逻辑由用户所设定的引导方法决定
	
### 分派
* 分派调用过程揭示多态性特征的一些最基本的体现
* 依赖静态类型来定位方法执行版本的分派动作称为静态分派，与多态的重载(OverLoad)有关
```
package com.adagio.demo;

/**
 * 静态分派 DEMO
 *
 */
public class StaticDispatch {

	static abstract class Human {
		
	}
	
	static class Man extends Human {
		
	}
	
	static class Woman extends Human {
		
	}
	
	public void sayHello(Human guy) {
		System.out.println("hi,human");
	}
	
	public void sayHello(Man guy) {
		System.out.println("hi,gentleman");
	}
	
	public void sayHello(Woman guy) {
		System.out.println("hi,lady");
	}
	
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		
		StaticDispatch sd = new StaticDispatch();
		
		//实际类型变化
		sd.sayHello(man);
		sd.sayHello(woman);
		
		//静态类型变化
		sd.sayHello((Man)man);
		sd.sayHello((Woman)woman);
		
		/**
		 * 静态类型变化:仅在使用时发生，变量本身的静态类型不会改变，并且最终的静态类型是在编译期可知的
		 * 实际类型变化：结果在运行期才可确定，编译器在编译时不知道一个对象的实际类型是什么
		 */
		
	}
	
}
```

```
package com.adagio.demo;

import java.io.Serializable;

/**
 * 重载方法匹配优先级
 * 'a'转整数97
 * char -> int -> long ->float -> double 
 * -> Character 封装类型
 * -> Serializable  Character实现了序列化
 * -> Object 超级父类
 * -> char... 可变参数
 */
public class OverLoad {

	// 6
	public static void sayHello(Object arg) {
		System.out.println("hello, object");
	}
	
	// 2
	public static void sayHello(int arg) {
		System.out.println("hello, int");
	}
	
	// 3
	public static void sayHello(long arg) {
		System.out.println("hello, long");
	}
	
	// 4
	public static void sayHello(Character arg) {
		System.out.println("hello, Character");
	}
	
	// 1
	public static void sayHello(char arg) {
		System.out.println("hello, char");
	}
	
	public static void sayHello(char... arg) {
		System.out.println("hello, char...");
	}
	
	// 5
	public static void sayHello(Serializable arg) {
		System.out.println("hello, Serializable");
	}
	
	public static void main(String[] args) {
		sayHello('a');
	}
}
```
* 动态分派，与多态的重写(Override)有关

### 动态语言支持
* Java语言没有直接变为动态语言，是通过内置动态语言执行引擎的方式来满足动态性的需求
	* 如JavaScript 在包jdk.nashorn之下，实现了ECMAScript规范
* 动态语言特征：类型检查的主体过程是在运行期而不是编译期

## 基于栈的字节码解释执行引擎
* 解释执行：编译程序代码到目标机器代码的过程
* 编译过程
```
程序源码	-> 	词法分析	 -> 	单词流	 -> 语法分析
											|向下
解释执行	<- 	解释器	 <- 指令流(可选)	 <- 抽象语法树
											|向下
目标代码	<- 	生成器	 <- 中间代码(可选)	 <- 优化器(可选)
```
* javac编译器完成了程序代码经过词法分析、语法分析到抽象语法树，再遍历语法树生成线性的字节码指令流的过程
* 基于栈的指令集和基于寄存器的指令集

获取持久力的创作：
养成基础体力，获得强壮坚韧的体力，让身体站在自己这一边，成为友军

有氧运动是指游泳和跑步这类时间长、运动量适度的运动


