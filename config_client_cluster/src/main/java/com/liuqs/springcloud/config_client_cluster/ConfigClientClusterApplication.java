package com.liuqs.springcloud.config_client_cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClientClusterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientClusterApplication.class, args);
	}
}
