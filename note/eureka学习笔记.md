# 创建服务注册中心

## @EnableEurekaServer
通过在启动类上添加@EnableEurekaServer注解来表名是一个服务注册中心

## 配置文件说明
eureka.client.register-with-eureka：由于该应用为注册中心，所以设置为false, 代表不向注册中心注册自己。<br>
eureka.client.fetch-registry：由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false。<br>
具体配置如下
```
server:
  port: 1111
eureka:
  instance:
    hostname: localhost
  client:
    fetch-registry: false
    register-with-eureka: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

```

## 启动项目
![启动项目](http://a4.qpic.cn/psb?/V11X9h921LUmIc/vPQsVUDkhNhdxVtfmxBJIjo.tqxqpjf5GK5bNq66288!/c/dHMAAAAAAAAA&ek=1&kp=1&pt=0&bo=agdwAgAAAAARFz8!&vuin=763667629&tm=1521982800&sce=60-2-2&rf=0-0)
从图可见 Instances currently registered with Eureka下显示 No instances available,说明目前没有服务可用


# 创建服务提供者

## @EnableEurekaClient
通过在启动类上添加@EnableEurekaClient来表名是一个服务提供者

## 配置文件说明
spring.application.name：设置该项目
eureka.client.serviceUrl.defaultZone：指向Eureka服务注册中心的地址<br>
```
spring:
  application:
    name: hello-service
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:llll/eureka/
```

## 启动项目
启动服务提供者项目后，刷新服务注册中心项目，可见 Instances currently registered with Eureka下显示有 HELLO-SERVICE，说明HELLO-SERVICE的服务已经注册在服务注册中心了
![启动项目](http://a4.qpic.cn/psb?/V11X9h921LUmIc/wEz2BGVORU2u4R49ptMqwrsXwHMuA44ub9YPX4n1HI4!/c/dPMAAAAAAAAA&ek=1&kp=1&pt=0&bo=TgffAAAAAAARF7Y!&vuin=763667629&tm=1521986400&sce=60-2-2&rf=0-0)

# Eureka集群服务注册中心
在微服务架构这样的分布式环境中，需要充分考虑发生故障的情况，所以在生产环境中必须对各个组件进行高可用部署。

## 创建集群服务注册中心1
```
spring:
    application:
        name: eureka-server
server:
    port: 1112
eureka:
    instance:
        hostname: cluster1
    client: 
        serviceUrl: 
            defaultZone: http://cluster2:1113/eureka/
```
集群1的主机名为 *cluster1*，指向集群2的Eureka服务注册中心地址 *http://cluster2:1113/eureka/*

## 创建集群服务注册中心2
```
spring:
    application:
        name: eureka-server
server:
    port: 1113
eureka:
    instance:
        hostname: cluster2
    client: 
        serviceUrl: 
            defaultZone: http://cluster1:1112/eureka/
```
集群2的主机名为 *cluster2*，指向集群1的Eureka服务注册中心地址 *http://cluster1:1112/eureka/*

## 修改hosts文件
在/etc/hosts文件中添加cluster1和cluster2的转换，让上面的serviceUrl地址能正确被访问到，Windows系统路径为：C:\Windows\System32\drivers\etc\hosts
>127.0.0.1 cluster1<br>
127.0.0.1 cluster2

## 启动Eureka服务注册中心集群1和Eureka服务注册中心集群2
![Eureka服务注册中心集群1](http://a2.qpic.cn/psb?/V11X9h921LUmIc/NY*yVX6HaA86sEYwnORh7qo0sDHzgQmyDkB6aIUIrj8!/c/dJUAAAAAAAAA&ek=1&kp=1&pt=0&bo=WQc2AQAAAAARF0k!&vuin=763667629&tm=1521986400&sce=60-2-2&rf=0-0)
如图 **Eureka服务中心集群1**，DS Replicas下显示cluster2，说明存在另一个副本(即另一个Eureka服务注册中心)，两个服务注册中心构成集群
![Eureka服务注册中心集群2](http://a2.qpic.cn/psb?/V11X9h921LUmIc/6CvYVjMzs*e*0T7zfzzb1FtfYxIjEq9UjDGn3hskz*Q!/c/dEEBAAAAAAAA&ek=1&kp=1&pt=0&bo=YgcwAQAAAAARF3Q!&vuin=763667629&tm=1521986400&sce=60-2-2&rf=0-0)
如图 **Eureka服务中心集群2**，DS Replicas下显示cluster1，说明存在另一个副本(即另一个Eureka服务注册中心)，两个服务注册中心构成集群

## 创建服务提供者 -- 注册到集群中
```
spring:
  application:
    name: hello-service
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:1112/eureka/,http://localhost:1113/eureka/
```
把服务提供者注册到集群中，serviceUrl指向集群1和集群2的服务中心地址
![创建服务提供者 -- 注册到集群中](http://m.qpic.cn/psb?/V11X9h921LUmIc/eIhh6IQqE5mCe4kMbn8YB3LeXPRMaMr7wSprgurxJrM!/b/dJUAAAAAAAAA&bo=TgdtAQAAAAARBxU!&rf=viewer_4)

如图可见显示了HELLO-SERVICE的服务，说明已经注册到服务注册中心

