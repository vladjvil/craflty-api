package com.craftly.craftlyauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.craftly.craftlyauth",
		"com.craftly.jwtmodule"
})
public class CraftlyAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(CraftlyAuthApplication.class, args);
	}

}
