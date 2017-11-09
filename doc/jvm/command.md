# 命令大全

## mvn
* maven打jar包：
	* 配置pom文件：<packaging>jar</packaging>  
	* 直接打包：mvn clean package
	* 排除测试代码后进行打包：mvn clean package  -Dmaven.test.skip=true
* 启动Maven打的jar包
	* 启动jar包命令：java -jar  target/spring-boot-scheduler-1.0.0.jar，关闭控制台服务不能访问
	* 后台运行启动：nohup java -jar target/spring-boot-scheduler-1.0.0.jar &
	* 启动读取不同配置：java -jar app.jar --spring.profiles.active=dev
* Maven打war包
	* 配置pom文件：<packaging>war</packaging>
	* 打包时排除tomcat，scope属性设置为provided
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```
	* 执行命令：mvn clean package  -Dmaven.test.skip=true
	* 会在target目录下生成：项目名+版本号.war文件，拷贝到tomcat服务器中启动即可
* 如何重启
	* 简单粗暴
		* ps -ef|grep java
		* kill -9 pid
		* Java -jar  xxxx.jar
	* 脚本执行
```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
```
		* 可以直接./yourapp.jar 来启动
		* 注册为服务，做一个软链接 
			* ln -s /var/yourapp/yourapp.jar /etc/init.d/yourapp
			* chmod +x /etc/init.d/yourapp
			* /etc/init.d/yourapp start|stop|restart
			* service yourapp start|stop|restart
		

## 生产运维
* 查看JVM参数的值：jinfo -flags pid
	* -XX:CICompilerCount ：最大的并行编译数
	* -XX:InitialHeapSize 和 -XX:MaxHeapSize ：指定JVM的初始和最大堆内存大小
	* -XX:MaxNewSize ： JVM堆区域新生代内存的最大可分配大小
	* -XX:+UseParallelGC ：垃圾回收使用Parallel收集器	
* 
	
引：http://www.cnblogs.com/ityouknow/p/6834287.html

