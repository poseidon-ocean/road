# Maven实战 - 基于Maven的持续集成实践

## Martin的《持续集成》
* 只维护一个源码仓库
* 自动化构建
* 让构建自行测试
* 每人每天向主干提交代码
* 每次提交都应在持续集成机器上构建主干
* 保持快速的构建
* 在模拟生产环境中测试
* 让每个人都能轻易获得最新的可执行文件
* 每个人都能看到进度
* 自动化部署

## 架设私有Maven
* 持续集成的最大好处：降低风险、尽早暴露问题，能让开发人员及早发现并修复，从而降低修复成本
* 私有Maven的仓库：
	* 节省大量从Internet下载时间，只需下载一次后面的都从私服下载
	* 结合自动化部署和Maven的SNAPSHOT机制，能达到促进项目集成的效率
	* 持续集成服务器，每次成功构建一个模块，都应该将该模块的SNAPSHOT版本发布到Maven仓库
		* 本地成功执行mvn clean install并不代表持续集成服务器上该命令能够成功
		* 每人的本地环境各有差异，因此集成的成功与否应当以持续集成服务器为准
	
## 正确的集成命令 mvn clean install
* 不用忘了clean：clean能够保证上一次构建的输出不会影响本次构建
* 使用deploy而不是install：构建的SNAPSHOT输出应当被自动部署到私有Maven仓库供他人使用
* 使用-U参数：该参数能强制让Maven检查所有SNAPSHOT依赖更新，确保集成基于最新的状态
	* 如果没有这个参数，Maven默认以天为单位检查更新，而持续集成的频率应该比这高很多
* 使用-e参数：如果构建出现异常，该参数能让Maven打印完整的stack trace，以方便分析错误原因
* 使用-Dmaven.repo.local参数：
	如果持续集成服务器有很多任务，每个任务都会是使用本地仓库，下载依赖至本地仓库，
为了避免这种多线程使用本地仓库可能引起的冲突，可以使用-Dmaven.repo.local=
/home/juven/ci/foo-repo/这样的参数为每个任务分配本地仓库
* 使用-B参数：
	该参数表示让Maven使用批处理模块构建项目，能够避免一些需要人工参与交互而造成的挂起状态
* 综上：持续集成服务器上的集成命令> mvn clean deploy -B -e -U -Dmaven.repo.local=xxx
* 定期清理持续集成服务器的本地Maven仓库，避免浪费磁盘资源
* 可以写一行简单的shell或bat脚本，然后配置以天为单位自动清理仓库

## 用好Profile
* 不同环境的集成：分阶段构建(staged build)
	* 第一部分包括了编译和单元测试等能够快速结束的任务
	* 第二部分包括集成测试等耗时较长的任务
	* 只有第一部分成功完成后，才触发第二部分集成
	Maven的Profile机制能够很好的支持分阶段构建。例如，借助Maven Surefire Plugin，你可以统一单元测试命名为**UT，统一集成测试命名为**IT，然后配置Maven Surefire Plugin默认只运行单元测试，然后再编写一个名为integrationTest的Profile，在其中配置Maven Surefire Plugin运行集成测试。然后再以此为基础分阶段构建项目，第一个构建为 mvn clean install -B -e -U ，第二个构建任务为 mvn clean deploy -B -e -U -PintegrationTest 。前一个构建成功后再触发第二个构建，然后才部署至Maven仓库。值得一提的是，Maven Surefire Plugin能够很好支持JUnit 3、JUnit 4和TestNG，你可以按照最适合自己的方式来划分单元测试和集成测试。





引：http://www.infoq.com/cn/articles/xxb-maven-4-ci?utm_source=articles_about_maven-practice&utm_medium=link&utm_campaign=maven-practice


