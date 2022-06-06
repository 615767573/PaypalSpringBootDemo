package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class BillingCyclesBO implements Serializable {


    private PricingSchemeBO pricing_scheme;

    /**
     * 此结算周期的频率详细信息。
     */
    private FrequencyBO frequency;

    /**
     * 计费周期的任期类型。如果计划具有试用周期，则每个计划仅允许2个试用周期。 可能的值为：
     * REGULAR。定期的结算周期。
     * TRIAL。试用帐单周期。
     */
    private String tenure_type;

    /**
     * 在其他计费周期中，该周期的运行顺序。例如，试用计费周期的sequence值为，
     * 1而普通计费周期的的sequence值为2，因此试用周期在常规周期之前运行。
     */
    private Integer sequence;

    /**
     * 此计费周期执行的次数。试验结算周期才能执行的有限次数（间值1和999对total_cycles）。
     * 定期计费周期可以执行无限倍（值0对total_cycles）或有限次数（间值1和999对total_cycles）
     */
    private Integer total_cycles;
}

