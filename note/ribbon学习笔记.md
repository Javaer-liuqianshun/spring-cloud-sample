# Ribbon + RestTemplate服务消费者

## @EnableDiscoveryClient
通过在启动类上添加@EnableDiscoveryClient来表明是一个服务提供者，以获取服务发现能力

## @LoadBalanced
```
@Bean
@LoadBalanced
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```
@Bean注解表示向Spring上下文中添加一个id为restTemplate的bean<br>
@LoadBalanced注解标识这个restTemplate开启负载均衡的功能

## 创建服务消费者
```
@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/ribbon/hi")
    public String hi(){
        return restTemplate.getForObject("http://HELLO-SERVICE/hello",String.class);
    }
}
```

## 浏览器运行
结果1：<br>
![结果1](http://a1.qpic.cn/psb?/V11X9h921LUmIc/cF6mq0k2**Sg4gZdEUytYB2pkh82DBVnH8SirkjiHaw!/c/dPQAAAAAAAAA&ek=1&kp=1&pt=0&bo=hgONAAAAAAARFyg!&vuin=763667629&tm=1522047600&sce=60-2-2&rf=0-0)
结果2：<br>
![结果2](http://a3.qpic.cn/psb?/V11X9h921LUmIc/SQENP6BecPBhJVI.rhds12ktk2dlBdtBFnGLbn6tSvs!/c/dOIAAAAAAAAA&ek=1&kp=1&pt=0&bo=vwJ4AAAAAAARF.U!&vuin=763667629&tm=1522047600&sce=60-2-2&rf=0-0)

如图 *结果1* 和 *结果2*，同样是访问 *localhost:1117/ribbon/hi* 但是浏览器显示的结果不同?<br>
因为 **HELLO-SERVICE** 服务使用集群，有两个应用服务。而该项目的restTemplate使用@LoadBalanced注解来开启的负载均衡

# Feign服务消费者

## @EnableDiscoveryClient
通过在启动类上添加@EnableDiscoveryClient来表明是一个服务提供者，以获取服务发现能力

## @EnableFeignClients
通过在启动类上添加@EnableFeignClients来开启Feign功能


## 定义一个Feign接口
```
@FeignClient(value = "hello-service")
public interface HelloServiceFeign {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String invokeHelloServiceValueEqualsHello();

}
```
创建一个接口，使用@FeignClient(服务名)来指定调用那个服务，如：@FeignClient(value = "hello-service")<br>
Feign支持Spring MVC的注解，@RequestMapping(value = "/hello")对应 **HELLO-SERVICE** 服务url为 */hello*<br>

## 创建服务消费者
```
@RestController
public class FeignController {

    @Autowired
    private HelloServiceFeign helloServiceFeign;

    @RequestMapping(value = "/invoke",method = RequestMethod.GET)
    public String invoke(){
        return helloServiceFeign.invokeHelloServiceValueEqualsHello();
    }
}
```

## 浏览器运行
结果1：<br>
![结果1](http://a1.qpic.cn/psb?/V11X9h921LUmIc/I2GN3pssPEZF.CjcM1RL5fByh0iWSaLFbjYZlCdBR2c!/c/dPQAAAAAAAAA&ek=1&kp=1&pt=0&bo=PQOlAAAAAAARF7s!&vuin=763667629&tm=1522206000&sce=60-2-2&rf=0-0)
结果2：<br>
![结果2](http://a2.qpic.cn/psb?/V11X9h921LUmIc/KP2VZQnNPk2uU5*atv9cLN53t1h5JcuwwqD1KYBNn5A!/c/dD0BAAAAAAAA&ek=1&kp=1&pt=0&bo=HwOTAAAAAAARF68!&vuin=763667629&tm=1522206000&sce=60-2-2&rf=0-0)

如图 结果1 和 结果2，同样是访问 localhost:1118/invoke 交替显示 *Hello，I am from port:1115*,*Hello，I am from port:1116*