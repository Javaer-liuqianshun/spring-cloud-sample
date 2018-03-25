package com.liuqs.springcloud.eureka_client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String index(){
        logger.info("welcome use eureka!");
        return "Hello World";
    }



}
