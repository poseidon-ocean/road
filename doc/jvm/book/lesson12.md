# Java内存模型与线程
* 虚拟机如何实现多线程、多线程之间由于共享和竞争数据而导致的一系列问题及解决方案

## 硬件的效率与一致性
* 高速缓存：解决-内存和处理器之间的缓冲，问题-缓存一致性
* 处理器对输入代码进行乱序执行优化，java虚拟机有类似功能-指令重排优化

## Java内存模型
* 目标是定义程序中各个变量的访问规则，即在虚拟机中将变量存储到内存和从内存中取出变量这样的底层细节
* Java内存模型规定：
	* 所有的变量都存储在主内存
	* 每条线程还有自己的工作内存
* 主内存和工作内存间交互：
	* 作用于主内存的变量
		* lock(锁定)：它把一个变量标识为一条线程独占的状态
		* unlock(解锁)：它把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定
		* read(读取)：它把一个变量的值从主内存传输到线程的工作内存中，以便随后的load动作使用
		* write(写入)：它把store操作从工作内存中得到的变量的值放入主内存的变量中
	* 作用于工作内存的变量
		* load(载入)：它把read操作从主内存中得到的变量放入工作内存的变量副本中
		* use(使用)：它把工作内存中一个变量的值传递给执行引擎，每当虚拟机遇到一个需要使用到变量的值的字节码指令时将会执行这个操作
		* assign(赋值)：它把一个从执行引擎接收到的值赋值给工作内存的变量，每当虚拟机遇到一个给变量赋值的字节码指令时执行这个操作
		* store(存储)：它把工作内存中一个变量的值传送到主内存中，以便随后的write操作使用
```
在将变量从主内存读取到工作内存中，必须顺序执行read、load；要将变量从工作内存同步回主内存中，必须顺序执行store、write。并且这8种操作必须遵循以下规则： 
- 1，不允许read和load、store和write操作之一单独出现。即不允许一个变量从主内存被读取了，但是工作内存不接受，或者从工作内存回写了但是主内存不接受。 
- 2，不允许一个线程丢弃它最近的一个assign操作，即变量在工作内存被更改后必须同步改更改回主内存。 
- 3，工作内存中的变量在没有执行过assign操作时，不允许无意义的同步回主内存。 
- 4，在执行use前必须已执行load，在执行store前必须已执行assign。 
- 5，一个变量在同一时刻只允许一个线程对其执行lock操作，一个线程可以对同一个变量执行多次lock，但必须执行相同次数的unlock操作才可解锁。 
- 6，一个线程在lock一个变量的时候，将会清空工作内存中的此变量的值，执行引擎在use前必须重新read和load。 
- 7，线程不允许unlock其他线程的lock操作。并且unlock操作必须是在本线程的lock操作之后。 
- 8，在执行unlock之前，必须首先执行了store和write操作。
```

## 对于volatile型变量的特殊规则
*　Java虚拟机提供的最轻量级的同步机制
* 具备两种特性：
	* 保证变量对所有线程的可见性
		* 可见性指：当一条线程修改了这个变量的值，新值对于其他线程来说可以立即得知的
```
package com.adagio.demo;

/**
 * volatile自增运算演示
 *
 */
public class VolatileDemo {

	public static volatile int race = 0;
	
	private static final int THREADS_COUNT = 20;
	
	public static void increase() {
		race ++;
	}
	
	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_COUNT];
		
		for(int i=0; i<THREADS_COUNT; i++) {
			threads[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					for(int i=0; i<10000; i++) {
						increase();
					}
					
				}
				
			});;
			
			threads[i].start();
			
			while(Thread.activeCount() > 1) {
				Thread.yield();
				System.out.println(race);
			}
		}
	}
	
}
```
* 分析上面这段代码
	* 发起20个线程，每个线程对race变量进行10000次自增操作。若正常并发结果是200000
	* 实际运行结果达不到预期值，一直会小于200000
	* 使用javap反编译下面代码
	* increase()这个方法在执行时
		* 指令getstatic：volatile可以保证可见性的一致
		* 指令iconst_1、iadd：其它线程可能改变了数据
		* 指令putstatic：存入的值可能就是较小的race值
```
 public static void increase();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=0, args_size=0
         0: getstatic     #13                 // Field race:I
         3: iconst_1
         4: iadd
         5: putstatic     #13                 // Field race:I
         8: return
```
* volatile可以禁止指令重排







