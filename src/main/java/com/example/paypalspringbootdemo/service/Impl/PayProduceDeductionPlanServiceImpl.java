package com.example.paypalspringbootdemo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.paypalspringbootdemo.domain.PayProduceDeductionPlan;
import com.example.paypalspringbootdemo.dto.*;
import com.example.paypalspringbootdemo.mapper.PayProduceDeductionPlanMapper;
import com.example.paypalspringbootdemo.service.IPayProduceDeductionPlanService;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.example.paypalspringbootdemo.utils.RestTemplateUtils;
import com.paypal.base.rest.PayPalRESTException;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hsl
 * @since 2022-07-19
 */
@Service
public class PayProduceDeductionPlanServiceImpl extends ServiceImpl<PayProduceDeductionPlanMapper, PayProduceDeductionPlan> implements IPayProduceDeductionPlanService {


    @SneakyThrows
    @Override
    public PayProduceDeductionPlan createProducePlan(PayProduceDeductionPlan payProduceDeductionPlan) {

        PlanBO plan = new PlanBO();
        //CreateProducts获取的产品id
        plan.setProduct_id(String.valueOf(payProduceDeductionPlan.getProductId()));
        plan.setName(payProduceDeductionPlan.getPlanName());
        plan.setDescription(payProduceDeductionPlan.getDescription());
        BillingCyclesBO billingCycles = new BillingCyclesBO();
        FrequencyBO frequency = new FrequencyBO();
        //设置付款频率
        frequency.setInterval_unit(payProduceDeductionPlan.getIntervalUnit());
        frequency.setInterval_count(payProduceDeductionPlan.getIntervalCount());
        billingCycles.setFrequency(frequency);

        PricingSchemeBO pricingScheme = new PricingSchemeBO();
        FixedPriceBO fixedPrice = new FixedPriceBO();
        fixedPrice.setCurrency_code(payProduceDeductionPlan.getCurrencyCode());
        fixedPrice.setValue(String.valueOf(payProduceDeductionPlan.getValue()));
        pricingScheme.setFixed_price(fixedPrice);
        billingCycles.setPricing_scheme(pricingScheme);

        // 扣款次数 0无限次
        billingCycles.setTotal_cycles(payProduceDeductionPlan.getTotalCycles());
        billingCycles.setSequence(payProduceDeductionPlan.getSequence());
//        REGULAR。定期的结算周期。
        billingCycles.setTenure_type(payProduceDeductionPlan.getTenureType());
        BillingCyclesBO[] billingCyclesArray = {billingCycles};
        plan.setBilling_cycles(billingCyclesArray);


//        订阅的付款首选项。
        PaymentPreferencesBO paymentPreferences = new PaymentPreferencesBO();
        SetupFeeBO setupFee = new SetupFeeBO();
        setupFee.setCurrency_code(payProduceDeductionPlan.getCurrencyCode());
        setupFee.setValue(String.valueOf(payProduceDeductionPlan.getFirstDiscountAmount()));
        paymentPreferences.setSetup_fee(setupFee);
        plan.setPayment_preferences(paymentPreferences);
        String id = createPlan(plan);
        payProduceDeductionPlan.setPlanNo(id);
        this.insertOrUpdate(payProduceDeductionPlan);
        payProduceDeductionPlan.setCreateTime(LocalDateTime.now());
        payProduceDeductionPlan.setEndTime(LocalDateTime.now());
        return payProduceDeductionPlan;
    }


    public static String createPlan(PlanBO plan) throws PayPalRESTException {
        ResponseEntity<String> responseEntity = RestTemplateUtils.post("https://api.sandbox.paypal.com/v1/billing/plans", new PayPalClient().setHttpHeaders(), plan, String.class);
        JSONObject jsonObject = JSONArray.parseObject(responseEntity.getBody());
        return jsonObject.get("id").toString();
    }
}
