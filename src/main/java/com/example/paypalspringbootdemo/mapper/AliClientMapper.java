package com.example.paypalspringbootdemo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.paypalspringbootdemo.domain.IotDeviceSerialsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AliClientMapper extends BaseMapper<IotDeviceSerialsVO> {

   List<IotDeviceSerialsVO> getDeviceIdList(String lv_pk,int page);
}
