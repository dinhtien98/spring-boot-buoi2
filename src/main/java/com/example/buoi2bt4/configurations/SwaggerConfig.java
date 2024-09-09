package com.example.buoi2bt4.configurations;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${open.api.title}")
    private String title;
    @Value("${open.api.version}")
    private String version;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description("this is a sample API")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                        .servers(List.of(new Server().url("http://localhost:8080/").description("this is a sample server")));
    }



    @Bean
    public GroupedOpenApi groupedOpenApi1() {
        return GroupedOpenApi
                .builder()
                .group("admin-api")
                .pathsToMatch("/api/v1/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApi2() {
        return GroupedOpenApi
                .builder()
                .group("layout-api")
                .pathsToMatch("/api/v1/layout/**")
                .build();
    }
}
