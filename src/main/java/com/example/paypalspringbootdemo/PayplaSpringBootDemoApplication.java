package com.example.paypalspringbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableWebMvc
@MapperScan("com.example.paypalspringbootdemo.mapper")
public class PayplaSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayplaSpringBootDemoApplication.class, args);
    }

}
