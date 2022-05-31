package com.example.paypalspringbootdemo.service;


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
    public String createOrder() throws IOException;


    /**
     * OrdersGet（查询订单详情）
     *
     * @param orderID 由创建订单返回
     * @return
     */
    public String ordersGet(String orderID) ;

}
