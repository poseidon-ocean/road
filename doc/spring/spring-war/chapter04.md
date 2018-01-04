# 面向切面的Spring
* 面向切面编程的基本原理
* 通过POJO创建切面
* 使用@AspectJ注解
* 为AspectJ切面注入依赖

## 定义AOP术语
* 通知(Advice)
	* 前置通知(Before)：在目标方法被调用之前调用通知功能
	* 后置通知(After)：在目标方法完成之后调用通知，此时不会关心方法的输出是什么
	* 返回通知(After-returning)：在目标方法成功执行之后调用通知
	* 异常通知(After-throwing)：在目标方法抛出异常后调用通知
	* 环绕通知(Around)：通知包裹了被通知的方法，在被通知的方法调用之前和调用之后执行自定义的行为
* 连接点(Join point)，是在应用执行过程中能够插入切面的一个点
* 切点(Poincut)，利用正则表达式定义所匹配的类和方法名称来指定切点
* 切面(Aspect)，通知和切点的结合，它是什么，在何时何处完成其功能
* 引入(Introduction)：允许向现有的类添加新方法或属性，不影响现有的类
* 织入(Weaving)：
	* 把切面应用到目标对象并创建新的代理对象的过程
	* 在指定的连接点被织入到目标对象中
	* 在目标对象的生命周期里有多个点可以进行织入：
		* 编译期：切面在目标类编译时被织入，如AspectJ
		* 类加载期：切面在目标类加载到JVM时被织入，如AspectJ 5的加载时织入(load-time weaving,LTW)
		* 运行期：切面在应用运行的某个时刻被织入，如Spring AOP
		
## Spring对AOP的支持
* 基于代理的经典Spring AOP
* 纯POJO切面
* @AspectJ注解驱动的切面
* 注入式AspectJ切面(适用于Spring各版本)
```
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

/**
 * 是一个切面，没有提供前置、后置或环绕通知，而是使用了@DeclareParents注解
 * @DeclareParents注解详解：
 * value属性指定了哪种类型的bean要引入的接口，"+"表示Performance的所有子类型
 * defaultImpl属性指定了引入功能提供实现的类
 * @DeclareParents注解所标注的静态属性指明了要引入了接口
 * 
 * 在Spring应用中把EncoreableIntroducer声明为一个bean，当Spring发现@Aspect注解时会创建一个代理，然后将调用委托给被代理的bean或被引入的实现
 * 
 */
@Aspect
public class EncoreableIntroducer {

	@DeclareParents(value="com.leaf.u_spring.chapter04.Performance+",
			defaultImpl=DefaultEncoreable.class)
	public static Encoreable encoreable;
	
	
	
}
```
	
## 通过切点来选择连接点
* AspectJ的切点表达式语言
	* AspectJ指示器和描述
	* arg(),限制连接点匹配参数为指定类型的执行方法
	* @args(),限制连接点匹配参数由指定注解标注的执行方法
	* execution(),用于匹配是连接点的执行方法
	* this(),限制连接点匹配AOP代理的bean引用为指定类型的类
	* target()，限制连接点匹配目标对象为指定类型的类
	* @target(),限制连接点匹配特定的执行对象，这些对象对应的类要具有指定类型的注解
	* within(),限制连接点匹配指定的类型
	* @within(),限制连接点匹配指定注解所标注的类型(当使用Sping AOP时，方法定义在由指定的注解所标注的类里)
	* @annotation,限定匹配带有指定注解的连接点
* 编写切点 Performance
```
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.google.common.collect.Maps;

/**
 * 使用参数化的通知来记录磁道播放的次数
 */
@Aspect
public class TrackCounter {

	private Map<Integer, Integer> trackCounters = Maps.newHashMap();
	
	/**
	 * 通知playTrack()方法
	 * 
	 * 
	 * playTrack(int)指定int类型参数
	 * && args(trackNumber)指定参数
	 * 
	 * 
	 * @param trackNumber
	 */
	@Pointcut("execution(* com.leaf.u_spring.chapter02.CompactDisc.playTrack(int)) && args(trackNumber)")
	public void trackPlayed(int trackNumber){}
	
	/**
	 * 播放前为磁道计数
	 * @param trackNumber
	 */
	@Before("trackPlayed(trackNumber)")
	public void countTrack(int trackNumber){
		int currentCount = getPlayCount(trackNumber);
		trackCounters.put(trackNumber, currentCount + 1);
	}

	public int getPlayCount(int trackNumber) {
		return trackCounters.containsKey(trackNumber)?trackCounters.get(trackNumber):0;
	}
	
}

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.google.common.collect.Lists;
import com.leaf.u_spring.chapter02.CompactDisc;
import com.leaf.u_spring.chapter03.BlankDisc;


/**
 * 将BlankDisc和TrackCounter定义为bean，并启用AspectJ自动代理
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class TrackCounterConfig {

	@Bean
	public CompactDisc sgtPeppers(){
		BlankDisc cd = new BlankDisc();
		cd.setTitle("阳光总在风雨后");
		cd.setArtist("许美静");
		
		List<String> tracks = Lists.newArrayList();
		tracks.add("阳光总在风雨后");
		tracks.add("成都");
		tracks.add("一生所爱");
		tracks.add("我的中国心");
		tracks.add("Alone Yet Not Alone");
		cd.setTracks(tracks);
		
		return cd;
	}
	
	@Bean
	public TrackCounter trackCounter(){
		return new TrackCounter();
	}
	
}
```

## 使用注解创建切面
* Spring使用AspectJ注解来声明通知方法
	* @After	通知方法会在目标方法返回或抛出异常后调用
	* @AfterReturning		通知方法会在目标方法返回后调用
	* @AfterThrowing		通知方法会在目标方法抛出异常后调用
	* @Around				通知方法会将目标方法封装起来
	* @Before				通知方法会在目标方法调用之前执行
* 环绕通知是最为强大的通知类型
* Java不是动态语言，编译之后很难再添加新的功能，但是引入AOP，切面可以为Spring bean添加新方法，例EncoreableIntroducer
```
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 演出效果 
 * 
 * @Aspect注解表明Audience类不仅仅是个POJO，还是一个切面
 * Audience类中的方法都使用注解来定义切面的具体行为
 */
@Aspect
public class Audience {

	/**
	 * 表演之前
	 */
	@Before("execution(** com.leaf.u_spring.chapter04.Performance.perform(..))")
	public void silenceCellPhones(){
		System.out.println("silence Cell Phones");
	}
	
	/**
	 * 表演之前
	 */
	@Before("execution(** com.leaf.u_spring.chapter04.Performance.perform(..))")
	public void takeSeates(){
		System.out.println("taking seates");
	}
	
	
	/**
	 * 表演之后
	 */
	@AfterReturning("execution(** com.leaf.u_spring.chapter04.Performance.perform(..))")
	public void applause(){
		System.out.println("CLAP CLAP CLAP!!!");
	}
	
	/**
	 * 表演失败之后
	 */
	@AfterThrowing("execution(** com.leaf.u_spring.chapter04.Performance.perform(..))")
	public void demandRefund(){
		System.out.println("Demanding a refund");
	}
	
}

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 演出效果 
 * 相同的切点表达式重复了4遍
 * @Ponitcut注解能够在一个@AspectJ切面内定义可重用的切点
 * 
 */
@Aspect
public class Audience2 {

	/**
	 * 定义命名的切点
	 */
	@Pointcut("execution(** com.leaf.u_spring.chapter04.Performance.perform(..))")
	public void performance(){}
	
	/**
	 * 表演之前
	 */
	@Before("performance())")
	public void silenceCellPhones(){
		System.out.println("silence Cell Phones");
	}
	
	/**
	 * 表演之前
	 */
	@Before("performance())")
	public void takeSeates(){
		System.out.println("taking seates");
	}
	
	
	/**
	 * 表演之后
	 */
	@AfterReturning("performance())")
	public void applause(){
		System.out.println("CLAP CLAP CLAP!!!");
	}
	
	/**
	 * 表演失败之后
	 */
	@AfterThrowing("performance())")
	public void demandRefund(){
		System.out.println("Demanding a refund");
	}
	
}
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 演出效果 
 * 创建环绕通知
 * 
 */
@Aspect
public class Audience3 {

	/**
	 * 定义命名的切点
	 */
	@Pointcut("execution(** com.leaf.u_spring.chapter04.Performance.perform(..))")
	public void performance(){}
	
	/**
	 * 环绕通知方法
	 */
	@Around("performance())")
	public void watchPerformance(ProceedingJoinPoint jp){
		try {
			System.out.println("silence Cell Phones");
			System.out.println("taking seates");
			//不调用proceed方法，通知会阻塞对被通知方法的调用
			jp.proceed();
			System.out.println("CLAP CLAP CLAP!!!");
		} catch (Throwable e) {
			System.out.println("Demanding a refund");
		}
	}
	
}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy			//启用AspectJ自动代理	XMl-->   <aop:aspectj-autoproxy/>
@ComponentScan
public class ConcertConfig {

	/**
	 * 声明Audience bean
	 * @return
	 */
	@Bean
	public Audience audience(){
		return new Audience();
	}
}
```

## 在XML中声明注解
* 原则：基于注解的配置优于基于Java的配置，基于Java的配置要优于基于XML的配置
* 需要声明切面，又不能为通知类添加注解时，须转向XML的配置
* Spring的AOP配置能够以非侵入式的方式声明切面：
	* <aop:advisor>：定义AOP通知器
	* <aop:after>：定义AOP后置通知（不管被通知的方法是否执行成功）
	* <aop:after-returning>：定义AOP返回通知
	* <aop:after-throwing>：定义AOP异常通知
	* <aop:around>：定义AOP环绕通知
	* <aop:aspect>：定义一个切面
	* <aop:aspectj-autoproxy>：启用@AspectJ注解驱动的切面
	* <aop:before>：定义一个AOP前置通知
	* <aop:config>：顶层的AOP配置元素，大多数的<aop:*>元素必须包含在<aop:config>元素内
	* <aop:declare-parents>：以透明的方式为被通知的对象引入额外的接口
	* <aop:pointcut>：定义一个切点
	











