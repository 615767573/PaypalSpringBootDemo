package com.example.paypalspringbootdemo.controller;


import com.example.paypalspringbootdemo.service.AliClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: hsl
 * @Date: Created in 20:07 2022/5/27
 * @Desprition:Welcome 控制类
 */
@RestController
@RequestMapping(path = "/v1/aliClient")
@CrossOrigin
public class AliClientController {

    @Autowired
    private AliClientService aliClientService;

    @ApiOperation(value = "countDevice")
    @RequestMapping(value = "/countDevice", method = RequestMethod.POST)
    public String countDevice() {
        aliClientService.getDeviceRegion();
        return "scucess";
    }


    @ApiOperation(value = "deviceResion")
    @RequestMapping(value = "/deviceResion", method = RequestMethod.POST)
    public String deviceResion() {
        aliClientService.aliiotClient(null,null);
        return "scucess";
    }

    /**
     * 查询阿里套餐
     *
     * @return
     */
    @ApiOperation(value = "commodity Query")
    @RequestMapping(value = "commodity/query", method = RequestMethod.POST)
    public String queryAli() {
        aliClientService.commodityQuery(null,null);
        return "scucess";
    }

    /**
     * 购买阿里套餐
     *
     * @return
     */
    @ApiOperation(value = "commodity buy")
    @RequestMapping(value = "commodity/buy", method = RequestMethod.POST)
    public String cloudstorageCommodityBuy() {
        aliClientService.cloudstorageCommodityBuy(null,null);
        return "scucess";
    }


    /**
     * 免费领取阿里套餐
     *
     * @return
     */
    @ApiOperation(value = "commodity consume")
    @RequestMapping(value = "commodity/consume", method = RequestMethod.POST)
    public String freecloudstorageConsume() {
        aliClientService.freecloudstorageConsume(null,null);
        return "scucess";
    }
}
