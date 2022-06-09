package com.example.paypalspringbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        // v2 不同
        return new Docket(DocumentationType.OAS_30)
                .select()
                // 设置扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.example.paypalspringbootdemo.controller"))
                .build();
    }
}