# Maven实战-坐标规划

## 坐标是什么？为什么要规划？
* 坐标是Maven最基本的概念，如同每个构件的身份证号码
* 简单配置：junit:junit:4.12
	* 第一个junit是groupId
	* 第二个junit是artifactId
	* 4.12是version
* Maven的很多其它核心机制都依赖于坐标，其中最显著的是仓库和依赖管理
* junit:junit:4.12存储构件的位置
	* 对应仓库路径：/junit/junit/4.12/junit-4.12.pom和/junit/junit/4.12/junit-4.12.jar
	* 可以访问中央仓库或者浏览本地仓库目录~/.m2/repository/查看上面的结构
	* 依赖的配置也是完全基于坐标的
```
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.12</version>
  <scope>test</scope>
</dependency>
```
	* 值为test的scope是用来控制该依赖只在测试时可用

## 坐标规划的原则
* groupId是用来定义项目
* artifactId是用来定义模块
* version是用来定义版本：<主版本>.<次版本>.<增量版本>-<限定符>
	* 主版本表示大型架构变更
	* 次版本表示特性的增加
	* 增量版本服务于bug修复
	* 限定符如alpha、beta等表示里程碑

## Classifier
* Classifier可能是最容易被忽略的Maven特性
	设想这样一个情况，有一个jar项目，就说是 dog-cli-1.0.jar 吧，运行它用户就能在命令行上画一只小狗出来。现在用户的要求是希望你能提供一个zip包，里面不仅包含这个可运行的jar，还得包含源代码和文档，换句话说，这是比较正式的分发包。这个文件名应该是怎样的呢？dog-cli-1.0.zip？不够清楚，仅仅从扩展名很难分辨什么是Maven默认生成的构件，什么是额外配置生成分发包。如果能是dog-cli-1.0-dist.zip就最好了。这里的dist就是classifier，默认Maven只生成一个构件，我们称之为主构件，那当我们希望Maven生成其他附属构件的时候，就能用上classifier。常见的classifier还有如dog-cli-1.0-sources.jar表示源码包，dog-cli-1.0-javadoc.jar表示JavaDoc包等等。


引：http://www.infoq.com/cn/news/2010/12/xxb-maven-1?utm_source=news_about_maven-practice&utm_medium=link&utm_campaign=maven-practice











