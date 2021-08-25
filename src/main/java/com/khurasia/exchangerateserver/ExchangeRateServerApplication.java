package com.khurasia.exchangerateserver;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class ExchangeRateServerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ExchangeRateServerApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "5000"));
        app.run(args);
    }

    @Bean
    public OpenAPI apiSpecification(@Value("${app.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Mock Exchange Rates Server")
                        .description("This server generates random data for educational purposes. DO NOT CONSUME THESE DATA TO MAKE FINANCIAL DECISIONS.")
                        .version(appVersion)
                        .license(new License().name("Terms of use: MIT License").url("https://mit-license.org/")));
    }

}
