package com.liuqs.springcloud.eureka_client_feign.controller;

import com.liuqs.springcloud.eureka_client_feign.service.HelloServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-03-28
 * @ Modified:
 **/
@RestController
public class FeignController {

    @Autowired
    private HelloServiceFeign helloServiceFeign;

    @RequestMapping(value = "/invoke",method = RequestMethod.GET)
    public String invoke(){
        return helloServiceFeign.invokeHelloServiceValueEqualsHello();
    }
}
