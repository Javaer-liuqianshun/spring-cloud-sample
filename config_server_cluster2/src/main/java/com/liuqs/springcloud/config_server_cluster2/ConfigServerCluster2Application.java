package com.liuqs.springcloud.config_server_cluster2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigServerCluster2Application {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerCluster2Application.class, args);
	}
}
