# 手写Maven插件自动生成RestFul风格的接口文档

## 时下流行的API生成工具
* 1、Swagger
	* 可以通过代码生成漂亮的在线API，甚至可以提供运行示例。
	* 支持Scala、Java、JavaScript、Ruby、PHP，甚至ActionScript3
* I/O Docs
	* 是一个用于RESTful Web APIs的交互式文档系统。
	* 使用JSON模型根据资源、方法和参数定义APIs。
	* I/O Docs将生成JavaScript客户端接口，可通过这些接口来调用系统
	*　服务器端甚至基于Node.js开发
* Dexy
	* 非常灵活的一款文档工具，支持任何语言编写的API
	
## 为什么要了解手写Maven插件
* 了解Maven插件的工作原理，了解细节，知其然知其所以然
*　深度还原自动生成ＡＰＩ全过程，别人能做的事我也能做

## Maven插件基本实现原理
* 插件的使用是自己配置还是copy
* Maven的运行机制
	* 编译 - maven-compiler-plugin
	* 测试 - maven-surefire-plugin
	* 打包 - maven-jar/war/source-plugin
	* 部署 - maven-deploy-plugin
* Maven插件的工作机制
	* 所有Maven插件类都继承org.apache.maven.plugin.AbstractMojo类，实现execute方法
	* 通过pom.xml配置文件传参，实现相应的逻辑
	* 用户输入mvn命令时获取参数，并执行相应的逻辑

## 代码演示
* 创建三个maven项目，分别
	* core-mommon-doc 公共项目，定义通用的注解
	* core-maven-plugin-doc Maven插件项目，实现自动生成API的逻辑
	* gupaoedu-maven-plugin-doc-demo 应用自定义插件的demo项目
* core-maven-plugin-doc要引入的jar包
```
maven-plugin-api
maven-model
```
* AbstractMojo配置
	* public class DocPlugin extends AbstractMojo{}









