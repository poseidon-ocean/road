# Maven插件基本实现原理
* Maven的运行机制
	* 编译 maven-compiler-plugin
	* 测试 maven-surefire-plugin
	* 打包 maven-jar/war/source-plugin
	* 部署 maven-deploy-plugin
* Maven插件的工作机制
	* 所有Maven插件类都继承org.apache.maven.plugin.AbstractMojo类，实现execute方法
	* 通过pom.xml配置文件传参，实现相应的逻辑
	* 用户输入mvn命令时获取参数，并执行相应的逻辑

