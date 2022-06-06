package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class FrequencyBO implements Serializable {
    /**
     * 订阅收费或计费的时间间隔。 可能的值为：
     * DAY。每日结算周期。
     * WEEK。每周结算周期。
     * MONTH。每月结算周期。
     * YEAR。每年的帐单周期。
     */
    private String interval_unit;

    /**
     * 订阅者计费之后的时间间隔数。例如，如果interval_unit是DAY用interval_count的 2，
     * 该订阅收费每两天一次。下表列出了最大允许值interval_count的每个interval_unit
     */
    private Integer interval_count;
}

