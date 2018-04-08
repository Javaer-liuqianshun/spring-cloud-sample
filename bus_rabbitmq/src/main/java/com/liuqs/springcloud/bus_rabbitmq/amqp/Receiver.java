package com.liuqs.springcloud.bus_rabbitmq.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ Author: liuqianshun
 * @ Description:
 * <p>
 * 消费者
 * @ Date: Created in 2018-04-08
 * @ Modified:
 **/
@Component
@RabbitListener(queues = "hello")
public class Receiver {

	@RabbitHandler
	public void process(String context) {
		System.out.println("消费者 : " + context);
	}

}
