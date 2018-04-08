package com.liuqs.springcloud.bus_rabbitmq.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-04-08
 * @ Modified:
 **/
@Configuration
public class RabbitConfig {

	@Bean
	public Queue helloQueue(){
		return new Queue("hello");
	}
}
