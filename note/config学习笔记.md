# 配置中心(config-server)
## @EnableConfigServer
通过在启动类上添加@EnableConfigServer注解开启Spring Cloud Config服务端功能

## application.yml配置文件
```
spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/didispace/SpringCloud-Learning/
          search-paths: spring_cloud_in_action/config-repo
          username:
          password:
server:
  port: 1121
```
>spring.application.name = config-server：服务名称<br>
spring.cloud.config.server.git.uri = ttps://gitee.com/didispace/SpringCloud-Learning/：配置git仓库地址<br>
spring.cloud.config.server.git.search-paths=spring_cloud_in_action/config-repo：配置文件位于git仓库中的位置<br>
spring.cloud.config.server.git.username=  ：用户名<br>
spring.cloud.config.server.git.password=  ：密码<br>
server.port= 1121：项目端口号<br>

## 访问配置信息URL与配置文件映射关系
- /{application}/{profile}/[/{label}]
- /{application}-{profile}.yml
- /{label}/{application}-{profile}.yml
- /{application}-{profile}.properties
- /{label}/{application}-{profile}.properties

## 访问http://localhost:1121/didispace/prod/master
![config服务端](http://a3.qpic.cn/psb?/V11X9h921LUmIc/eUQEW*0L0PD*DYP2OmU1KzbUApm.GOybZwqlzHs4UMc!/c/dGoBAAAAAAAA&ek=1&kp=1&pt=0&bo=uwTuAQAAAAARF3A!&vuin=763667629&tm=1522677600&sce=60-2-2&rf=0-0)
如图可以看出返回应用名为 **didispace** ，环境名 **prod** ，分支名 **master** ，以及default环境和prod环境的配置内容。


# 客户端配置(config-client)
## 创建bootstrap.properties文件
```
spring.application.name=didispace
spring.cloud.config.profile=dev
spring.cloud.config.label=master
spring.cloud.config.uri= http://localhost:1121/

server.port=1122
```
如上配置必须放在 bootstrap.properties文件中<br>
>spring.application.name=didispace：对应配置文件规则中的{application}部分<br>
spring.cloud.config.profile=dev：对应配置文件规则中的{profile}部分<br>
spring.cloud.config.label=master：对应配置文件规则中的{label}部分<br>
spring.cloud.config.uri= http://localhost:1121/：配置中心con丘g-server的地址<br>
server.port=1122

## 从config-server读取信息
```
@RefreshScope
@RestController
public class TestController {
	@Value("${from}")
	private String from;

	@RequestMapping("/from")
	public String from() {
		return this.from;
	}
}
```

## 浏览器显示
![config-client](http://a3.qpic.cn/psb?/V11X9h921LUmIc/txVrh0aIZ04V7mD5Da3BkWXQ7*zZGygpmdI9QQsFiKo!/c/dDIBAAAAAAAA&ek=1&kp=1&pt=0&bo=3QP5AAAAAAARFwc!&vuin=763667629&tm=1522677600&sce=60-2-2&rf=0-0)

如图可见从 **config-server** 中读取了 **from** 变量


# 高可用的分布式配置
## 简介
当服务实例很多时，都从配置中心读取文件，这时可以考虑将配置中心做成一个微服务，将其集群化，从而达到高可用。<br>

把config server作为一个普通的微服务应用，纳入Eureka的服务治理体系中。这样我们的微服务应用就可以通过配置中心的服务名来获取配置信息，服务端的负载均衡配置和客户端的配置中心指定都通过服务治理机制一并解决了，既实现了高可用，也实现了自维护。<br>

## 服务端配置
### application.yml配置文件
```
spring:
  application:
    name: config-server
# Git管理配置
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/didispace/SpringCloud-Learning/
          search-paths: spring_cloud_in_action/config-repo
          username:
          password:
server:
  port: 7001
# 配置服务注册中心
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:1111/eureka/
```
### 启动类
添加@EnableDiscoveryClient和@EnableConfigServer，表示是一个config服务端并向服务中心注册。

## 客户端配置
```
spring.application.name=didispace
server.port=7003
eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/
spring.cloud.config.discovery.enabled=true
spring.cloud.config.discovery.serviceId=config-server
spring.cloud.config.profile=dev
```
>spring.cloud.config.discovery.enabled=true：开启通过服务访问Config Server的功能<br>
spring.cloud.config.discovery.serviceId=config-server：指定Config Server注册的服务名<br>

**这里使用Config Server注册的服务名来指定服务，达到负载均衡的作用**

## 从config-server-cluster中读取信息
```
@RestController
public class TestController {

	@Value("${from}")
	private String from;

	@RequestMapping("from")
	public String from(){
		return from;
	}
}
```


