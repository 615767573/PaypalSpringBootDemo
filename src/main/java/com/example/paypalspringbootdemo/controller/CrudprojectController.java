package com.example.paypalspringbootdemo.controller;

import com.example.paypalspringbootdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hsl
 * @Date: Created in 20:07 2022/5/27
 * @Desprition:Welcome 控制类
 */
@RestController
public class CrudprojectController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/test")
    public String welcome(){
        return orderService.CreateOrder();
    }
}