# Java类的加载机制

## 什么是类的加载
* 类的加载指的是将类的.class文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后在堆区创建一个java.lang.Class对象，用来封装类在方法区内的数据结构
* 类的加载的最终产品是位于堆区中的Class对象，Class对象封装了类在方法区的数据结构，并且向Java程序员提供了访问方法区的数据结构的接口
* 类加载器并不需要等到某个类被“首次主动使用”时再加载它，JVM规范允许类加载器在预料到某个类将要被使用时就预先加载它，如果在预先加载的过程中遇到了.class文件缺失或存在错误，类加载器必须在程序首次主动使用该类时才报告错误(LinkageError错误)，如果这个类一直没有被程序主动使用，那么类加载器就不会报告错误
* 加载.class文件的方式
	* 从本地系统中直接加载
	* 通过网络下载.class文件
	* 从zip,jar等归档文件中加载.class文件
	* 从专有数据库中提取.class文件
	* 将Java源文件动态编译为.class文件

## 类的生命周期
* 类加载的过程包括：加载、验证、准备、解析、初始化
* 加载、验证、准备和初始化发生的顺序是确定的，解析阶段不一定(可以在初始化阶段，也可在运行时绑定)
* 注意是按顺序开始，不是按顺序进行或完成

### 加载：查找并加载类的二进制数据
* 加载时虚拟机需要完成：
	* 通过一个类的全限定名来获取其定义的二进制字节流
	* 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构
	* 在Java堆中生成一个代表这个类的java.lang.Class对象，作为对方法区中这些数据的访问入口
* 这个阶段可控性最强，可以使用系统提供的类加载器来完成加载，也可以自定义自己的类加载器来完成加载
* 加载之后，虚拟机外部的二进制字节流就按照虚拟机所需的格式存储在方法区之中，而且在Java堆中也创建一个java.lang.Class类的对象，这样便可以通过该对象访问方法区中的这些数据

### 验证：确保被加载的类的正确性
* 验证、准备、解析是连接的阶段
* 验证的目的是为了确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全
	* 文件格式验证：验证字节流是否符合Class文件格式的规范；如是否以0xCAFEBABE开头，主次版本是否在当前虚拟机的处理范围之内、常量池中的常量是否有不被支持的类型
	* 元数据验证：对字节码描述的信息进行语义分析（注意：对比javac编译阶段的语义分析），以保证其描述的信息符合Java语言规范的要求；例如：这个类是否有父类，除了java.lang.Object之外
	* 字节码验证：通过数据流和控制流分析，确定程序语义是合法的、符合逻辑的
	* 符号引用验证：确保解析动作能正确执行
* 验证很重要，但不是必须的，对程序运行期没有影响，如果所引用的类经过反复验证，那么可以考虑采用-Xverifynone参数来关闭大部分的类验证，以缩短虚拟机类加载的时间

### 准备：为类的静态变量分配内存，并将其初始化为默认值
* 正式为类变量分配内存并设置初始值的阶段，这些内存是在方法区分配，注意：
	* 此时进行内存分配的仅包含类变量(static)，而不包括实例变量，实例变量会在对象实例化时随着对象一块分配在java堆中
	* 设置的初始值通常是数据类型默认的零值(如0，0L，null，false等)，而不是被在Java代码中被显示地赋值
		* 假设：public static int value = 3;
		* 那么变量value在本阶段后的初始值是0，而不是3，因为这时尚未开始执行任何Java方法
		* 把value赋值为3的putstatic指令是在程序编译后，存放与类构造器<client>()方法之中，是在初始化阶段赋值的
	* 如果类字段的字段属性表中存在ConstantValue,即同时被final和static修饰，那么在准备阶段变量value就会被初始化为ConstValue属性所指定的值
		* 假设：public static final int value = 3;
		* 编译时Javac将会为value生成ConstantValue属性，在准备阶段虚拟机就会根据ConstantValue的设置将value赋值为3
* 对基本数据类型来说，对于类变量(static)和全局变量，如果不显示地对其赋值而直接使用，则系统会为其赋予默认的零值，而对局部变量来说，在使用前必须显式地为其赋值，否则编译时不通过
* 对于同时被static和final修饰的常量，必须在声明的时候就为其显式地赋值，否则编译不通过；而只被final修饰的常量则既可以在声明时显式地为其赋值，也可以在类初始化时显式地赋值，总之，在使用前必须为其显式地赋值，系统不会为其赋予默认零值
* 对于引用数据类型reference来说，如数组引用、对象引用等，如果没有对其进行显式地赋值而直接使用，系统都会为其赋予默认的零值。即null
* 如果在数组初始化时没有对数组中的个元素赋值，那么其中的元素将根据对应的数据类型而被赋予默认的零值

### 解析：把类中的引用转换为直接引用
* 解析阶段是虚拟机将常量池内的符号引用替换为直接引用的过程
* 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符7类符号引用进行
* 符合引用就是一组符号来描述目标，可以是任何字面量
* 直接引用就是直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄

## 初始化
* 初始化，为类的静态变量赋予正确的初始值，JVM负责对类进行初始化，主要对类变量进行初始化
* 对类变量进行初始值设定：
	* 声明类变量是指定初始值
	* 使用静态代码为类变量指定初始值
* JVM初始化步骤
	* 假如这个类还没有被加载和连接，则程序先加载并连接该类
	* 假如该类的直接父类还没有被初始化，则先初始化其直接父类
	* 假如类中有初始化语句，则系统依次执行这些初始化语句
* 类初始化时机：只有当对类的主动使用的时候才会导致类的初始化
	* 创建类的实例，也就是new的方式
	* 访问某个类或接口的静态变量，或者对该静态变量赋值
	* 调用类的静态方法
	* 反射(如Class.forName("com.adagio.Test"))
	* 初始化某个类的子类，则其父类也会被初始化
	* Java虚拟机启动时被表明为启动的类(Java Test)，直接使用Java.exe命令来运行某个主类
	
## 结束生命周期
* 在如下几种情况下，Java虚拟机将结束生命周期
	* 执行了System.exit()方法
	* 程序正常执行结束
	* 程序在执行过程中遇到了异常或错误而异常终止
	* 由于操作系统出现错误而导致Java虚拟机进程终止
	
## 类加载器
* 寻找类加载器
```
package com.sky.d_cache;

public class ClassLoaderDemo {

	public static void main(String[] args) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		System.out.println(classLoader);
		System.out.println(classLoader.getParent());
		System.out.println(classLoader.getParent().getParent());
	}

	/**
	 * 输出结果：
	 * sun.misc.Launcher$AppClassLoader@73d16e93
	sun.misc.Launcher$ExtClassLoader@15db9742
	null
		没有获取到ExtClassLoader的父类Loader，原因是Bootstrap Loader(引导类加载器)是C语言实现的
		找不到一个确定的返回Loader的方式，就返回null
	
	 */
}
```
* 站在Java虚拟机角度，两种不同的类加载器：
	* 启动类加载器，使用C++实现，是Java虚拟机的一部分
		* 仅限HotSpot，也就是JDK1.5之后的虚拟机
		* 有很多其它类加载器是Java语言实现的
	* 所有其他类加载器，都由Java语言实现，独立于虚拟机外，全部继承ClassLoader
		* 这些类加载器需要由启动类加载器加载到内存中之后才能去加载其他的类
* 站在Java开发人员的角度，划分：
	* 启动类加载器：Bootstrap ClassLoader
		* 负责加载存放在JDK\jre\lib下（JDK代表JDK目录，下同），
		* 或被一Xbootclasspath参数指定的路径中的，
		* 并且能够被虚拟机识别的类库（如rt.jar，所有java.*开头的类均被Bootstrap ClassLoader加载）
		* 启动类加载器是无法被Java程序直接引用的
	* 扩展类加载器：Extension ClassLoader
		* 由sun.misc.Launcher$ExtClassLoader@15db9742实现
		* 负责加载JDK\jre\lib\ext目录中
		* 或者由java.ext.dirs系统变量指定的路径中的所有类库（如javax.*开头的类）
		* 开发者可以直接使用扩展类加载器
	* 应用程序类加载器：Application ClassLoader
		* 由sun.misc.Launcher$AppClassLoader@73d16e93来实现
		* 负责加载用户类路径(ClassPath)所指定的类
		* 开发者可以直接使用该类加载器
		* 应用程序如果没有自定义过自己的类加载器，一般情况下程序默认使用这个类加载器
* JVM自带的ClassLoader只能从本地文件系统加载标准的java class文件，自定义的可以：
	* 在执行非信代码之前，自动验证数字签名
	* 动态地创建符合用户特定需要的定制化构建
	* 从特定的场所取得java class，例如数据库中和网络中
* JVM类加载机制
	* 全盘负责，当一个类加载器负责加载某个类Class时，该Class所依赖的和引用的其它Class也将由该类加载器负责载入，除非显示使用另外一个类加载器来载入
	* 父类委托，先让父类加载器试图加载该类，只有在父类加载器无法加载该类时才尝试从自己的类路径中加载该类
	* 缓存机制，缓存机制将会保证所有加载过的Class都会被缓存，当程序需要使用某个Class时，类加载器先从缓存区寻找该Class，只有缓存区不存在，系统才会读取该类对应的二进制数据，并将其转换成Class对象，存入缓存区，这就是为什么修改了Class后，必须重启JVM，程序的修改才会生效
	
## 类的加载
* 类加载方式：
	* 命令行启动应用时候由JVM初始化加载
	* 通过Class.forName()方法动态加载
	* 通过ClassLoader.loadClass()方法动态加载
```
package com.sky.d_cache;

public class Hello {

	static {
		System.out.println("Hello Class loaded!");
	}
}

package com.sky.d_cache;

public class LoadHello {

	public static void main(String[] args) throws ClassNotFoundException {
		ClassLoader  loader = Hello.class.getClassLoader();
		System.out.println(loader);
		
		//不会执行sratic代码块
//		loader.loadClass("com.sky.d_cache.Hello");
		//默认会执行sratic代码块
//		Class.forName("com.sky.d_cache.Hello");
		//指定ClassLoader，false不会执行sratic代码块 true会执行
		Class.forName("com.sky.d_cache.Hello", false, loader);
		
	}
}
```
* Class.forName()和ClassLoader.loadClass()区别
	* Class.forName()：将类的.class文件加载到jvm中，还对类进行解释，执行类中的static块
	* ClassLoader.loadClass()：只将.class文件加载到jvm中，不会执行static中的内容，只有在newInstance才会去执行static块
	* Class.forName(name, initialize, loader)：可控是否加载static块，只有调用了newInstance()方法采用调用构造函数，创建类对象

## 双亲委派模型
* 工作流程：如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把请求委托给父加载器去完成，依次向上，因此，所有的类加载请求最终都应该被传递到顶层的启动类加载器中，只有当父加载器在它的搜索范围内没有找到所需的类时，即无法完成该加载，子加载器才会尝试自己去加载该类
* 双亲委派机制：
	* 当AppClassLoader加载一个class时，它首先不会自己去尝试加载这个类，而是把类加载请求委派给父类加载器ExtClassLoader去完成
	* 当ExtClassLoader加载一个class时，它首先也不会自己去尝试加载这个类，而是把类加载请求委派给BootStrapClassLoader去完成
	* 如果BootStrapClassLoader加载失败(例如在$JAVA_HOME/jre/lib里未查找到该class)，会使用ExtClassLoader来尝试加载
	* 若ExtClassLoader也加载失败，则会使用AppClassLoader来加载，如果AppClassLoader也加载失败，则会报异常ClassNotFoundException
* ClassLoader源码分析
```
 protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // 首先判断该类是否被加载
            Class<?> c = findLoadedClass(name);
            //如果没有被加载，就委托给父类加载或者委派给启动类加载器加载
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                    	//如果存在父类加载器，就委派给父类加载器加载
                        c = parent.loadClass(name, false);
                    } else {
                    	//如果不存在父类加载器，就检查是否是由启动类加载器加载的类，通过调用native方法：private native Class<?> findBootstrapClass(String name);
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    //如果父类加载器和启动类加载器都不能完成加载任务，才调用自身的加载功能
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
```
* 双亲委派模型的意义
	* 系统类防止内存中出现多份同样的字节码
	* 保证Java程序安全稳定运行

## 自定义类加载器
* 通常直接使用系统类加载器，有时也需要自定义类加载器
* 如应用通过网络来传输Java类的字节码，为保证安全性，这些字节码经过了加密处理，这时系统类加载器就无法对其进行加载，需要自定义类加载器
* 自定义类加载器一般继承ClassLoader类，从上面对loadClass方法来分析，只需重写findClass方法
```
package com.sky.d_cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 演示自定义类加载器流程
 *  
 */
public class MyClassLoader extends ClassLoader {

	private String root;
	
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		byte[] classData = loadClassData(className);
		if(classData == null){
			throw new ClassNotFoundException();
		} else {
			return defineClass(className, classData, 0, classData.length);
		}
	}

	private byte[] loadClassData(String className) {
		String fileName = root + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
		
		InputStream ins = null;
		ByteArrayOutputStream baos = null;
		try {
			ins = new FileInputStream(fileName);
			baos = new ByteArrayOutputStream();
			
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			
			int length = 0;
			while((length = ins.read(buffer)) != -1){
				baos.write(buffer, 0, length);
			}
			
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(ins != null){
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	public static void main(String[] args) {
		MyClassLoader classLoader = new MyClassLoader();
		classLoader.setRoot("D:\\test");
		
		try {
			Class<?> testClass = classLoader.loadClass("com.sky.d_cache.Hello");
			Object object = testClass.newInstance();
			System.out.println(object.getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}

```
* 自定义类加载器的核心在于对字节码文件的获取，如果是加密的字节码则需要在该类中对文件进行解密
* 注意点：
	* 传递的文件名需要类的全限定名，即com.adagio.Test格式，因为defineClass方法是按此格式处理的
	* 最好不要重写loadClass方法，因为容易破坏双亲委托模式
	* 类本身可以被AppClassLoader类加载，因此不能把类放在类路径下，否则由于双亲委托机制的存在，会导致该类由AppClassLoader加载，而不是自定义的类加载器加载










引：http://www.cnblogs.com/ityouknow/p/5603287.html

承认人性的弱点，正视人性的阴暗面
困扰当代人最大的问题，不是缺乏机会，而是机会太多
最重要的事情只有一件





