package com.craftly.craftlyeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CraftlyEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CraftlyEurekaApplication.class, args);
	}

}
