package com.example.paypalspringbootdemo.service;

import com.paypal.http.HttpResponse;
import com.paypal.payments.Refund;


/**
 * @author hsl
 */
public interface RefundOrderService {

    /**
     * RefundOrder（申请退款）
     *
     * @param captureOrderId Capture订单号
     * @return
     */
    public HttpResponse<Refund> refundOrder(String captureOrderId);


    /**
     * RefundsGet（查询退款详情）
     *
     * @param refundOrderId 退款id RefundOrder生成
     * @return
     */
    public HttpResponse<Refund> refundGet(String refundOrderId);

}
