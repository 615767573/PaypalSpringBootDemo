package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class PaymentPreferencesBO implements Serializable {
    /**
     * 服务的初始设置费用
     */
    private SetupFeeBO setup_fee;

}

