package com.liuqs.springcloud.config_client_cluster.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-04-03
 * @ Modified:
 **/
@RestController
public class TestController {

	@Value("${from}")
	private String from;

	@RequestMapping("from")
	public String from(){
		return from;
	}
}
