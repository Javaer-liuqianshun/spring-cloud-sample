package com.liuqs.springcloud.eureka_client_ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-03-26
 * @ Modified:
 **/
@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/ribbon/hi")
    public String hi(){
        return restTemplate.getForObject("http://HELLO-SERVICE/hello",String.class);
    }
}
