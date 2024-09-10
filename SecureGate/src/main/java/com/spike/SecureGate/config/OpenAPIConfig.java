package com.spike.SecureGate.config;

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

    @Value("${spike.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Info info = new Info()
                .title("Employee CRUD REST API")
                .version("1.0")
                .description("This API exposes endpoints to manage SPIKE crud operations.")
                .termsOfService("https://www.w3.org/Provider/Style/dummy.html");
        return new OpenAPI().info(info).servers(List.of(devServer));
    }

}
