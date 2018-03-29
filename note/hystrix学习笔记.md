# Ribbon + RestTemplate中使用Hystrix

## @EnableHystrix
通过在启动类上添加@EnableEurekaServer注解开启Hystrix功能

## @HystrixCommand
@HystrixCommand注解创建Hystrix，并通过@HystrixCommand(fallbackMethod = "")说明当 **http://HELLO-SERVICE/hello** 服务调用失败时，回调fallbackMethod=""中指定的方法

```
@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/ribbon/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String hi(){
        return restTemplate.getForObject("http://HELLO-SERVICE/hello",String.class);
    }

    public String hiError(){
        return "hello-service服务调不通";
    }
}
```

当 **http://HELLO-SERVICE/hello** 服务调用失败时，执行hiError()方法

## 浏览器显示
![ribbon中使用Hystrix](http://a1.qpic.cn/psb?/V11X9h921LUmIc/F*fKnEsCaTO54vkQyoSACp8*FvU5BGAEYLE5.MnJLxI!/c/dPQAAAAAAAAA&ek=1&kp=1&pt=0&bo=WAO0AAAAAAARF88!&vuin=763667629&tm=1522227600&sce=60-2-2&rf=0-0)

如图所示，请求 *http://localhost:1117/ribbon/hi* 时由于 **HELLO-SERVICE** 服务没有开启，所以浏览器中显示是 *hiError()* 返回的信息


# Feign中使用Hystrix

## application.yml配置
Feign是自带断路器的，在Spring Cloud中，它没有默认打开。需要在配置文件中配置打开它，在配置文件加以下代码
```
feign:
    hystrix:
        enabled: true
```

## @FeignClient
在@FeignClient中添加fallback = "",说明当 **http://HELLO-SERVICE/hello** 服务调用失败时，回调fallback=""类中的方法
```
@FeignClient(value = "hello-service",fallback = HelloHystrix.class)
public interface HelloServiceFeign {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String invokeHelloServiceValueEqualsHello();

}
```

## 断路器类
当 **http://HELLO-SERVICE/hello** 服务调用失败时，会执行 *HelloHystrix.class*中的方法，HelloHystrix类是HelloServiceFeign接口的实现方法<br>
```
@Component
public class HelloHystrix implements HelloServiceFeign{

    @Override
    public String invokeHelloServiceValueEqualsHello() {
        return "hello-service服务调不通";
    }
}
```
HelloHystrix类使用@Component注解在Spring应用上下文中实例化bean

## 浏览器显示
![Feign中使用Hystrix](http://a2.qpic.cn/psb?/V11X9h921LUmIc/lBzY4JwqPFIiknZ6o3yPqclFuOUgDAZOxCz9AGoZ*s0!/c/dGkAAAAAAAAA&ek=1&kp=1&pt=0&bo=EAPDAAAAAAARF*A!&vuin=763667629&tm=1522231200&sce=60-2-2&rf=0-0)

如图所示，请求 http://localhost:1118/invoke 时由于 HELLO-SERVICE 服务没有开启，所以浏览器中显示是实现类返回的信息