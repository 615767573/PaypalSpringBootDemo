package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class PaymentMethodBO implements Serializable {
    /**
     * 客户在商家站点上选择的付款方式。
     * 默认值：PAYPAL。
     */
    private String payer_selected;

    /**
     * 商家首选的付款方式。 可能的值为：
     * UNRESTRICTED。接受来自客户的任何类型的付款。
     * IMMEDIATE_PAYMENT_REQUIRED。仅接受客户的即时付款。例如，
     * 信用卡，贝宝余额或即时ACH。确保在捕获时，付款不具有“待处理”状态。
     * 默认值：UNRESTRICTED。
     */
    private String payee_preferred;
}

