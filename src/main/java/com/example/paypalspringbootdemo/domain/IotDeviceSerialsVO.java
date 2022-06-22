package com.example.paypalspringbootdemo.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_iot_device_serials")
public class IotDeviceSerialsVO {
    private String serialNumber;
    private String batchNo;
    private String solution;
    private String mKey;
    private String pKey;
    private String model;
    private String uid;
    private String lvPk;
    private String lvPs;
    private String lvDn;
    private String lvDs;
    private Integer status;
    private String burnAccount;
    private Date generateTime;
    private Date allocationTime;
    private Date burnTime;
    private Integer burnDate;
    private String version;
    private String sn;
    private String key;
    private String mac;
    private String ctei;
    private String bindCode;
}
