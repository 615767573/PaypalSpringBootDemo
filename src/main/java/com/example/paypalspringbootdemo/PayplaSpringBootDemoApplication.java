package com.example.paypalspringbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableWebMvc
public class PayplaSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayplaSpringBootDemoApplication.class, args);
    }

}
