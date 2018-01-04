# Spring MVC的高级技术
* Spring MVC配置的替代方案
* 处理文件上传
* 在控制器中处理异常
* 使用flash属性

## Spring MVC配置的替代方案
* 添加其它的Servlet和Filter
* 需要让Spring MVC在启动的时候，从带有@Configuration注解的类上加载配置   在web.xml中配置

## 处理multipart形式的数据
* DispartcherServlet没有实现任何解析multipart请求数据的功能
* CommonsMultipartResolver：使用Jakarta Commons FileUpload解析multipart请求
* StandardServletMultipartResolver：依赖Servlet3.0对multipart请求的支持(始于Servlet3.1)

## 处理异常
* Spring提供多种方式将异常转换为响应
	* 特定的Spring异常将会自动映射为指定的HTTP状态码
	* 异常上可以添加@ResponseStatus注解，从而将其映射为某一个HTTP状态码
	* 在方法上可以添加@ExceptionHandler注解，使其用来处理异常
* Spring的一些异常会默认映射为HTTP状态码
	* BindException		400-Bad Request
	* ConversionNotSupportedException		500-Internal Server Error
	* NoSuchRequestHandlingMethodException	404-Not Found
	* ...
* 捕获异常跳转到异常请求并响应
* 为控制器添加通知处理异常 AppWideExceptionHandler

## 跨重定向请求传递数据
* 重定向：能够防止用户点击浏览器的刷新按钮或后退箭头时，客户端重新执行危险的POST请求
* "redirect:"，重定向前缀
* 重定向之后，原始请求数据随之消亡
* 重定向传递数据：
	* 使用URL，如"redirect:/user/{username}"， "redirect:/user?username='000'"
	* 使用flash属性，如 model.addFlashAttribute(user)	把数据放在会话中，但是后面需要注意清除数据，免得混淆















