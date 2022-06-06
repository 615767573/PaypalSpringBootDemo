package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class PricingSchemeBO implements Serializable {
    /**
     * 订阅收取的固定金额。固定金额的更改适用于现有和将来的订阅。
     * 对于现有订阅，价格更改后10天内的付款不受影响
     */
    private FixedPriceBO fixed_price;
}

