package com.example.payplespringbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hsl
 * @Date: Created in 20:07 2022/5/27
 * @Desprition:Welcome 控制类
 */
@RestController
public class CrudprojectController {

    @RequestMapping(value = "/test")
    public String welcome(){
        return "Crud Spring Boot Project ! ";
    }
}