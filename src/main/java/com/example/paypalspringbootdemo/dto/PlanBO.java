package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class PlanBO implements Serializable {
    /**
     * 产品的ID。
     */
    private String product_id;

    /**
     * 计划名称。
     */
    private String name;

    /**
     * 计划的详细说明。
     */
    private String description;

    /**
     * 用于试用计费和常规计费的一系列计费周期。一个计划最多可以有两个试用周期，而只有一个常规周期
     */
    private BillingCyclesBO[] billing_cycles;

    /**
     * 订阅的付款首选项。
     */
    private PaymentPreferencesBO payment_preferences;
}

