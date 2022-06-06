package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class SubscriptionsBO implements Serializable {
    /**
     * 计划的ID。
     */
    private String plan_id;

    /**
     * 订阅开始的日期和时间
     */
    private String start_time;

    /**
     * 应用程序上下文，可在使用PayPal进行订阅批准过程中自定义付款人的体验
     */
    private ApplicationContextBO application_context;

}

