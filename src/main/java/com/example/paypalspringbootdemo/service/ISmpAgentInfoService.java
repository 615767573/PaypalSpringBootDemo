package com.example.paypalspringbootdemo.service;


import com.baomidou.mybatisplus.service.IService;
import com.example.paypalspringbootdemo.domain.PayProduceDeductionPlan;
import com.example.paypalspringbootdemo.domain.SmpAgentInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hsl
 * @since 2023-02-17
 */
public interface ISmpAgentInfoService extends IService<SmpAgentInfo> {

    void query();
}
