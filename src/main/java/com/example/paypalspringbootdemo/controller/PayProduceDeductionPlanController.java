package com.example.paypalspringbootdemo.controller;


import com.example.paypalspringbootdemo.domain.PayProduceDeductionPlan;
import com.example.paypalspringbootdemo.service.IPayProduceDeductionPlanService;
import com.example.paypalspringbootdemo.service.RefundOrderService;
import com.paypal.http.HttpResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hsl
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/pay-produce-deduction-plan")
public class PayProduceDeductionPlanController {

    @Autowired
    private IPayProduceDeductionPlanService iPayProduceDeductionPlanService;

    @ApiOperation(value = "创建自动续费扣款计划")
    @RequestMapping(value = "/createProducePlan", method = RequestMethod.POST)
    public PayProduceDeductionPlan refundsGet(@RequestBody PayProduceDeductionPlan payProduceDeductionPlan) {
        return iPayProduceDeductionPlanService.createProducePlan(payProduceDeductionPlan);
    }

}
