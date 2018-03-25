package com.liuqs.springcloud.eureka_client_cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientClusterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientClusterApplication.class, args);
	}
}
