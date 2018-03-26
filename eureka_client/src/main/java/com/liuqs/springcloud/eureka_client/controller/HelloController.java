package com.liuqs.springcloud.eureka_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-03-25
 * @ Modified:
 **/
@RestController
public class HelloController {

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String index(){
        return "Hello, I am from port: " +port ;
    }



}
