package com.example.paypalspringbootdemo.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.paypalspringbootdemo.domain.IotDeviceSerialsVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface AliClientService extends IService<IotDeviceSerialsVO> {


   public void getDeviceRegion();

   //    @SneakyThrows
   void aliiotClient(String deviceName, List<String> errorDervice);



   void commodityQuery(String deviceName, List<String> errorDervice);


   void cloudstorageCommodityBuy(String deviceName, List<String> errorDervice);


   void freecloudstorageConsume(String deviceName, List<String> errorDervice);
}
