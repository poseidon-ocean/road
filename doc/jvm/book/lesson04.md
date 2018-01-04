# 虚拟机性能监控与故障处理工具

* 定位系统问题
	* 知识、经验是关键基础
	* 数据是依据
	* 工具是应用知识处理数据的手段
	* 数据：运行日志、异常堆栈、GC日志、线程快照(threaddump/javacore)、堆转存储快照(headdump/hropf文件)
	
## JDK的命令行工具
* jps:虚拟机进程状况工具 (JVM Process Status Tool)
	命令格式：jps  options [hostid]
* jstat:虚拟机统计信息监视工具 (JVM Statistics Monitoring Tool)
	* 显示虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数据
	* 命令格式：jstat options 
	* 示例：每300毫秒查询一次进程13829垃圾收集状况，一共查询20次>jstat -gc 13829 300 20
	* 选项： -class -gc -gcutil -gcnew -gcold
* jinfo:Java配置信息工具 (Configuration info for java)
	* 格式：jinfo[options] pid
	* 示例：jinfo 7113
* jmap:Java内存映像工具 (Memory Map for Java)
	* 获取dump信息、查询finalize执行队列、Java堆、永久代信息
	* 选项：-dump -heap
	* 示例：jmap -dump:format=b,file=elipse.bin 7113
	* 生成的文件可以是mat工具分析
* jhat:虚拟机堆转储快照分析工具 (JVM Heap Analysis Tool) 不推荐使用
* jstack:Java堆栈跟踪工具 (Stack Trace for Java)
	* 生成当前时刻的线程快照，目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待
	*　格式：jstack [option] vmid
	* 查看线程堆栈信息 jstack -l 3500
	* Thread#getAllStackTraces获取服务器线程信息
* HSDIS:JIT生成代码反汇编

## JDK的可视化工具
* jconsole:Java监视与管理控制台(Java Monitoring and Console)
	* 基于JMX的可视化监视、管理工具，管理部分功能针对JMX MBean
	* 启动命令 jconsole
	* 概述：显示堆内存使用情况，线程，类，CPU使用情况4种信息的曲线图
	* 内存：可视化jstat命令，监视虚拟机内存(Java堆和永久代)的变化趋势
	* 运行如下代码，查看内存曲线变化
```
/**
 * VMargs: -Xms100m -Xmx100m -XX:+UseSerialGC
 * 
 * 代码以64k/50毫秒的速度往Java堆中填充数据，使用jconsole的内存页签进行监视，观察曲线和柱状指示图的变化
 *
 */
package com.leaf.u_jvm;

import java.util.ArrayList;
import java.util.List;

public class OOMDemo {

	static class OOMObject{
		public byte[] placeholder = new byte[64 * 1024];
	}
	
	public static void fillHeap(int num) throws InterruptedException{
		List<OOMObject> list = new ArrayList<OOMDemo.OOMObject>();
		for(int i = 0; i<num; i++){
			//稍作延时，令监视曲线的变化更加明显
			Thread.sleep(50);
			
			list.add(new OOMObject());
		}
		System.gc();
	}
	
	public static void main(String[] args) throws InterruptedException {
		fillHeap(1000);
	}
	
}
```
	* 线程监控，可视化jstack命令
		* 线程长时间停顿的主要原因：等待外部资源（数据库连接、网络资源、设备资源）、死循环、锁等待（活锁和死锁）
```
package com.leaf.u_jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * 线程等待演示
 *
 */
public class ThreadWaitDemo {

	/**
	 * 线程死锁循环
	 */
	public static void createBusyThread(){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				//观察会发现线程一直在执行空循环
				//线程处于RUNNABLE状态，没有归还执行令牌的动作，这种等待会消耗很多的CPU资源
				while(true){}
			}
		}, "testBusyThread");
		
		thread.start();
	}
	
	/**
	 * 线程等待演示
	 */
	public static void createLockThread(final Object lock){
		Thread thread = new Thread(new Runnable(){
			
			@Override
			public void run(){
				synchronized(lock){
					try {
						//等待lock对象的notify或notifyAll方法的出现，线程这时处于WAITING状态
						//在被唤醒前不会被分配执行时间
						//属于正常的活锁状态，可以被唤醒
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "testLockThread");
		
		thread.start();
	}
	
	/**
	 * 线程死锁等待演示
	 *
	 */
	static class SynAddRunnable implements Runnable {
		int a,b;
		
		public SynAddRunnable(int a, int b){
			this.a = a;
			this.b = b;
		}
		
		@Override
		public void run(){
			
			/**
			 * 造成死锁的原因是Integer.valueOf()方法基于减少对象创建次数和节省内存的考虑
			 * [-128, 127]之间的数字会被缓存，当valueOf()方法传入参数在这个范围之内，将直接
			 * 返回缓存中的对象。
			 * 
			 * 示例：代码调用200次Integer.valueOf()方法一共就只返回了两个不同的对象，假如在
			 * 某个线程的两个synchronized块之间发生了一次线程切换，就会出现线程A等着被线程B持有的
			 * Integer.valueOf(1)，线程B又等待着被线程A持有的Integer.valueOf(2)
			 * jsconsole页面可以检测到死锁
			 * 
			 */
			
			synchronized(Integer.valueOf(a)){
				synchronized(Integer.valueOf(b)){
					System.out.println(a + b);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		//运行后，执行jconsole命令，切换到线程页签查看main详情
		
		//等待System.in键盘输入，RUNNABLE状态的线程被分配运行时间，
		//但readBytes方法检查到流没有更新时会立刻归还执行令牌，这种等待只消耗很小的CPU资源
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		
//		br.readLine();
//		createBusyThread();
//		
//		br.readLine();
//		Object obj = new Object();
//		createLockThread(obj);
		
		
		
		//死锁测试
		for (int i=0;i<100;i++){
			new Thread(new SynAddRunnable(1,2)).start();
			new Thread(new SynAddRunnable(2,1)).start();
		}
		
	}
}
```
* VisualVM:多合一故障处理工具
	* VisualVM(ALL-in-One Java Troubleshooting Tool) 
	* https://www.ibm.com/developerworks/cn/java/j-lo-visualvm/


