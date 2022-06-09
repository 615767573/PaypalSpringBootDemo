package com.example.paypalspringbootdemo.service.Impl;

import com.example.paypalspringbootdemo.dto.OrderDto;
import com.example.paypalspringbootdemo.service.OrderService;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.SerializeException;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author hsl
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    public static final String CAPTURE = "CAPTURE";
    /**
     * 该标签将覆盖PayPal网站上PayPal帐户中的公司名称
     */
    public static final String BRANDNAME = "Supernote";
    /**
     * LOGIN。当客户单击PayPal Checkout时，客户将被重定向到页面以登录PayPal并批准付款。
     * BILLING。当客户单击PayPal Checkout时，客户将被重定向到一个页面，以输入信用卡或借记卡以及完成购买所需的其他相关账单信息
     * NO_PREFERENCE。当客户单击“ PayPal Checkout”时，将根据其先前的交互方式将其重定向到页面以登录PayPal并批准付款，或重定向至页面以输入信用卡或借记卡以及完成购买所需的其他相关账单信息使用PayPal。
     * 默认值：NO_PREFERENCE
     */
    public static final String LANDINGPAGE = "NO_PREFERENCE";
    /**
     * CONTINUE。将客户重定向到PayPal付款页面后，将出现“ 继续”按钮。当结帐流程启动时最终金额未知时，请使用此选项，并且您想将客户重定向到商家页面而不处理付款。
     * PAY_NOW。将客户重定向到PayPal付款页面后，出现“ 立即付款”按钮。当启动结帐时知道最终金额并且您要在客户单击“ 立即付款”时立即处理付款时，请使用此选项。
     */
    public static final String USERACTION = "CONTINUE";
    /**
     * GET_FROM_FILE。使用贝宝网站上客户提供的送货地址。
     * NO_SHIPPING。从PayPal网站编辑送货地址。推荐用于数字商品
     * SET_PROVIDED_ADDRESS。使用商家提供的地址。客户无法在PayPal网站上更改此地址
     */
    public static final String SHIPPINGPREFERENCE = "SET_PROVIDED_ADDRESS";

    @Autowired
    private PayPalClient payPalClient;


    /**
     * 生成订单主体信息
     */
    private OrderRequest buildRequestBody(String customId,String invoiceId) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(CAPTURE);

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName(BRANDNAME)
                .landingPage(LANDINGPAGE)
                .cancelUrl("https://www.example.com").returnUrl("https://www.example.com")
                .userAction(USERACTION)
                .shippingPreference(SHIPPINGPREFERENCE);
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
        @SuppressWarnings("serial")
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .description("流量卡套餐")
                .customId(customId)
                .invoiceId(invoiceId)
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode("USD")
                        .value("2.00")
                        // value = itemTotal + shipping + handling + taxTotal + shippingDiscount;
                        .amountBreakdown(new AmountBreakdown()
                                // itemTotal = Item(value × quantity) + Item(value × quantity)
                                .itemTotal(new Money().currencyCode("USD").value("2.00"))
                                .shipping(new Money().currencyCode("USD").value("0.00"))
                                .handling(new Money().currencyCode("USD").value("0.00"))
                                .taxTotal(new Money().currencyCode("USD").value("0.00"))
                                .shippingDiscount(new Money().currencyCode("USD").value("0.00"))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item().name("Supernote A6").description("30G流量卡套餐")
                                .unitAmount(new Money()
                                        .currencyCode("USD")
                                        .value("1.00"))
                                .quantity("1"));
                        add(new Item().name("云存储").description("云存储无限量套餐")
                                .unitAmount(new Money()
                                        .currencyCode("USD")
                                        .value("1.00"))
                                .quantity("1"));
                    }
                })
                .shippingDetail(new ShippingDetail()
                        .name(new Name().fullName("RATTA"))
                        .addressPortable(new AddressPortable()
                                .addressLine1("龙华新区")
                                .addressLine2("清湖")
                                .adminArea2("港之龙")
                                .adminArea1("深圳市")
                                .postalCode("518110")
                                .countryCode("CN")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    /**
     * 创建订单的方法
     *
     * @throws IOException
     */
    @SneakyThrows
    @Override
    public OrderDto createOrder() {
        OrderDto orderDto = new OrderDto();
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer", "return=representation");

        String customId = RandomStringUtils.randomAlphanumeric(16);
        String invoiceId = RandomStringUtils.randomAlphanumeric(16);

        request.requestBody(buildRequestBody(customId,invoiceId));
        orderDto.setCustomId(customId);
        orderDto.setInvoiceId(invoiceId);
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
        } catch (IOException e1) {
            try {
                log.error("第1次调用paypal订单创建失败");
                response = payPalClient.client().execute(request);
            } catch (Exception e) {
                try {
                    log.error("第2次调用paypal订单创建失败");
                    response = payPalClient.client().execute(request);
                } catch (Exception e2) {
                    log.error("第3次调用paypal订单创建失败，失败原因：{}", e2.getMessage());
                }
            }
        }
        String approve = "";
        if (response.statusCode() == 201) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            //需要进行扣款的orderId
            System.out.println("Order ID: " + response.result().id());
            orderDto.setOrderId(response.result().id());
            orderDto.setStatus(response.result().status());
            orderDto.setStatusCode(response.statusCode());
            log.info("Status Code = {}, Status = {}, OrderID = {}, Intent = {}", response.statusCode(), response.result().status(), response.result().id(), response.result().checkoutPaymentIntent());
            for (LinkDescription link : response.result().links()) {
                log.info("Links-{}: {}    \tCall Type: {}", link.rel(), link.href(), link.method());
                if (link.rel().equals("approve")) {
                    approve = link.href();
                    orderDto.setApproveUlr(approve);
                }
            }
            String totalAmount = response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode() + ":" + response.result().purchaseUnits().get(0).amountWithBreakdown().value();
            orderDto.setTotalAmount(totalAmount);
            log.info("Total Amount: {}", totalAmount);
            String json = new JSONObject(new Json().serialize(response.result())).toString(4);
            log.info("createOrder response body: {}", json);
        }
        return orderDto;
    }

    @SneakyThrows
    @Override
    public OrderDto ordersGet(String orderID) {
        // 订单id，CreateOrder 生成
        OrdersGetRequest request = new OrdersGetRequest(orderID);
        OrderDto orderDto = new OrderDto();
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
        } catch (Exception e) {
            try {
                System.out.println("调用paypal订单查询失败，链接异常1");
                response = payPalClient.client().execute(request);
            } catch (Exception e2) {
                try {
                    System.out.println("调用paypal订单查询失败，链接异常2");
                    response = payPalClient.client().execute(request);
                } catch (Exception e3) {
                    System.out.println("调用paypal订单查询失败，链接异常3");
                    System.out.println(e3.getMessage());
                }
            }
        }
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status: " + response.result().status());
        System.out.println("Order id: " + response.result().id());
        orderDto.setOrderId(response.result().id());
        orderDto.setStatus(response.result().status());
        orderDto.setStatusCode(response.statusCode());
        if (response.result().purchaseUnits().get(0).payments() != null) {
            List<Capture> captures = response.result().purchaseUnits().get(0).payments().captures();
            orderDto.setCaptures(captures);
            if (captures != null) {
                for (Capture capture : captures) {
                    System.out.println("\t订单编号= " + capture.invoiceId() + "\tCapture Id= " + capture.id() + "\tCapture status= " + capture.status() + "\tCapture amount= " + capture.amount().currencyCode() + ":" + capture.amount().value());
                }
            }
            List<Refund> refunds = response.result().purchaseUnits().get(0).payments().refunds();
            orderDto.setRefunds(refunds);
            if (refunds != null) {
                for (Refund refund : refunds) {
                    System.out.println("\t售后编号= " + refund.invoiceId() + "\tRefund Id= " + refund.id() + "\tRefund status= " + refund.status() + "\tRefund amount= " + refund.amount().currencyCode() + ":" + refund.amount().value());
                }
            }

        }
        System.out.println("Links: ");
        for (com.paypal.orders.LinkDescription link : response.result().links()) {
            System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
        }

        System.out.println("Full response body:");
        String json = null;
        json = new JSONObject(new Json().serialize(response.result())).toString(4);
        System.out.println(json);
        return orderDto;
    }
}
