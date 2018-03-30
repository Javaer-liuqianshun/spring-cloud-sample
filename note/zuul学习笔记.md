# Zuul简介
Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，比如／api/user转发到到user服务，/api/shop转发到到shop服务。zuul默认和Ribbon结合实现了负载均衡的功能。

# 路由
## @EnableZuulProxy
通过在启动类上添加@EnableZuulProxy注解来开Zuul的功能

## 请求路由
### 传统路由方式
```
zuul.routes.api-a-url.path=/api-a-url/**
zuul.routes.api-a-url.url=http://localhost:8080/
```
该配置定义了发往API网关服务的请求中，所有符合 **/api-a-url/**** 规则的访问都将被路由转发到 http://localhost:8080/ 地址上。也就是，当访问 http://localhost:5555/api-a-url/hello 的时候，API网关服务将该请求路由到 http://localhost:8080/hello 

### 面向服务的路由
```
zuul.routes.api-a.path=/api-a/**
zuul.routes.api-a.serviceId=hello-service
```
如当访问 http://localhost:5555/api-a/hello，当url符合 **/api-a/**** 规则，由api-a路由负责转发，该路由映射的serviceId为 **hello-service**，所以最终 /hello 请求会发送到 hello-service某个服务实例上。

## application.yml配置
```
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:1111/eureka/
server:
  port: 1120
spring:
  application:
    name: service-zuul
zuul:
  routes:
    api-a:
      path: /api-a/**
      serviceId: service-ribbon
    api-b:
      path: /api-b/**
      serviceId: service-feign
```
## 启动项目
1. 启动eureka_server
2. 启动两个eureka_client
>java -jar eureka_client.jar --server.port=1115<br>
java -jar eureka_client.jar --server.port=1116
3. 启动eureka-client-ribbon
4. 启动eureka-client-feign
5. 启动server-zuul


## 浏览器显示
![image](http://a1.qpic.cn/psb?/V11X9h921LUmIc/4gJBJmFiqHqNF3bbNLznVZVUiDf65MN.8KaxiP2wWPE!/c/dPQAAAAAAAAA&ek=1&kp=1&pt=0&bo=lQMfAQAAAAARF6g!&vuin=763667629&tm=1522314000&sce=60-2-2&rf=0-0)

如图通过Zuul网关服务，成功访问 **HELLO-SERVICE** 服务。<br>

整个流程：
Zuul网关服务 -> ribbon或feign服务调用 -> 服务集群<br>
Zuul网关服务,ribbon或feign服务调用和服务集群 都在服务注册中心注册

# 过滤
## 实现ZuulFilter
1. filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下： 
    - pre：路由之前
    - routing：路由之时
    - post：路由之后
    - error：发送错误调用
2. filterOrder：过滤的顺序
3. shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
4. run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。


```
@Component
public class MyFilter extends ZuulFilter {
	private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
		Object accessToken = request.getParameter("token");
		if(accessToken == null) {
			log.warn("token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("token is empty");
			}catch (Exception e){}

			return null;
		}
		log.info("ok");
		return null;
	}
}
```

## 浏览器显示
![zuul过滤](http://a1.qpic.cn/psb?/V11X9h921LUmIc/p3i0r79C.glFp1ry4.xAwjvqJSWwfecCi24lzYlK6B8!/c/dPQAAAAAAAAA&ek=1&kp=1&pt=0&bo=fwO7AAAAAAARF.c!&vuin=763667629&tm=1522317600&sce=60-2-2&rf=0-0)

如图 可见当没有token参数时，显示 *token is empty*