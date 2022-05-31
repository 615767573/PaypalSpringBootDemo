package com.example.paypalspringbootdemo.controller;

import com.example.paypalspringbootdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: hsl
 * @Date: Created in 20:07 2022/5/27
 * @Desprition:Welcome 控制类
 */
@RestController
@RequestMapping(path = "/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/createOrder")
    public String createOrder() {
        try {
            return orderService.createOrder();
        } catch (IOException e) {
            return "create order error";
        }
    }

    @RequestMapping(value = "/createOrder")
    public String createOrder(@RequestParam String orderId) {
        return orderService.ordersGet(orderId);

    }
}