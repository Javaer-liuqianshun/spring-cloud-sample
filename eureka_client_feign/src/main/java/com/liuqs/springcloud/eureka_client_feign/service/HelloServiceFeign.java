package com.liuqs.springcloud.eureka_client_feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-03-28
 * @ Modified:
 **/
@FeignClient(value = "hello-service")
public interface HelloServiceFeign {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String invokeHelloServiceValueEqualsHello();

}
