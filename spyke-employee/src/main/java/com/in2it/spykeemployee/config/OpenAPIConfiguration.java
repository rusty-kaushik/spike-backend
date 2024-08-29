package com.in2it.spykeemployee.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	public OpenAPI defineOpenApi() {
		Server server = new Server();
		server.setUrl("http://localhost:8088");
		server.setDescription("Security controller");

		Contact myContact = new Contact();
		myContact.setName("Kishan Kashyap");
		myContact.setEmail("kishan.kashyap@in2ittech.com");

		Info information = new Info().title(" Secured Blog website API").version("1.0")
				.summary("This Prject is created for JWT authentication purpose using daoAuthentication mannager")
				.description("This API exposes endpoints to handle all the blog website API.").contact(myContact);
		return new OpenAPI().info(information).addSecurityItem(new SecurityRequirement().addList("JWT Token Authentication")).components(new Components().addSecuritySchemes("JWT Token Authentication", new SecurityScheme()
				.name("JWT Token Authentication").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))).servers(List.of(server));
	}



}
