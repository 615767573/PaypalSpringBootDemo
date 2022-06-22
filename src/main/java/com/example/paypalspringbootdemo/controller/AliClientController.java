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

    @ApiOperation(value = "s")
    @RequestMapping(value = "/countDevice", method = RequestMethod.POST)
    public String countDevice() {
        aliClientService.getDeviceRegion();
        return "scucess";
    }
}
