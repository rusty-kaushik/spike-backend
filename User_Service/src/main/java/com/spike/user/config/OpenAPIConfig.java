package com.spike.user.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${aniket.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("aniketkaushik22@gmail.com");
        contact.setName("Aniket Kaushik");

        //License mitLicense = new License().name("Dummy License").url("https://www.w3.org/Provider/Style/dummy.html");

        Info info = new Info()
                .title("Employee CRUD REST API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage user module crud operations.")
                .termsOfService("https://www.w3.org/Provider/Style/dummy.html");
                //.license(mitLicense);
        return new OpenAPI().info(info).servers(List.of(devServer));
    }

}
