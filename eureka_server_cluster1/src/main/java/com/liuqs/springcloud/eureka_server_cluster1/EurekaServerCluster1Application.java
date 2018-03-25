package com.liuqs.springcloud.eureka_server_cluster1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerCluster1Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerCluster1Application.class, args);
	}
}
