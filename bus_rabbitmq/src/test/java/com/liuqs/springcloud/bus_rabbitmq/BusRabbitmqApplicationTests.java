package com.liuqs.springcloud.bus_rabbitmq;

import com.liuqs.springcloud.bus_rabbitmq.amqp.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusRabbitmqApplication.class)
public class BusRabbitmqApplicationTests {

	@Autowired
	private Sender sender;

	@Test
	public void sendMessage() {
		sender.send();
	}

}
