# SpringBoot中使用RabbitMQ
## pom.xml中引入rabbitMQ依赖
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
## application.xml
```
spring:
  application:
    name: rabbitmq-hello
  rabbitmq:
    host: localhost
    port: 5672
    username: springcloud
    password: 123456
```
username：rabbitmq server管理员用户名<br>
password: rabbitmq server管理员密码<br>
需要为管理员分配管理队列的权限，即设置virtual hosts
## 生产者
```
@Component
public class Sender {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send() {
		String context = "hello " + new Date();
		System.out.println("发送者 : " + context);
		/** convertAndSend(String routingKey, Object message) **/
		amqpTemplate.convertAndSend("hello", context);
	}
}
```
由AmqpTemplate的convertAndSend()来发送消息<br>
convertAndSend()一共有6个重载方法，总共4个参数<br>
如：convertAndSend(String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor)
参数分别表示指定 *消息交换机、路由关键字、消息内容和消息处理器*


## 消费者
```
@Component
@RabbitListener(queues = "hello")
public class Receiver {

	@RabbitHandler
	public void process(String context) {
		System.out.println("消费者 : " + context);
	}

}
```

## 启动应用主类
在控制台中打印信息中可见创建了一个访问127.0.0.1:5672中springcloud连接。<br>
![访问rabbitmq](http://a3.qpic.cn/psb?/V11X9h921LUmIc/jnfeV8OZEolpb7dfFJn8jyyOX1Om*nKVy2pYiyv3TQE!/c/dPIAAAAAAAAA&ek=1&kp=1&pt=0&bo=.QRRAAAAAAARF4w!&vuin=763667629&tm=1523199600&sce=60-2-2&rf=0-0)

在RabbitMQ的控制面板，可以看到Connections和Channels中包含当前连接的条目：<br>
![RabbitMQ控制面板 - Connections](http://a1.qpic.cn/psb?/V11X9h921LUmIc/w1VH4DRohCUeoyutB520KvqfYFD4z6j4FGungClouDo!/c/dEQBAAAAAAAA&ek=1&kp=1&pt=0&bo=JgR8AQAAAAARF38!&vuin=763667629&tm=1523199600&sce=60-2-2&rf=0-0)
![RabbitMQ控制面板 - Channels](http://a1.qpic.cn/psb?/V11X9h921LUmIc/BYW*mS9uvZ5HbQm4cuy1BrR6WuxnnaXPxYyIB7ElkEA!/c/dAQBAAAAAAAA&ek=1&kp=1&pt=0&bo=0QRpAQAAAAARF50!&vuin=763667629&tm=1523199600&sce=60-2-2&rf=0-0)

## 测试类
```
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusRabbitmqApplication.class)
public class BusRabbitmqApplicationTests {

	@Autowired
	private Sender sender;

	@Test
	public void sendMessage() {
		sender.send();
	}
}
```
启动测试类，在控制台打印信息中可见消息产生记录和消息消费记录：<br>
![RabbitMQ测试](http://a1.qpic.cn/psb?/V11X9h921LUmIc/YHTYxLNrkHEd8BIhyr1oIo2hgYXbkfD39s3pNlunaQM!/c/dDABAAAAAAAA&ek=1&kp=1&pt=0&bo=FQZ7AQAAAAARF0k!&vuin=763667629&tm=1523199600&sce=60-2-2&rf=0-0)

## RabbitMQ学习资料
1. [RabbitMQ官方教程](http://www.rabbitmq.com/getstarted.html)
2. [Erlang环境 + RabbitMQ安装](https://blog.csdn.net/lu1005287365/article/details/52315786)
3. [RabbitMQ基础知识详解](https://mp.weixin.qq.com/s/OABseRR0BnbK9svIPyLKXw)
4. [启动RabbitMQ报java.net.SocketException解决方法](https://blog.csdn.net/only09080229/article/details/43304543)

# Spring Cloud Bus中使用RabbitMQ
## 扩展config-client-cluster应用
### 修改pom.xml
新增**AMQP**依赖<br>
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
### 修改bootstrap.properties
新增**RabbitMQ**的配置依赖<br>
```
# 配置RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=springcloud
spring.rabbitmq.password=123456
```
## 启动应用主类(config-client-cluster)

### console控制台
在启动时，config-client-cluster多了一个 */bus/refresh* 的POST请求：<br>
![/bus/refresh](http://a3.qpic.cn/psb?/V11X9h921LUmIc/6unYtcJPNBlOniHVUxITL.8FbjK2Mbly4YUWAUPnH7k!/c/dDIBAAAAAAAA&ek=1&kp=1&pt=0&bo=rgRwAAAAAAARF*o!&vuin=763667629&tm=1523278800&sce=60-2-2&rf=0-0)
在启动时，config-client-cluster创建了一个访问127.0.0.1:5672中springcloud连接，RabbitMQ使用端口为 **2540**：<br>
![建立RabbitMQ连接](http://a3.qpic.cn/psb?/V11X9h921LUmIc/vG6wKGdrKMpGZpTTF*gcXnca9chlJ89W65f2E3.*1lw!/c/dFYBAAAAAAAA&ek=1&kp=1&pt=0&bo=SgaIAAAAAAARF.Q!&vuin=763667629&tm=1523278800&sce=60-2-2&rf=0-0)

## 启动应用主类(config-server-cluster)
### 浏览器访问Git远程仓库
![访问Git远程仓库](http://a2.qpic.cn/psb?/V11X9h921LUmIc/FYJGbt1shxK6cdAG60wnNGWlbyLMTNZ7OOd7Cn0v8Us!/c/dEEBAAAAAAAA&ek=1&kp=1&pt=0&bo=dASWAQAAAAARF8c!&vuin=763667629&tm=1523278800&sce=60-2-2&rf=0-0)


## 更新Git远程仓库信息
把Git远程仓库中**config-client-dev.properties**中的 *from=git-dev-2.0* 更新成 *from=git-dev-6.0*

## 向config-config-cluster中发送/bus/refresh的POST请求更新配置更新
![消息总线更新远程配置](http://a4.qpic.cn/psb?/V11X9h921LUmIc/Bjeag3mwoqyzjStjBjeRraR*ysKUyQ4P4kI1c.dyuvY!/c/dDMBAAAAAAAA&ek=1&kp=1&pt=0&bo=.gOAAgAAAAARGFQ!&vuin=763667629&tm=1523278800&sce=60-2-2&rf=0-0)

将系统系统起来后，图中"**Service A(即config客户端)**"三个服务会请求 **Config Server(即config配置中心)** ，根据应用配置的规则从Git仓库中获取配置信息并返回。<br>
若Git仓库中的配置信息更新，如 *from=git-dev-2.0* 更新成 *from=git-dev-6.0*，但是没有重启服务，那么**Service A**三个服务获取不到更新后Git仓库的配置信息。此时，需要通过向一个**Service A**服务发送/bus/refresh的POST请求。如：向**Service A - 3**发送一个  *http://ServiceA-3/bus/refresh*的POST请求，将刷新请求发送到消息总线中，该消息事件会被**ServiceA- 1**服务和**Service A - 2**服务从消息总线中获取到，将重新通过**Config Server**中获取Git仓库中更新后的配置信息，从而实现配置信息的动态更新。