# Maven实战-POM重构之增与删
* 重构(名词)：对软件内部结构的一种调整，目的是在不改变软件之可察行为前提下，提高其可理解性，降低其修改成本
* 重构(动词)：使用一系列重构准则(手法)，在不改变软件之可察行为前提下，调整其结构
* 重构能够改善软件设计，使代码更易读，更容易找出bug，并帮助你更快速地编码
* 对POM重构，在保持构建成功的前提下，简化POM内容，使其更简洁易懂

## 前提
* 如果没有单元测试为前提，对代码进行重构是非常危险的
* 同样，在重构POM之前，项目应该有足够的自动化测试保证POM重构不会破坏构建
* 重构POM的时候可能删除或添加依赖，造成依赖版本变化，依赖范围变化，插件版本变化等
	* 这些变化可能会导致项目编译失败，或者测试失败
	* 在自动化测试及构建的基础上，最好能够有持续集成环境，以保证POM的修改可能造成的问题能够及时的被发现和修复
	
## 增还是删
* POM中存在一些依赖或插件配置，但实际代码没有用到，应尽早删掉以免给人带来困惑
* 可以删掉一些POM的元素，如与父模块同样的groupId和version
```
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.juvenxu.sample</groupId>
    <artifactId>sample-parent</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </parent>
  <artifactId>sample-foo</artifactId>
  <packaging>jar</packaging>
...
</project>
```
* 注意：artifactId是不能被删除的，因为该元素不能也不应该被继承，父子模块应当使用不同的artifactId的值
* 需要在POM中增加一些XML元素，目的是为了让POM更清晰易读，且保证Maven构建的稳定性
```
<plugin>
  <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
    <source>1.5</source>
    <target>1.5</target>
  </configuration>
</plugin>
```
* 虽然没有groupId及version，但这段配置是完全合法的
	* 没有groupId，自动使用org.apache.maven.plugins
	* 没有version，自动使用最新版
* 虽然可以简化，但是容易让人困惑，沟通成本增加了
```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>2.3.2</version>
  <configuration>
    <source>1.5</source>
    <target>1.5</target>
  </configuration>
</plugin>
```
* 基于类似的原因，在配置项目依赖的时候，我们应当显示地写明依赖，避免不同时刻引入不同版本的依赖导致项目构建的不稳定
* Maven提供了Maven Dependency Plugin来帮助分析项目中哪些依赖配置应该删除或增加
* 运行命令：mvn dependency:analyze 
```
[INFO] --- maven-dependency-plugin:2.1:analyze (default-cli) @ sample-bar ---
[WARNING] Used undeclared dependencies found:
[WARNING]    org.springframework:spring-context:jar:2.5.6:compile
[WARNING] Unused declared dependencies found:
[WARNING]    org.springframework:spring-core:jar:2.5.6:compile
[WARNING]    org.springframework:spring-beans:jar:2.5.6:compile
[INFO] ------------------------------------------------------------------------
```
* Used undeclared dependencies是指哪些在项目中直接使用的，但没有在POM中配置的依赖
* 该例中可能项目中的一些类有关于spring-context的java import声明，但spring-context这个依赖实际是通过传递性依赖进入classpath的
* dependency:analyze只分析编译主代码和测试代码使用的依赖
* 还有一些POM内容，不影响项目构建，但能方便信息的沟通
	* 项目的URL
	* 开发者信息
	* SCM信息
	* 持续集成服务器信息
```
<project>
  <description>...</description>
  <url>...</url>
  <licenses>...</licenses>
  <organization>...</organization>
  <developers>...</developers>
  <issueManagement>...</issueManagement>
  <ciManagement>...</ciManagement>
  <mailingLists>...</mailingLists>
  <scm>...</scm>
</project>
```

## 小结
* 无论是对POM内容进行增还是删，其目的都是一样的，就是为了让POM更清晰易懂且让构建更稳定
* 需要谨记的是，重构的前提是完善的自动化测试和持续集成

引：http://www.infoq.com/cn/news/2010/12/xxb-maven-2-pom?utm_source=news_about_maven-practice&utm_medium=link&utm_campaign=maven-practice

















