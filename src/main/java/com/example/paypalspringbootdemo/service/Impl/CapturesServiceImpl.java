package com.example.paypalspringbootdemo.service.Impl;

import com.example.paypalspringbootdemo.service.CapturesService;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.SerializeException;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import com.paypal.payments.CapturesGetRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author hsl
 */
@Service
@Slf4j
public class CapturesServiceImpl implements CapturesService {

    @Autowired
    private PayPalClient payPalClient;

    @Override
    public Payer CaptureOrder(String orderId) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
        } catch (IOException e1) {
            try {
                log.error("第1次调用paypal扣款失败");
                response = payPalClient.client().execute(request);
            } catch (Exception e) {
                try {
                    log.error("第2次调用paypal扣款失败");
                    response = payPalClient.client().execute(request);
                } catch (Exception e2) {
                    log.error("第3次调用paypal扣款失败，失败原因 {}", e2.getMessage());
                }
            }
        }
        log.info("Status Code = {}, Status = {}, OrderID = {}", response.statusCode(), response.result().status(), response.result().id());
        for (LinkDescription link : response.result().links()) {
            log.info("Links-{}: {}    \tCall Type: {}", link.rel(), link.href(), link.method());
        }
        for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
            for (Capture capture : purchaseUnit.payments().captures()) {
                log.info("Capture id: {}", capture.id());
                log.info("status: {}", capture.status());
                log.info("invoice_id: {}", capture.invoiceId());
                if ("COMPLETED".equals(capture.status())) {
                    //进行数据库操作，修改订单状态为已支付成功，尽快发货（配合回调和CapturesGet查询确定成功）
                    log.info("支付成功,状态为=COMPLETED");
                }
                if ("PENDING".equals(capture.status())) {
                    log.info("status_details: {}", capture.captureStatusDetails().reason());
                    String reason = "PENDING";
                    if (capture.captureStatusDetails() != null && capture.captureStatusDetails().reason() != null) {
                        reason = capture.captureStatusDetails().reason();
                    }
                    //进行数据库操作，修改订单状态为已支付成功，但触发了人工审核，请审核通过后再发货（配合回调和CapturesGet查询确定成功）
                    log.info("支付成功,状态为=PENDING : {}", reason);
                }
            }
        }
        Payer buyer = response.result().payer();
        log.info("Buyer Email Address: {}", buyer.email());
        log.info("Buyer Name: {} {}", buyer.name().givenName(), buyer.name().surname());
        String json = null;
        try {
            json = new JSONObject(new Json().serialize(response.result())).toString(4);
        } catch (SerializeException e) {
            e.printStackTrace();
        }
        log.info("captureOrder response body: {}", json);
        return buyer;
    }

    @Override
    public HttpResponse captureGet(String CaptureOrderId) {
        CapturesGetRequest request = new CapturesGetRequest("扣款id， CaptureOrder生成");

        HttpResponse<com.paypal.payments.Capture> response = null;
        try {
            response = payPalClient.client().execute(request);
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Capture ids: " + response.result().id());
            System.out.println("Links: ");
            for (com.paypal.payments.LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        } catch (IOException e) {
            log.error("captureGet : {}", e);
        }
        return response;
    }


}
