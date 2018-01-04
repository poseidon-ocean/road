# 装配Bean
* 任何一个成功的应用都是由多个为了实现某个业务目标而相互协作的组件构成的
* 创建应用对象之间协作关系的行为通常称为装配(wiring)，这也是依赖注入(DI)

## Spring配置的可选方案
* 在XML中进行显示配置
* 在Java中进行显示配置
* 隐式的bean发现机制和自动装配

## 自动化装配bean
* 组件扫描(component scanning)：Spring会自动发现应用上下文中所创建的bean
* 自动装配(autowiring)：Spring自动满足bean之间的依赖


## 设置组件扫描的基础包
* @ComponentScan：默认扫描当前所在的包
* @ComponentScan("soundsystem")：表明你所设置的是基础包
* @ComponentScan(basePackages="soundsystem")/@ComponentScan(basePackages={"soundsystem", "video"})：更清晰表明设置的基础包
* @ComponentScan(basePackageClasses={CDPlayer.class, DVDPlayer.class})：扫描这些类所在的包
```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动扫描当前包下的@Component
 * 也可以启用 XML <context:component-scan base-package="com.leaf.u_spring.chapter02" />
 */
@Configuration
@ComponentScan
public class CDPlayerConfig {

	/**
	 * 随机选择一个CompactDisc来播放
	 * 这个例子可以看出@Bean的强大功能
	 * 
	 * 还可以指定名称
	 */
	@Bean(name="randomDisc")
	public CompactDisc randomBeatlesCD(){
		int choice = (int) Math.floor(Math.random() * 4);
		if(choice == 0){
			return new SgtPeppers();
		} else if(choice == 1) {
			return null;
		} else if(choice == 2) {
			return null;
		} else {
			return null;
		}
		
	}
	
}
```

## 通过Java代码装配bean
* 显示配置，JavaConfig是更好的方案，因为它更为强大、类型安全并且对重构友好
* 构造器和Setter方法只是@Bean方法的两个简单样例

## 通过XML装配bean
* 需要在配置文件的顶部声明多个XML模式(XSD)文件，这些文件定义了配置Spring的XML元素
* 创建和管理Spring XML配置文件一种简便方式：https://spring.io/tools/sts  STS>File>New>Spring Bean Configuration File
* 声明bean的方式：使用<bean>元素指定class属性
* 借助构造器注入初始化bean：<constructor-arg>元素和使用Spring3.0所引入的c-命名空间
* c:cd-ref="compactDisc"： c(命名空间前缀) cd(构造器参数名) ref(注入bean引用) compactDisc(要注入的bean的ID)
* 强依赖使用构造器注入，可选依赖使用属性注入

## 导入和混合配置
* 自动化和显示配置不是互斥的
* @Import注解可以导入JavaConfig到另一个JavaConifg中，或者在更高级类中把多个JavaConfig组合在一起
* @ImposrtResource注解，可以装配xml到JavaConfig中
* <import>XML导入另一个XML
* bean可以在XML中导入JavaConfig
* 组件扫描 <context:component-scan>或@Componenet
* 装配bean：自动化配置、基于Java的显示配置以及基于XML的显示配置

















