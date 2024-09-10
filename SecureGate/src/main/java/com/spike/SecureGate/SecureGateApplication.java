package com.spike.SecureGate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.spike.SecureGate.feignClients")
public class SecureGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureGateApplication.class, args);
	}

}
