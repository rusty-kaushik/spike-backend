package com.spike.SecureGate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.spike.SecureGate.feignClients")
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecureGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureGateApplication.class, args);
	}

}
