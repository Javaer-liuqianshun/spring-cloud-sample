package com.liuqs.springcloud.eureka_client_feign.hystrix;

import com.liuqs.springcloud.eureka_client_feign.service.HelloServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-03-28
 * @ Modified:
 **/
@Component
public class HelloHystrix implements HelloServiceFeign{

    @Override
    public String invokeHelloServiceValueEqualsHello() {
        return "hello-service服务调不通";
    }
}
