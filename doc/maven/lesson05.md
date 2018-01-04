# Maven实战 - 自动化Web应用集成测试

## 自动化集成测试的角色
* 没有自动化测试的持续集成，很难称之为真正的持续化集成
* 自动化测试包括单元测试、验收测试、性能测试

## 基于Maven的一般流程
* 集成测试与单元测试最大的区别是它需要尽可能的测试整个功能及相关环境
* 测试Web应用步骤：
	* 启动Web容器
	* 部署待测试Web应用
	* 以Web客户端的角色运行测试用例
	* 停止Web容器
* 以黑盒的形式模拟客户端进行测试，需要注意的是
	* 理解一些基本的HTTP协议知识
		* 服务端在什么情况下应该返回HTTP代码200
		* 什么时候应该返回401错误
		* 所支持的Content-Type是什么

## 一个简单的例子
* IT表示IntegrationTest
* 通过命名规则和创建配置，可以优雅地分离单元测试和集成测试
* mvn clean install -Pintegration-test
* http://cupofjava.de/blog/2013/02/05/integration-tests-with-maven-and-tomcat/
```
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		
		//如果参数不完整，则返回HTTP 400错误，表示客户端的请求有问题
		if(a == null || b == null){
			resp.setStatus(400);
			return;
		}
		
		int result = Integer.valueOf(a) + Integer.valueOf(b);
		
		resp.setStatus(200);
		resp.getWriter().print(result);
	}

}
```

```
import static org.junit.Assert.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * IT表示IntegrationTest
 *  
 * @Date:2017年8月9日下午2:45:40 
 * @Author:hjt
 */
public class AddServletIT {

	
	@Test
	public void addWithParametersAndSucceed() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("http://localhost:8080/add?a=1&b=2");
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		assertEquals("3", EntityUtils.toString(httpResponse.getEntity()));
	}
	
	@Test
	public void addWithoutParameterAndFail() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("http://localhost:8080/add");
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		assertEquals(400, httpResponse.getStatusLine().getStatusCode());
	}
}
```




世上无难事，只怕你弱智啊
引：http://www.infoq.com/cn/news/2011/03/xxb-maven-5-integration-test?utm_source=news_about_maven-practice&utm_medium=link&utm_campaign=maven-practice

Maven生命周期：
http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html





