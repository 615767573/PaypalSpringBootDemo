package com.example.paypalspringbootdemo.service;


import com.example.paypalspringbootdemo.dto.OrderDto;

import java.io.IOException;

/**
 * @author hsl
 */
public interface OrderService {

    /**
     * 下单返回付款界面
     *
     * @return  paypal支付页面连接
     * @throws IOException
     */
    public OrderDto createOrder();


    /**
     * OrdersGet（查询订单详情）
     *
     * @param orderID 由创建订单返回
     * @return
     */
    public OrderDto ordersGet(String orderID) ;

}
