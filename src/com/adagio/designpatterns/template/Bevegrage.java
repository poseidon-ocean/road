package com.adagio.designpatterns.template;

/**
 * 
 * 模版模式 
 * JdbcTemplate
 * 
 * 冲饮料(拿出去卖钱了)
 * 
 * 执行流程固定，但中间有些步骤有细微的差别（运行时确定）
 * 流程标准化，原料自己加
 *
 */
public abstract class Bevegrage {
	
	//不能被重写
	public final void create(){
		//1、把水烧开
		boilWater();
		//2、把杯子准备好、原材料放到杯中
		pourInCup();
		//3、用水冲泡
		brew();
		//4、添加辅料
		addCoundiments();
	}
	
	public abstract void pourInCup();
	
	public abstract void addCoundiments();
	
	
	public void brew(){
		System.out.println("将开水放入杯中进行冲泡");
	};
	
	public void boilWater(){
		System.out.println("烧开水，烧到100度可以起锅了");
	}
	
}
