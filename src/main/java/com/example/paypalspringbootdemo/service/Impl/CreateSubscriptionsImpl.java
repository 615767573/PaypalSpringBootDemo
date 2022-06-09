package com.example.paypalspringbootdemo.service.Impl;

import com.example.paypalspringbootdemo.service.CreateSubscriptionsSrvice;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.example.paypalspringbootdemo.utils.RestTemplateUtils;
import com.paypal.api.payments.Event;
import com.paypal.base.rest.PayPalRESTException;
import com.example.paypalspringbootdemo.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @describe : 创建订阅
 */
@Slf4j
public class CreateSubscriptionsImpl implements CreateSubscriptionsSrvice {
    public static Map<String, String> createSubscriptions(SubscriptionsBO subscriptions) throws PayPalRESTException {
        Map<String, String> map = new HashMap<>();
        ResponseEntity<String> responseEntity = RestTemplateUtils.post("https://api.sandbox.paypal.com/v1/billing/subscriptions", new PayPalClient().setHttpHeaders(), subscriptions, String.class);
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        JSONArray object = (JSONArray) jsonObject.get("links");
        String url = "";
        for (Object o : object) {
            JSONObject jsonObj = (JSONObject) o;
            if (jsonObj.get("rel").toString().equals("approve")) {
                url = jsonObj.get("href").toString();
            }
        }
        map.put("id", jsonObject.get("id").toString());
        map.put("url", url);
        return map;
    }

    /**
     * 查询订阅的授权付款
     *
     * @param subscriptionsId 定期付款ID
     * @throws PayPalRESTException
     */
    public static void querySubscriptionsCapture(String subscriptionsId) throws PayPalRESTException {
        ResponseEntity<String> responseEntity = RestTemplateUtils.post("https://api.sandbox.paypal.com/v1/billing/subscriptions/" + subscriptionsId + "/capture", new PayPalClient().setHttpHeaders(), "", String.class);
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        JSONArray object = (JSONArray) jsonObject.get("links");
        log.info("SubscriptionsCapture", jsonObject);
    }




    /**
     * 取消订阅
     *
     * @param subscriptionsId
     * @throws PayPalRESTException
     */
    public static void cancelSubscriptions(String subscriptionsId) throws PayPalRESTException {
        RestTemplateUtils.post("https://api.sandbox.paypal.com/v1/billing/subscriptions/" + subscriptionsId + "/cancel", new PayPalClient().setHttpHeaders(), "", String.class);
          }

//    public static void main(String[] args) throws PayPalRESTException {
            // 取消订阅
//        cancelSubscriptions("I-GX16952AV2TL");
//    }


    public static void main(String[] args) throws PayPalRESTException {

//        querySubscriptionsCapture("I-KHPHBU2NYXTB");
        SubscriptionsBO subscriptions = new SubscriptionsBO();
        subscriptions.setPlan_id("P-5KW14887VM393702PMKPSBRI");
        subscriptions.setStart_time("2022-06-07T18:01:00Z");
        ApplicationContextBO applicationContext = new ApplicationContextBO();
        applicationContext.setBrand_name("jooan");
        applicationContext.setCancel_url("https://www.example.com");
        applicationContext.setReturn_url("https://www.example.com");
        applicationContext.setLocale("zh-CN");
        applicationContext.setUser_action("SUBSCRIBE_NOW");
        applicationContext.setShipping_preference("GET_FROM_FILE");
        PaymentMethodBO paymentMethod = new PaymentMethodBO();
        paymentMethod.setPayee_preferred("UNRESTRICTED");
        paymentMethod.setPayer_selected("PAYPAL");
        applicationContext.setPayment_method(paymentMethod);
        subscriptions.setApplication_context(applicationContext);
        Map<String, String> map = createSubscriptions(subscriptions);
        System.out.println(map.get("id"));
        System.out.println(map.get("url"));
    }

    // https://developer.paypal.com/docs/subscriptions/integrate/#5-go-live
    // https://developer.paypal.com/docs/api/subscriptions/v1/


}

