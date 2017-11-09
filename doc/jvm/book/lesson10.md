# 早期(编译期)优化
* 从计算机程序出现的第一天起，对效率的追求就是程序员天生的坚定信仰

## javac编译器
* 分析源码是了解一项技术实现内幕最有效的手段
* 编译过程：
	* 解析与填充符号表过程
		* 解析是词法分析和语法分析
		* 符号表是由一组符号地址和符号信息构成的表格
	* 插入式注解处理器的注解处理过程
	* 分析与字节码生成过程
* 注解处理器：JavacProcessingEnviroment#doProcessing()

## Java语法糖的味道
* 泛型和类型檫除
* 自动装箱、拆箱与遍历循环
















