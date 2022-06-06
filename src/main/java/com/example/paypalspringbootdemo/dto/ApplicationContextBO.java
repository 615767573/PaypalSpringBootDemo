package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class ApplicationContextBO implements Serializable {
    /**
     * 该标签将覆盖PayPal网站上PayPal帐户中的公司名称
     */
    private String brand_name;

    /**
     * 贝宝付款体验显示的BCP 47格式的页面区域设置。PayPal支持五个字符的代码。
     * 例如，da-DK，he-IL，id-ID，ja-JP，no-NO，pt-BR，ru-RU，sv-SE，th-TH，zh-CN，zh-HK，或zh-TW。
     */
    private String locale;

    /**
     * 送货地址的来源位置。 可能的值为：
     * GET_FROM_FILE。在贝宝网站上获得客户提供的送货地址。
     * NO_SHIPPING。从PayPal网站编辑送货地址。推荐用于数字商品。
     * SET_PROVIDED_ADDRESS。获取商家提供的地址。客户无法在PayPal网站上更改此地址。如果商家未通过地址，则客户可以在PayPal页面上选择地址。
     * 默认值：GET_FROM_FILE。
     */
    private String shipping_preference;

    /**
     * 将标签名称配置为订阅同意体验Continue或Subscribe Now为订阅同意体验配置。 可能的值为：
     * CONTINUE。将客户重定向到PayPal订阅同意页面后，将出现“继续”按钮。当您要控制订阅的激活并且不希望PayPal激活订阅时，请使用此选项。
     * SUBSCRIBE_NOW。将客户重定向到PayPal订阅同意页面后，将显示立即订阅按钮。当您希望贝宝激活订阅时，请使用此选项。
     * 默认值：SUBSCRIBE_NOW
     */
    private String user_action;


    /**
     * 客户和商家的付款首选项。目前仅支持PAYPAL付款方式。
     */
    private PaymentMethodBO payment_method;

    /**
     * 客户批准付款后将客户重定向到的URL
     */
    private String return_url;

    /**
     * 客户取消付款后，将客户重定向到的URL
     */
    private String cancel_url;

}

