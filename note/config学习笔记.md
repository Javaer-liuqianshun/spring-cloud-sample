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
spring.cloud.server.git.uri = ttps://gitee.com/didispace/SpringCloud-Learning/：配置git仓库地址<br>
spring.cloud.server.git.search-paths=spring_cloud_in_action/config-repo：配置文件位于git仓库中的位置<br>
spring.cloud.server.git.username=  ：用户名<br>
spring.cloud.server.git.password=  ：密码<br>
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