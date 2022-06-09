package com.example.paypalspringbootdemo.controller;

import com.example.paypalspringbootdemo.dto.OrderDto;
import com.example.paypalspringbootdemo.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @ApiOperation(value = "创建订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public OrderDto createOrder() {
        return orderService.createOrder();
    }

    @ApiOperation(value = "查询订单详情")
    @RequestMapping(value = "/ordersGet", method = RequestMethod.POST)
    public OrderDto ordersGet(@RequestParam String orderId) {
        return orderService.ordersGet(orderId);

    }
    @ApiOperation(value = "创建计费计划")
    @PostMapping(value = "/createPlan")
    public OrderDto createPlan(@RequestParam String orderId) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        return orderDto;
    }

}