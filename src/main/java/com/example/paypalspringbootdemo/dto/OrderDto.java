package com.example.paypalspringbootdemo.dto;

import com.paypal.orders.Capture;
import com.paypal.orders.Refund;
import lombok.Data;

import java.util.List;

/**
 * @author hsl
 */
@Data
public class OrderDto {

    // 支付页面url
    private String approveUlr;

    private String OrderId;

    private int statusCode;

    private String status;

    private String totalAmount;

    private String customId;

    private String invoiceId;

    List<Capture> captures;

    List<Refund> refunds;
}
