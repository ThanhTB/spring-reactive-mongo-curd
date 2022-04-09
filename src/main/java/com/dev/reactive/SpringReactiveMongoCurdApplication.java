package com.dev.reactive;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Spring Webflux CURD example",
        version = "1.0",
        description = "demo swagger in spring webflux"
))
public class SpringReactiveMongoCurdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringReactiveMongoCurdApplication.class, args);
    }

}
