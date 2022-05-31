package com.example.paypalspringbootdemo.service;

import java.util.Map;


/**
 * @author hsl
 */
public interface PayPalCheckoutService {

    /**
     * 回调
     * @param map
     * @return
     */
    String callback(@SuppressWarnings("rawtypes") Map map);
}
