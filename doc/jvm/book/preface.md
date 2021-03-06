# 深入理解Java虚拟机 - JVM高级特性与最佳实践

## 背景
* Java的技术体系主要构成由：
	* 支撑Java程序运行的虚拟机
	* 提供各开发领域接口支持的Java API
	* Java编程语言及许多第三方Java框架(如Spring、Struts等)
* 在虚拟机层面隐藏了底层技术的复杂性以及机器与操作系统的差异性，是Java开发技术的一重要优点
	* Java虚拟机在千差万别的物理机上建立统一的运行平台，程序员可以集中在业务层，而不是物理硬件的兼容性
	* 只要了解Java API、Java语法，以及学习适当的第三方开发框架，已经满足日常开发需要
	* 了解虚拟机的运作并不是一般的程序员必须掌握的知识
*　凡是都具有两面性，随着Java技术的不断发展，被应用于越来越多的领域
	* 如电力、金融、通信等，对程序的性能、稳定性和可扩展性有极高的要求
	* 程序可能在10个人使用正常，10000个人同时使用会缓慢、死锁，甚至崩溃，需要更高性能的物理硬件
	* 但是提升硬件效能无法等比例提升程序的运作性能和并发能力，甚至可能没有任何改善
	* Java虚拟机的原因：为达到所有硬件提供一致的虚拟平台的目的，牺牲一些硬件相关性能特性
	* 人为原因：开发人员不了解虚拟机一些技术特性的运行原理，无法写出最适合虚拟机运行和自优化的代码
* 目前商用的高性能Java虚拟机都提供了相当多的优化特性和调节手段，用于满足应用程序在实际生产环境中对性能和文档性的要求
	* 入门学习，程序在本机运行，这些特性可有可无
	* 生产开发，尤其是企业级生产开发，就迫切需要开发人员中至少有一部分人对虚拟机的特性及调节方法具有很清晰的认识，所以在Java技术体系中，对架构师、系统调优师、高级程序员等角色的需求一直非常大
	* 学习虚拟机中各种自动运作特性的原理，是Java程序员成长道路的一部分

## 导航
* 走近Java：了解Java的来龙去脉
* 自动内存管理机制：
	* 程序员把内存控制的权力交给了Java虚拟机，所以在编码的时候享受自动内存管理的优势
	* 正因为如此，一旦出现内存泄漏和溢出方面的问题，如果不了解虚拟机是怎样使用内存的，那么排查错误将会称为一项异常艰难的工作
	* 虚拟机中内存是如何划分的，以及哪部分区域、什么样的代码和操作可能导致内存溢出异常
* 虚拟机执行子系统
	* 虚拟机中必不可少的组成部分，了解虚拟机如何执行程序，才能写出更优秀的代码
* 程序编译与代码优化
* 高效并发
* 源码：http://www.hzbook.com/ps/













