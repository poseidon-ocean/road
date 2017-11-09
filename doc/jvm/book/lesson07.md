# 虚拟机类加载机制
* 代码编译的结果从本地机器码转变为字节码，是存储格式发展的一小步，却是编程语言发展的一大步
* 虚拟机把描述类的数据从Class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被虚拟机直接使用的Java类型，这就是虚拟机的类加载机制

## 类加载的时机
* 生命周期：
	* 加载(Loading)、验证(Verification)、准备(Preparation)、解析(Resolution)、
	* 初始化(Initialization)、使用(Using)和卸载(Unloading)
* 对类进行初始化
	* 遇到指令new、getstatic、putstatic、invokestatic这4条字节码指令时，如果类没有初始化会先触发其初始化
	* 使用java,lang.reflect包的方法对类进行反射调用时，如果类没有初始化会先触发其初始化
	* 当初始化一个类时，发现父类没有初始化，会先触发父类的初始化
	* 当虚拟机启动时，用户需要指定一个要执行的主类(包含main()方法的那个类)，虚拟机会先初始化这个主类
	* 使用JDK1.7的动态语言支持
		* 如果一个java.lang.invoke.MethodHandle实例最后的
		* 解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄
		* 这个方法句柄对方的类没有初始化会先触发其初始化
* 以上5种场景中的行为称为对一个类进行主动引用，其它的称为被动引用
```
//通过子类引用父类的静态字段，不会导致资料初始化
class SuperClass{
	
	static {
		System.out.println("SuperClass init!");
	}
	public static int value = 123;
}

class SubClass extends SuperClass {
	static {
		System.out.println("SubClass init");
	}
	
}

//非主动使用类字段演示
public class NotInitialization {

	public static void main(String[] args) {
		//非主动使用类字段演示一：初始化父类
		//System.out.println(SubClass.value);
		//非主动使用类字段演示二：数组，没有触发父类的初始化
		SuperClass[] sca = new SuperClass[10];
	}
}
```

```
class ConstClass {
	static {
		System.out.println("ConstClass int");
	}
	//常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的初始化
	public static final String HELLO = "hello world";
}

public class NotInitialization {

	public static void main(String[] args) {
		////非主动使用类字段演示三：ConstClass类不会初始化
		System.err.println(ConstClass.HELLO);
	}
}
```
* 加载
	* 普通类的加载见java_class_load.md
	* 数组类本身不通过类加载器创建，由Java虚拟机直接创建
		* 如果数组的组件类型(Component Type，指的是数组去掉一个维度的类型)是引用类型，那就递归采用普通的加载
		* 如果不是引用类型，Java虚拟机会把数组C标记为引导类加载器关联
		* 可见性与其它的组件类型可见性一致，若不是引用类型，可见性默认为public
* 验证
	* 目的是确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全
	* 决定了Java虚拟机是否能承受恶意代码的攻击
	* 校验动作：文件格式验证、元数据验证、字节码验证、符号引用验证
* 准备
	* 为类变量分配内存并设置类变量初始值的阶段
* 解析
	* 虚拟机将常量池内的符号引用替换为直接引用的过程
	* 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符7类符号引用进行
```
package com.leaf.u_jvm.class_load;
/**
 * 字段解析 DEMO
 * 
 * @desc 广告位资源
 *
 */
public class FieldResolution {

	interface Interface0 {
		int A = 0;
	}
	
	interface Interface1 extends Interface0{
		int A = 1;
	}
	
	interface Interface2 {
		int A = 2;
	}
	
	static class Parent implements Interface1{
		public static int A = 3;
	}
	
	static class Sub extends Parent implements Interface2 {
		//注释掉这段代码 会报The field Sub.A is ambiguous 
		//同时在父类和接口中定义了，编译器拒绝编译
		public static int A = 4;
	}
	
	public static void main(String[] args) {
		System.out.println(Sub.A);
	}
}
```
* 初始化
	* 执行类中定义的Java程序代码
	* 初始化阶段是执行类构造器<client>()方法的过程
	
## 类加载器
* 通过一个类的全限定名来获取描述此类的二进制字节流
* 比较两个类是否“相等”，只有在这两个类是由同一个类加载器加载的前提下才有意义	

### 双亲委派模型
* 从Java虚拟机的角度来讲：
	* 启动类加载器(Bootatrap ClassLoader)，使用C++实现的，虚拟机的一部分
	* 其它类加载器：Java语言实现，独立于虚拟机外，继承java.lang.ClassLoader
* 从Java开发人员的角度：
	* 启动类加载器(Bootatrap ClassLoader)：%JAVA_HOME%/lib目录下，如rt.jar
	* 扩展类加载器(Extension ClassLoader)：由sun.misc.Launcher$ExtClassLoader实现
		* 负责加载%JAVA_HOME%/lib/ext目录
		* 或被java.ext.dirs系统变量所指定的路径中的所有类库
		* 开发者可以直接扩展该类
	* 应用程序类加载器(Application ClassLoader)：由sun.misc.Launcher$App-ClassLoader实现
		* 是ClassLoader中的getSystemClassLoader()方法的返回值，也称系统类加载器
	* 自定义类加载器：需要的时候自己实现Application ClassLoader
* 类加载器之间的层次关系，称为类加载器的双亲委派模型
	* 双亲委派模型要求除顶层启动类加载器之外，其余的类加载器都应当有自己的父类加载器
	* 工作过程：
		* 如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类
		* 而是把这个请求委派给父类加载器去完成，每一层的类加载器都是如此
		* 因此所有的加载请求都应该传送到顶层的启动类加载器中
		* 只有当父类加载器反馈无法完成这个加载请求时，子加载器才会自己去尝试加载
	* 实现双亲委派的代码集中在java.lang.ClassLoader#loadClass()方法





	
	



