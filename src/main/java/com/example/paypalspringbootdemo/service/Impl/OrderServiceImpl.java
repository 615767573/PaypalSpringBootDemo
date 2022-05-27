package com.example.paypalspringbootdemo.service.Impl;

import com.example.paypalspringbootdemo.service.OrderService;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public String CreateOrder() {
        return "创建订单";
    }
}
