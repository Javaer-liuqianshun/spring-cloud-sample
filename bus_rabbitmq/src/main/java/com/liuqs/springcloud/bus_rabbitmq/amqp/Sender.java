package com.liuqs.springcloud.bus_rabbitmq.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ Author: liuqianshun
 * @ Description:
 * <p>
 * 生产者
 * @ Date: Created in 2018-04-08
 * @ Modified:
 **/
@Component
public class Sender {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send() {
		String context = "hello " + new Date();
		System.out.println("生产者 : " + context);
		amqpTemplate.convertAndSend("hello", context);
	}
}
