# Maven实战-打包的技巧
* 打包
	* 高大上的说法：构建项目软件包
	* 具体说：将项目中的各种文件，比如源代码、编译生成的字节码、配置文件、文档，按照规范的格式生产归档

## Packaging的含义
* 任何一个Maven项目都需要定义POM元素packaging，不写默认jar
* 该元素决定了项目的打包方式
	* 不声明该元素或者定义为jar，Maven会帮你生成一个JAR包
	* 如果定义该元素的值为war，你会得到一个WAR包
	* 如果定义其值为POM，比如父模块，什么包都不会打
	* ...
* 不需要了解打包细节，只需告诉Maven是个什么类型的项目，这个就是约定优于配置的力量
* packaging直接影响Maven的构建生命周期，这一点非常重要
	* 特别是当你需要自定义打包行为的时候，你就必须知道去配置哪个插件
	* 一个常见例子，打war项目排除某些web资源文件
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>2.1.1</version>
    <configuration>
      <webResources>
        <resource>
          <directory>src/main/webapp</directory>
          <excludes>
            <exclude>**/*.jpg</exclude>
          </excludes>
        </resource>
      </webResources>
    </configuration>
  </plugin>
```

## 源码包和Javadoc包
* 一个Maven项目只生成一个主构件，当需要生成附属构件时，需用上classifier
* 附属构件 源码包
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-source-plugin</artifactId>
    <version>2.1.2</version>
    <executions>
      <execution>
        <id>attach-sources</id>
        <phase>verify</phase>
        <goals>
          <goal>jar-no-fork</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
```
* 附属构件 Javadoc包
```
<plugin>          
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>2.7</version>
    <executions>
      <execution>
        <id>attach-javadocs</id>
          <goals>
            <goal>jar</goal>
          </goals>
      </execution>
    </executions>
  </plugin>    
```
* 为了帮助所有Maven用户更方便的使用Maven中央仓库中海量的资源，强制要求开源项目同时提供源码包和Javadoc包

## 可执行CLI包
* 在命令行可直接运行的CLI(Command Line)包
* 一个可以直接在命令行通过java命令运行的JAR文件，需满足两个条件
	* JAR包中的/META-INF/MANIFEST.MF元数据文件必须包含Main-Class信息
	* 项目所有的依赖都必须在Classpath中
* maven-shade-plugin，可以让用户配置Main-Class值，然后在打包时将值填入/META-INF/MANIFEST.MF文件
```
 <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>1.4</version>
    <executions>
      <execution>
        <phase>package</phase>
        <goals>
          <goal>shade</goal>
        </goals>
        <configuration>
          <transformers>
            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
              <mainClass>com.juvenxu.mavenbook.HelloWorldCli</mainClass>
            </transformer>
          </transformers>
        </configuration>
      </execution>
    </executions>
  </plugin>
```
* 构建完成后：
	* 得到一个常规hello-world-1.0.jar文件，一个hello-world-1.0-cli.jar文件
	* 命令运行java -jar hello-world-1.0-cli.jar

## 自定义格式包
* 实际的软件项目常常会有更复杂的打包需求
	* 如给客户提供一份产品的分发包
	* 这个包包含项目的字节码文件
	* 这个包依赖以及相关脚本文件方便客户解压后运行
	* 还应包含一些必要的文档
	* 这时项目的源码目录结构大致如下：
	```
	pom.xml
	src/main/java/
	src/main/resources/
	src/test/java/
	src/test/resources/
	src/main/scripts/
	src/main/assembly/
	README.txt
	```
	* 目录src/main/scripts/包含一些脚本文件如run.sh和run.bat
	* 目录src/main/assembly/会包含一个assembly.xml文件，这是打包的描述文件
	* README.txt是份简单的文档
* 生成一个zip格式的分发包
	```
	bin/
	lib/
	README.txt
	```
	* 其中bin/目录包含了可执行脚本run.sh和run.bat
	* lib/目录包含了项目JAR包和所有依赖JAR
	* README.txt就是前面提到的文档
* Maven最强大的打包插件：maven-assembly-plugin
	* 支持各种打包文件格式，包括zip、tar.gz、tar.bz2等
	* 通过一个打包描述文件（该例中是src/main/assembly.xml），它能够帮助用户选择具体打包哪些文件集合、依赖、模块、和甚至本地仓库文件
	* 每个项的具体打包路径用户也能自由控制
	* 打包描述文件src/main/assembly.xml：
	```
	<assembly>
	  <id>bin</id>
	  <formats>
	    <format>zip</format>
	  </formats>
	  <dependencySets>
	    <dependencySet>
	      <useProjectArtifact>true</useProjectArtifact>
	      <outputDirectory>lib</outputDirectory>
	    </dependencySet>
	  </dependencySets>
	  <fileSets>
	    <fileSet>
	      <outputDirectory>/</outputDirectory>
	      <includes>
	        <include>README.txt</include>
	      </includes>
	    </fileSet>
	    <fileSet>
	      <directory>src/main/scripts</directory>
	      <outputDirectory>/bin</outputDirectory>
	      <includes>
	        <include>run.sh</include>
	        <include>run.bat</include>
	      </includes>
	    </fileSet>
	  </fileSets>
	</assembly>
	```
		* 首先这个assembly.xml文件的id对应了其最终生成文件的classifier。
		* 其次formats定义打包生成的文件格式，这里是zip。因此结合id我们会得到一个名为hello-world-1.0-bin.zip的文件。（假设artifactId为hello-world，version为1.0）
		* dependencySets用来定义选择依赖并定义最终打包到什么目录，这里我们声明的一个depenencySet默认包含所有所有依赖，而useProjectArtifact表示将项目本身生成的构件也包含在内，最终打包至输出包内的lib路径下（由outputDirectory指定）。
		* fileSets允许用户通过文件或目录的粒度来控制打包。这里的第一个fileSet打包README.txt文件至包的根目录下，第二个fileSet则将src/main/scripts下的run.sh和run.bat文件打包至输出包的bin目录下
* 配置maven-assembly-plugin使用打包描述文件，并绑定生命周期阶段使其自动执行打包操作：
```
 <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>2.2.1</version>
    <configuration>
      <descriptors>
        <descriptor>src/main/assembly/assembly.xml</descriptor>
      </descriptors>
    </configuration>
    <executions>
      <execution>
        <id>make-assembly</id>
        <phase>package</phase>
        <goals>
          <goal>single</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
```
* 运行mvn clean package之后，我们就能在target/目录下得到名为hello-world-1.0-bin.zip的分发包了





引：http://www.infoq.com/cn/news/2011/06/xxb-maven-9-package?utm_source=news_about_maven-practice&utm_medium=link&utm_campaign=maven-practice


