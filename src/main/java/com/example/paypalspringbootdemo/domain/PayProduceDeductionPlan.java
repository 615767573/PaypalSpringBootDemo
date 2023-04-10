package com.example.paypalspringbootdemo.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author hsl
 * @since 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pay_produce_deduction_plan")
public class PayProduceDeductionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    private Integer id;

    /**
     * 计划编号
     */
    private String planNo;

    /**
     * 续费计划名称
     */
    private String planName;

    /**
     * 描述
     */
    private String description;

    /**
     * 订阅收费时间间隔(DAY,WEEK,MONTH,YEAR)
     */
    private String intervalUnit;

    /**
     * 订阅者计费之后的时间间隔数(MONTH/1个月)
     */
    private Integer intervalCount;

    /**
     * 货币(USD)
     */
    private String currencyCode;

    /**
     * 每期扣款费用
     */
    private BigDecimal value;

    //首次扣款费用
    private BigDecimal firstDiscountAmount;

    /**
     * 扣款次数/0是无限制
     */
    private Integer totalCycles;

    /**
     * 在其他计费周期中，该周期的运行顺序
     */
    private Integer sequence;

    /**
     * REGULAR/定期的结算周期,TRIAL/试用帐单周期。
     */
    private String tenureType;

    private String setupFee;

    private String country;

    /**
     * 产品id
     */
    private Integer productId;

    private LocalDateTime createTime;

    private LocalDateTime endTime;

    private String is_cyclepay;

    private String company_name;



}
