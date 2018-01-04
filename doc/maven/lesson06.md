# Maven实战-Gradle，构建工具的未来

## Gradle初体验
* 下载地址：http://services.gradle.org/distributions/
* 安装：解压ZIP包，设置GRADLE_HOME环境变量并将 GRADLE_HOME/bin加到PATH环境变量中
* 运行 gradle -v命令验证安装
* 安装与Maven类似
* 文档地址：https://gradle.org/docs/

## 依赖管理和集成Maven仓库
* 依赖管理、仓库和约定优于配置等概念是Maven的核心内容
* Maven配置
```
 <properties>
        <kaptcha.version>2.3</kaptcha.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.google.code.kaptcha</groupId>
            <artifactId>kaptcha</artifactId>
            <version>${kaptcha.version}</version>
            <classifier>jdk15</classifier>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
    </dependencies>
```
* 换成Gradle脚本
```
dependencies {
    compile('org.springframework:spring-core:2.5.6')
    compile('org.springframework:spring-beans:2.5.6')
    compile('org.springframework:spring-context:2.5.6')
    compile('com.google.code.kaptcha:kaptcha:2.3:jdk15')
    testCompile('junit:junit:4.7')
}
```
	较之于Maven或者Ant的XML配置脚本，Gradle使用的Grovvy脚本杀伤力太大了，爱美之心，人皆有之，相比于七旬老妇松松垮垮的皱纹，大家肯定都喜欢少女紧致的脸蛋，XML就是那老妇的皱纹。
* 自动化依赖管理的基石是仓库
```
repositories {
    mavenLocal()
    mavenCentral()
    mavenRepo urls: "http://repository.sonatype.org/content/groups/forge/"
}
```
* 这段代码就是Gradle中配置使用Maven本地仓库、中央仓库、以及自定义仓库

## 约定优于配置
* 通过Gradle的Java Plugin实现，定义了与Maven完全一致的项目布局
	* src/main/java
	* src/main/resources
	* src/test/java
	* src/test/resources
* 区别：使用Groovy自定义布局更加的方便
```
sourceSets {
    main {
        java {
            srcDir 'src/java'
        }
        resources {
            srcDir 'src/resources'
        }
    }
}
```
* Gradle Java Plugin也定义了构建生命周期：编译主代码、处理资源、编译测试代码、执行测试、上传归档等
 






> 与其跟狗争辩，被它咬一口，倒不如让它先走。否则就算宰了它，也治不好你被咬的伤疤。 - 林肯



















