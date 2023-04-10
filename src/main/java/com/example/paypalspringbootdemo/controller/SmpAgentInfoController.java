package com.example.paypalspringbootdemo.controller;


import com.example.paypalspringbootdemo.domain.PayProduceDeductionPlan;
import com.example.paypalspringbootdemo.domain.SmpAgentInfo;
import com.example.paypalspringbootdemo.service.ISmpAgentInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hsl
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/smp-agent-info")
public class SmpAgentInfoController {

    @Autowired
    private ISmpAgentInfoService iSmpAgentInfoService;

    @ApiOperation(value = "创建自动续费扣款计划")
    @RequestMapping(value = "/angetCode", method = RequestMethod.POST)
    public String angetCode() {
        iSmpAgentInfoService.query();
        return "1";
    }

}
