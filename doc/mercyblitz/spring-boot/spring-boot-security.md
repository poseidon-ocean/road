# Spring Boot 安全
* 安全无小事，出现时会让人焦头烂耳

## 客户端安全

### CSRF
* 跨站请求伪造(Cross-site request forgery)
* 也称one-click attack或者session riding(会话控制)
* 缩写CSRF或者XSRF
* 一种挟制用户在当前登录的Web应用程序上执行非本意的操作攻击方法
* CSRF Token：服务端为客户端生成令牌，改令牌将用于合法性校验，一般通过请求或请求参数传递到服务端
* CSRF Token仓库：服务端组件
	* 用于从请求加载或生成CSRF Token
	* Spring Security提供了Cookie和HttpSession两种实现
* CSRF请求校验匹配器：服务端组件，用于判断请求是否需要CSRF校验
* POST请求是非幂等操作，非安全的，程序设计时需要考虑

### 攻防逻辑

### CSRF Token仓库
* 框架接口：CsrfTokenRespository
































