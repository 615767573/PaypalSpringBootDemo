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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @describe : 创建订阅
 */
@Slf4j
public class CreateSubscriptionsImpl implements CreateSubscriptionsSrvice {
    public static Map<String, String> createSubscriptions(SubscriptionsBO subscriptions) throws PayPalRESTException {
        Map<String, String> map = new HashMap<>();
        ResponseEntity<String> responseEntity = RestTemplateUtils.post("https://api-m.paypal.com/v1/billing/subscriptions", new PayPalClient().setHttpHeaders(), subscriptions, String.class);
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
        RestTemplateUtils.post("https://api.sandbox.paypal.com/v1/billing/subscriptions/" + subscriptionsId + "/cancel", new PayPalClient().setHttpHeaders(), subscriptionsId, String.class);
          }

//    public static void main(String[] args) throws PayPalRESTException {
            // 取消订阅
//        cancelSubscriptions("I-GX16952AV2TL");
//    }


    public static void main(String[] args) throws PayPalRESTException {

//        querySubscriptionsCapture("I-RF4R4E1HH2WL");
        SubscriptionsBO subscriptions = new SubscriptionsBO();
        subscriptions.setPlan_id("P-2LE946636M743473EMMVKY5I");
        SimpleDateFormat simpledateformatsdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.forLanguageTag("Locale.US"));
        String start_time = simpledateformatsdf.format(System.currentTimeMillis() + 60000);
        subscriptions.setStart_time(start_time);
        ApplicationContextBO applicationContext = new ApplicationContextBO();
        applicationContext.setBrand_name("jooan");
        applicationContext.setCancel_url("https://usw2vas.jooaniot.com/cs-test/renew_pay_result.html");
        applicationContext.setReturn_url("https://usw2vas.jooaniot.com/cs-test/pay_result.html");
        applicationContext.setUser_action("SUBSCRIBE_NOW");
        applicationContext.setShipping_preference("GET_FROM_FILE");
        applicationContext.setLocale("ru-RU");
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

