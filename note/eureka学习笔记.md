[toc]

# @EnableEurekaServer
通过在启动类上添加@EnableEurekaServer注解来启动一个服务注册中心

# 配置文件说明
## 服务注册中心配置说明
> eureka.client.register-with-eureka：由于该应用为注册中心，所以设置为false, 代表不向注册中心注册自己。<br>
eureka.client.fetch-registry: 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false。

