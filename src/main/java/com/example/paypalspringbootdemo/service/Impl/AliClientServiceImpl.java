package com.example.paypalspringbootdemo.service.Impl;

import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.paypalspringbootdemo.domain.IoTApiRequest;
import com.example.paypalspringbootdemo.domain.IotDeviceSerialsVO;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.example.paypalspringbootdemo.dto.GetTokenResponse;
import com.example.paypalspringbootdemo.mapper.AliClientMapper;
import com.example.paypalspringbootdemo.service.AliClientService;
import com.example.paypalspringbootdemo.utils.JsonUtils;
import com.example.paypalspringbootdemo.utils.SyncApiClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;

@Slf4j
@Service
public class AliClientServiceImpl extends ServiceImpl<AliClientMapper, IotDeviceSerialsVO> implements AliClientService {
    private static final String API_VERSION = "1.0.1";
    private static final String ALI_HOST = "api.link.aliyun.com";
    private static final String GET_CLOUD_TOKEN_URL = "/cloud/token";

    @Autowired
    private AliClientMapper aliClientMapper;


    //    ExecutorService executor = Executors.newCachedThreadPool();
    private static ExecutorService executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 10,
            Runtime.getRuntime().availableProcessors() * 30,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue(Integer.MAX_VALUE),
            new ThreadPoolExecutor.DiscardOldestPolicy());

//    AtomicInteger shanghai = new AtomicInteger();
//    AtomicInteger useast = new AtomicInteger();
//    AtomicInteger southeast = new AtomicInteger();
//    AtomicInteger germany = new AtomicInteger();

    LongAdder shanghai = new LongAdder();
    LongAdder useast = new LongAdder();
    LongAdder southeast = new LongAdder();
    LongAdder germany = new LongAdder();
    LongAdder errorCount = new LongAdder();

    private static SyncApiClient syncApiClient() {
        HttpClientBuilderParams builderParams = new HttpClientBuilderParams();
        builderParams.setAppKey("25067735");
        builderParams.setAppSecret("3c130ac233eaf9f1e850c4507c977b8f");
        return new SyncApiClient(builderParams);
    }
    @Override
    public void getDeviceRegion() {


        List<IotDeviceSerialsVO> iotDeviceSerialsVOS = null;
        List<String> errorDervice = new ArrayList<>();
        for (int j = 1; j <= 2041; j++) {
            iotDeviceSerialsVOS = aliClientMapper.getDeviceIdList("a1po0r02m1v", (j-1)*100);
//            Page<IotDeviceSerialsVO> userPage = new Page<>(j, 200);
//            EntityWrapper<IotDeviceSerialsVO> wrapper = new EntityWrapper<>();
//            wrapper.eq("lv_pk", "a18jj7MB3W9");
//            wrapper.eq("solution", "ALILV");
//            iotDeviceSerialsVOS = aliClientMapper.selectPage(userPage, wrapper);
            log.info("page : {}", j);
            if(!CollectionUtils.isEmpty(iotDeviceSerialsVOS)){
                iotDeviceSerialsVOS.stream().forEach(deviceId -> CompletableFuture.supplyAsync((Supplier<String>) () -> {
                    aliiotClient(deviceId.getLvDn(),errorDervice);
                    return "success";
                }, executor).exceptionally(e -> {
                    errorCount.increment();
                    return "false";
                }));
            }
        }
        log.info("sucess total--------{} , errorDervice size {}:  shanghai: {}, useast : {}, southeast: {},germany: {}",errorCount,errorDervice.size() ,shanghai,useast,southeast,germany);
    }

    //    @SneakyThrows
    private void aliiotClient(String deviceName, List<String> errorDervice) {
        //-----------------------------------------------------
        IoTApiRequest request1 = new IoTApiRequest();
        request1.setApiVer("1.0.1");
        request1.putParam("productKey", "a1po0r02m1v");
        request1.putParam("deviceName", deviceName);


        ApiResponse response = null;
        try {
            log.warn("deviceName:{}", deviceName);
            response = syncApiClient().postBody(ALI_HOST, "/living/cloud/device/region/get", request1, true);
            log.warn("coed: {}", response.getCode());
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException : {}", e);
            errorDervice.add(deviceName);
        }

        if (200 == response.getCode()) {
            Map body = JsonUtils.jsonToPojo(new String(response.getBody()), Map.class);

            if ("200".equals(body.get("code").toString())) {
                String data = body.get("data").toString();
                log.info(" body data: {}" ,body.get("data").toString());
                switch (data) {
                    case "cn-shanghai":
                        shanghai.increment();
                        break;
                    case "us-east-1":
                        useast.increment();
                        break;
                    case "ap-southeast-1":
                        southeast.increment();
                        break;
                    case "germany":
                        germany.increment();
                        break;
                }
                log.info("shanghai : {}   useast: {}, southeast: {}, germany: {}", shanghai, useast, southeast, germany);
            }
        } else {
            log.error("errorDervice : {}", deviceName);
            errorDervice.add(deviceName);
        }


    }

    @SneakyThrows
    private String getAliToken() {
        IoTApiRequest request = new IoTApiRequest();
        //设置API的版本
        //调用/cloud/token接口时，不需要传CloudToken，请参见云端资源服务中/cloud/token接口的代码示例
        //调用其他接口（除/cloud/token接口外），都需要先调用/cloud/token接口获取到token，再传入ApiVer和CloudToken，才可以正常调用
        request.setApiVer(API_VERSION);
        //设置接口的参数
        request.putParam("grantType", "project");
        request.putParam("res", "a1236Xoy3t8OSkXE");
//        request.putParam("res", "a123xKpGr03dN1nB");
        //请求参数域名、path、request
        //host地址  中国站：api.link.aliyun.com  国际站：api-iot.ap-southeast-1.aliyuncs.com
        ApiResponse response = syncApiClient().postBody(ALI_HOST, GET_CLOUD_TOKEN_URL, request, true);
        String token = "";
        if (200 == response.getCode()) {
            Map body = JsonUtils.jsonToPojo(new String(response.getBody()), Map.class);
            Map data = (LinkedHashMap) body.get("data");
            token = data.get("cloudToken").toString();
            System.out.println(data.get("cloudToken"));
        }
        System.out.println(
                "response code = " + response.getCode() + " response = " + new String(response.getBody(), "UTF-8"));
        /*String a123xKpGr03dN1nB = AliIotUtils
                .getCloudToken("25067735", "3c130ac233eaf9f1e850c4507c977b8f", "a123xKpGr03dN1nB");*/
//        String identity = getIdentity("87e07895266032718a9e5d9e9f96857a ", "30848003 ", a123xKpGr03dN1nB);
//        String openId = getOpenId(identity, "30848003");
        System.out.println(token);
        return token;
    }


}
