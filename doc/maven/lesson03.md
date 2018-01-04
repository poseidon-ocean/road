# Maven实战-多模块项目的POM重构

## 重复，还是重复
* 程序员应该有狗一般的嗅觉，要能嗅到重复这一常见的坏味道
* 不管重复披着怎样的外衣，一旦发现，都应该毫不留情地彻底将其干掉
```
<dependency>
  <groupId>org.springframework</groupId>
  <artifactid>spring-beans</artifactId>
  <version>2.5</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactid>spring-context</artifactId>
  <version>2.5</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactid>spring-core</artifactId>
  <version>2.5</version>
</dependency>
```
* 重复<version>2.5</version>，使用Maven属性将2.5提取出来
* 提取之后2.5之出现在一处，日后升级。之需要修改一处，也能避免漏掉升级某个依赖
* 重点：多模块POM重构的目的和该例一样，也是为了消除重复，模块越多，潜在的重复就越多，重构就越有必要

## 消除多模块依赖配置重复
* Maven提供了继承机制以及dependencyManagement元素，可以统一通用依赖，如Junit、Log4j
* 在父模块配置dependencies，所有的子模块都自动继承
* dependencyManagement只会影响现有依赖的配置，但不会引入依赖，如：
```
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactid>junit</artifactId>
      <version>4.8.2</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>log4j</groupId>
      <artifactid>log4j</artifactId>
      <version>1.2.16</version>
    </dependency>
  </dependencies>
</dependencyManagement>
```
* 这段配置不会给任何子模块引入依赖，但子模块可以简化为：
```
 <dependency>
    <groupId>junit</groupId>
    <artifactid>junit</artifactId>
  </dependency>
  <dependency>
    <groupId>log4j</groupId>
    <artifactid>log4j</artifactId>
  </dependency>
 ```
* 在多模块Maven项目中，dependencyManagement几乎是必不可少的，因为只有它才能有效地帮助我们维护依赖一致性
* Maven的继承和Java的继承一样，是无法实现多重继承的，如果10个、20个甚至更多模块继承自同一个模块
* 父模块的dependencyManagement会包含大量的依赖。如果你想把这些依赖分类以更清晰的管理，那就不可能了，import scope依赖能解决这个问题
 
## 消除多模块插件配置重复
* 使用pluginManagement元素管理插件
* 一个常见的用法就是我们希望项目所有模块的使用Maven Compiler Plugin的时候，都使用Java 1.8，以及指定Java源文件编码为UTF-8
```
<build>
  <pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>2.3.2</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
          <encoding>UTF-8</encoding>
        </configuration>
      </plugin>
    </plugins>
  </pluginManagement>
</build>
```






引：http://www.infoq.com/cn/news/2011/01/xxb-maven-3-pom-refactoring?utm_source=news_about_maven-practice&utm_medium=link&utm_campaign=maven-practice






















