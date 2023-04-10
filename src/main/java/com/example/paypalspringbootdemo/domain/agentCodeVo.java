package com.example.paypalspringbootdemo.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @描述
 * @创建人 hsl
 * @创建时间 2023/2/17
 */
@Data
public class agentCodeVo {

    private String agentCode;

    /**
     * 云存储分成百分比
     */
    private BigDecimal percentageCs;

    /**
     * 流量卡分成百分比
     */
    private BigDecimal percentageFc;

    /**
     * 差价模式（0：不启用，1启用）
     */
    private Integer priceDifferenceMethod;

    private String parentAgentCode;
}
