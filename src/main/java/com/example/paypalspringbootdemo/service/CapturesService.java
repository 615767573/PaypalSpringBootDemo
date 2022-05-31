package com.example.paypalspringbootdemo.service;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Payer;

/**
 * @author hsl
 */
public interface CapturesService {

    /**
     * CaptureOrder（执行扣款）
     * <p>
     * 用户通过CreateOrder生成 approveUrl 跳转paypal支付成功后，只是授权，并没有将用户的钱打入我们的paypal账户，
     * 我们需要通过 CaptureOrder接口，
     * 将钱打入我的PayPal账户
     *
     * @param orderId
     *      * @return
     */
    public Payer CaptureOrder(String orderId);

    /**
     *  查询扣款详情
     *
     * @param CaptureOrderId 扣款id CaptureOrder生成
     * @return
     */
    public HttpResponse captureGet(String CaptureOrderId);

}
