package com.spike.calender.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean

	OpenAPI customOpenAPI() {

		return new OpenAPI().info(new Info().title("Spike")
				.description("This API exposes endpoints to manage Calender-Event-Service."));
	}
}
