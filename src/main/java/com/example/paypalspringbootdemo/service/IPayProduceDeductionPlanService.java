package com.example.paypalspringbootdemo.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.paypalspringbootdemo.domain.PayProduceDeductionPlan;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hsl
 * @since 2022-07-19
 */
public interface IPayProduceDeductionPlanService extends IService<PayProduceDeductionPlan> {

    /**
     * 创建续费计划
     *
     * @param payProduceDeductionPlan
     * @return
     */
    PayProduceDeductionPlan createProducePlan(PayProduceDeductionPlan payProduceDeductionPlan);
}
