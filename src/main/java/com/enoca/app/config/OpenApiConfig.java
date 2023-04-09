package com.enoca.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info=@Info(title="Customer Relationship Manager API",version = "1.0",contact = @Contact(name = "Kamran Kamilli", email = "kamilli818@gmail.com")))
public class OpenApiConfig {

}
