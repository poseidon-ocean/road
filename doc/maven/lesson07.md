# Maven实战 - 常用Maven插件介绍

## 一
* Maven的本质是一个插件框架
* Maven的核心并不执行任何具体的构建任务，所有任务都交给插件来完成
* 每个任务对应一个插件目标(goal)，每个插件会有一个或者多个目标
	* maven-compiler-plugin的compile目标用来编译位于src/main/java目录下的主源码
	* maven-compiler-plugin的testCompile目标用来编译位于src/test/java目录下的测试代码
* 用户可以通过两种方式调用Maven插件目标
	* 第一种方式是将插件目标与生命阶段绑定(lifecyclephase)，这样用户在命令行只是输入生命周期阶段而已
		* 例如Maven默认将maven-compiler-plugin的compile目标与compile生命周期阶段绑定
		* 因此命令mvn compile实际上是先定位到compile这一生命周期阶段
		* 然后再根据绑定关系调用maven-compiler-plugin的compile目标
	* 第二种方式是直接在命令行指定要执行的插件目标
		* 例如mvn archetype:generate就是表示调用maven-archetype-plugin的generate目标
		* 这种带冒号的调用方式与生命周期无关

## 二
* Maven插件的基本概念能帮助你理解Maven的工作机制，不过了解常用插件可以避免重复发明轮子及更高效使用Maven
* Maven官方有两个插件列表
	* 第一个列表的GroupId为org.apahce.maven.plugins，这里插件最成熟
		* 地址：http://maven.apache.org/plugins/index.html
	* 第二个列表的GroupId为org.codehaus.mojo，这里插件没有那么核心，但也有不少十分有用
		* 地址：http://mojo.codehaus.org/plugins.html
* maven-antrun-plugin
	* 地址：http://maven.apache.org/plugins/maven-antrun-plugin/
	* maven-antrun-plugin能让用户在Maven项目中运行Ant目标
	* 适用Ant迁移Maven
	* 编写的自定义很高的任务又觉得Maven不够灵活，可以以Ant的方式实现之
* maven-archetype-plugin
	* 地址：http://maven.apache.org/archetype/maven-archetype-plugin/
	* Archetype指项目的骨架，mvn archetype:generate命令，生成一个简单的项目骨架
	* 可以自定义项目原型，提供一个Archetype给其它方进行二次开发
* maven-assembly-plugin
	* 地址：http://maven.apache.org/plugins/maven-assembly-plugin/
	* 用途是制作项目分发包，该分发包有可执行文件、源代码、readme、平台脚本等待
	* 支持各种主流格式：zip、tar.gz、jar、war等
	* 具体打包哪些文件是可控的：按文件级别的粒度、文件集别的粒度、模块级别的粒度、依赖级别的粒度
	* 支持包含和排除配置
	* 要求使用一个名为assembly.xml的元数据文件来表述打包
* maven-dependency-plugin
	* 地址：http://maven.apache.org/plugins/maven-dependency-plugin/
	* 用途是帮助分析项目依赖
	* dependency:list能够列出项目最终解析到的依赖列表
	* dependency:tree能进一步的描绘项目依赖树
	* dependency:analyze可以告诉你项目依赖潜在的问题：如告警使用到却没有声明的依赖
	* dependency:copy-dependencies能将项目依赖从本地仓库复制到某个特定文件目录下
	* ...
* maven-enforcer-plugin
	* 地址：http://maven.apache.org/plugins/maven-enforcer-plugin/
	* 在父POM中配置规则：设定Java版本、Maven版本、禁止某些依赖、禁止SNAPSHOT依赖等
	* 可以扩展该插件，编写自己的规则
	* 负责检查规则，默认绑定到生命周期的validate阶段
* maven-help-pkugin
	* 地址：http://maven.apache.org/plugins/maven-help-plugin/
	* help:system可以打印所有可用的环境变量和Java系统属性
	* help:effective-pom打印项目有效POM(指合并了所有父POM后的文件)
	* help:effetive-settings打印有效settings
	* ...
* maven-release-plugin
	* 地址：http://maven.apache.org/plugins/maven-release-plugin/
	* 用途是帮助自动化项目版本发布，依赖POM中的SCM信息
	* release:prepare用来准备笨笨发布
		* 具体工作包括检查是否有未提交代码
		* 检查是否有SNAPSHOT依赖
		* 升级项目的SNAPSHOT版本至RELEASE版本
		* 为项目打标签等
	* release:perform是签出标签中的RELEASE源码，构建并发布
		* 版本发布是非常琐碎的工作，涉及了各种检查
		* 由于工作是偶尔需要，手动操作很容易遗漏一些细节
		* maven-release-plugin让该工作变得非常快速简便，不易出错

## 三
* Maven的本质是一个插件框架，它的核心并不执行任何具体的构建任务，所有任务都交给插件来完成
* maven-resources-plugin
	* 地址：http://maven.apache.org/plugins/maven-resources-plugin/
	* 为了使项目结构更清晰，Maven区别对待Java代码文件和资源文件
	* maven-compiler-plugin用来编译Java代码
	* maven-resources-plugin用来处理资源文件
	* 资源文件过滤是Maven的一大特性，可以配置maven-resource-plugin开启对资源文件的过滤
* maven-surefire-plugin
	* 地址：http://maven.apache.org/plugins/maven-surefire-plugin/
	* 用于执行测试才插件
	* mvn test -Dtest=FooTest命令运行FooTest测试类
* build-helper-maven-plugin
	* 地址：http://mojo.codehaus.org/build-helper-maven-plugin/
	* 能够指定多个源码目录，src/main/java之外的目录，不推荐使用
	* 目标attach-artifact，可以以classifier的形式选取部分项目文件上传附属构件，同时install到本地仓库，也可以deploy到远程仓库
* exec-maven-plugin
	* 地址：http://mojo.codehaus.org/exec-maven-plugin/
	* 能让你运行任何本地的系统程序
	* mvn exec:java命令运行查看运行效果
* jetty-maven-plugin
	* 地址：http://wiki.eclipse.org/Jetty/Feature/Jetty_Maven_Plugin
	* 执行命令mvn jetty:run，在IDE中修改代码，代码经IDE自动编译后产生变更，再由jetty-maven-plugin侦测更新到jetty容器
* version-maven-plugin
	* 地址：http://mojo.codehaus.org/versions-maven-plugin/
	* 用途 统一版本更新升级


引：http://www.infoq.com/cn/news/2011/04/xxb-maven-7-plugin?utm_source=news_about_maven-practice&utm_medium=link&utm_campaign=maven-practice
