package io.github.ricardsgon.helpdeskapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI helpdeskOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Helpdesk API")
                .description("API REST para abertura e acompanhamento de chamados de suporte.")
                .version("0.0.1")
                .contact(new Contact()
                        .name("Ricardo Silva")
                        .url("https://github.com/ricardsgon")));
    }
}