package com.example.paypalspringbootdemo.service.Impl;

import com.example.paypalspringbootdemo.service.RefundOrderService;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.payments.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author hsl
 */
@Slf4j
@Service
public class RefundOrderImpl implements RefundOrderService {

    @Autowired
    private PayPalClient payPalClient;

    @Override
    @SneakyThrows
    public HttpResponse<Refund> refundOrder(String orderId) {

        OrdersGetRequest ordersGetRequest = new OrdersGetRequest(orderId);
        HttpResponse<com.paypal.orders.Order> ordersGetResponse = null;
        try {
            ordersGetResponse = payPalClient.client().execute(ordersGetRequest);
        } catch (Exception e) {
            try {
                log.error("第1次调用paypal订单查询失败");
                ordersGetResponse = payPalClient.client().execute(ordersGetRequest);
            } catch (Exception e2) {
                try {
                    log.error("第2次调用paypal订单查询失败");
                    ordersGetResponse = payPalClient.client().execute(ordersGetRequest);
                } catch (Exception e3) {
                    log.error("第3次调用paypal订单查询失败，失败原因：{}", e3.getMessage());
                }
            }
        }
        String captureId = ordersGetResponse.result().purchaseUnits().get(0).payments().captures().get(0).id();
        CapturesRefundRequest request = new CapturesRefundRequest(captureId);
        request.prefer("return=representation");
        request.requestBody(buildRequestBody());
        HttpResponse<Refund> response = null;
        try {
            response = payPalClient.client().execute(request);
        } catch (IOException e) {
            try {
                log.error("第1次调用paypal退款申请失败");
                response = payPalClient.client().execute(request);
            } catch (Exception e1) {
                try {
                    log.error("第2次调用paypal退款申请失败");
                    response = payPalClient.client().execute(request);
                } catch (Exception e2) {
                    log.error("第3次调用paypal退款申请失败，失败原因 {}", e2.getMessage());
                }
            }
        }
        log.info("Status Code = {}, Status = {}, RefundID = {}", response.statusCode(), response.result().status(), response.result().id());
        if ("COMPLETED".equals(response.result().status())) {
            //进行数据库操作，修改状态为已退款（配合回调和退款查询确定退款成功）
            log.info("退款成功");
        }
        for (com.paypal.payments.LinkDescription link : response.result().links()) {
            log.info("Links-{}: {}    \tCall Type: {}", link.rel(), link.href(), link.method());
        }
        String json = new JSONObject(new Json().serialize(response.result())).toString(4);
        log.info("refundOrder response body: {}", json);
        return response;
    }

    @Override
    @SneakyThrows
    public HttpResponse<Refund> refundGet(String refundOrderId) {
        RefundsGetRequest request = new RefundsGetRequest("退款id RefundOrder生成");
        HttpResponse<Refund> response = payPalClient.client().execute(request);
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status: " + response.result().status());
        System.out.println("Refund Id: " + response.result().id());
        System.out.println("Links: ");
        for (LinkDescription link : response.result().links()) {
            System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
        }
        System.out.println("Full response body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        return response;
    }

    /**
     * 创建退款请求体
     */
    public RefundRequest buildRequestBody() {
        RefundRequest refundRequest = new RefundRequest();
        Money money = new Money();
        money.currencyCode("USD");
        money.value("40.00");
        refundRequest.amount(money);
        // 售后编号
//        refundRequest.invoiceId("T202005230002");
//        refundRequest.noteToPayer("7天无理由退款");
        return refundRequest;
    }
}
