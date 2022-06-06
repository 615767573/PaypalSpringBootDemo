package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class FixedPriceBO implements Serializable {
    /**
     * 标识货币的三字符ISO-4217货币代码。
     */
    private String currency_code;

    /**
     * 该值可能是：
     * 整数，例如： JPY此类通常不是小数。
     * TND此类货币的小数部分可细分为千分之一。
     * 有关货币代码所需的小数位数
     */
    private String value;
}

