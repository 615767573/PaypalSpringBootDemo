package com.example.paypalspringbootdemo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.paypalspringbootdemo.dto.*;
import com.example.paypalspringbootdemo.service.CreatePlanService;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.example.paypalspringbootdemo.utils.RestTemplateUtils;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;

/**
 * @describe :  创建计费计划
 */
public class CreatePlanImpl implements CreatePlanService {
    public static String createPlan(PlanBO plan) throws PayPalRESTException {
        ResponseEntity<String> responseEntity = RestTemplateUtils.post("https://api-m.paypal.com/v1/billing/plans", new PayPalClient().setHttpHeaders(), plan, String.class);
        JSONObject jsonObject = JSONArray.parseObject(responseEntity.getBody());
        return jsonObject.get("id").toString();
    }

    public static void main(String[] args) throws PayPalRESTException {
        PlanBO plan = new PlanBO();
        //CreateProducts获取的产品id
        plan.setProduct_id("90000009");
        plan.setName("30天云存储套餐扣款计划");
        plan.setDescription("30天云存储产品自动续费规则，每个月自动扣款。");
        BillingCyclesBO billingCycles = new BillingCyclesBO();
        FrequencyBO frequency = new FrequencyBO();
        //设置付款频率
        frequency.setInterval_unit("DAY");
        frequency.setInterval_count(1);
        billingCycles.setFrequency(frequency);

        PricingSchemeBO pricingScheme = new PricingSchemeBO();
        FixedPriceBO fixedPrice = new FixedPriceBO();
        fixedPrice.setCurrency_code("USD");
        fixedPrice.setValue("1");
        pricingScheme.setFixed_price(fixedPrice);
        billingCycles.setPricing_scheme(pricingScheme);

        billingCycles.setTotal_cycles(6);
        billingCycles.setSequence(1);
        billingCycles.setTenure_type("REGULAR");
        BillingCyclesBO[] billingCyclesArray = {billingCycles};
        plan.setBilling_cycles(billingCyclesArray);


        PaymentPreferencesBO paymentPreferences = new PaymentPreferencesBO();
        SetupFeeBO setupFee = new SetupFeeBO();
        setupFee.setCurrency_code("USD");
        setupFee.setValue("2");
        paymentPreferences.setSetup_fee(setupFee);
        plan.setPayment_preferences(paymentPreferences);
        String id = createPlan(plan);
        System.out.println(id);
    }

}

