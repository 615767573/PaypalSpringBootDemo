package com.example.paypalspringbootdemo.controller;

import com.example.paypalspringbootdemo.service.CapturesService;
import com.example.paypalspringbootdemo.service.PayPalCheckoutService;
import com.example.paypalspringbootdemo.service.RefundOrderService;
import com.example.paypalspringbootdemo.utils.RequestToMapUtil;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Payer;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: hsl
 * @Date: Created in 20:07 2022/5/27
 * @Desprition:Welcome 控制类
 */
@RestController
@RequestMapping(path = "/v1/payPal")
@CrossOrigin
public class PayPalController {

    @Autowired
    private CapturesService captureOrder;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private PayPalCheckoutService payPalCheckoutService;

    @ApiOperation(value = "执行扣款")
    @RequestMapping(value = "/capturesOrder")
    public Payer capturesOrder(@RequestParam String orderId) {
        return captureOrder.CaptureOrder(orderId);
    }

    @ApiOperation(value = "查询扣款详情")
    @RequestMapping(value = "/capturesGet")
    public HttpResponse capturesGet(@RequestParam String orderId) {
        return captureOrder.captureGet(orderId);
    }

    @ApiOperation(value = "申请退款")
    @RequestMapping(value = "/refundOrder")
    public HttpResponse refundOrder(@RequestParam String captureOrderId) {
        return refundOrderService.refundOrder(captureOrderId);
    }

    @ApiOperation(value = "查询退款详情")
    @RequestMapping(value = "/refundsGet")
    public HttpResponse refundsGet(@RequestParam String captureOrderId) {
        return refundOrderService.refundGet(captureOrderId);
    }


    @ApiOperation(value = "ipn异步回调")
    @PostMapping(value = "/paypal/ipn/back")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        return payPalCheckoutService.callback(RequestToMapUtil.getParameterMap(request));
    }


}
