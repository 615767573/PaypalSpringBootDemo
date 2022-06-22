package com.example.paypalspringbootdemo.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.paypalspringbootdemo.domain.IotDeviceSerialsVO;

/**
 * @author Administrator
 */
public interface AliClientService extends IService<IotDeviceSerialsVO> {


   public void getDeviceRegion();
}
