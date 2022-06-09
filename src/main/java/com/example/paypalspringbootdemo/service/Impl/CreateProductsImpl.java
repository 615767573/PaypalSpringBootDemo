package com.example.paypalspringbootdemo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.paypalspringbootdemo.dto.ProductsBO;
import com.example.paypalspringbootdemo.service.CreateProductsService;
import com.example.paypalspringbootdemo.utils.PayPalClient;
import com.example.paypalspringbootdemo.utils.RestTemplateUtils;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;

/**
 * @describe : 创建产品
 */
public class CreateProductsImpl implements CreateProductsService {
    public static String CreateProducts(ProductsBO products) throws PayPalRESTException {
        ResponseEntity<String> responseEntity = RestTemplateUtils.post("https://api.sandbox.paypal.com/v1/catalogs/products", new PayPalClient().setHttpHeaders(), products, String.class);
        JSONObject jsonObject = JSONArray.parseObject(responseEntity.getBody());
        return jsonObject.get("id").toString();
    }

    public static void main(String[] args) throws PayPalRESTException {
        ProductsBO products = new ProductsBO();
        products.setName("续费产品测试1");
        products.setDescription("续费产品测试");
        products.setCategory("SOFTWARE");
        products.setType("SERVICE");
        products.setImage_url("https://member.quicktvod.com/static/img/Mexico.jpg");
        products.setHome_url("https://member.quicktvod.com/static/img/Mexico.jpg");
        String id = CreateProductsImpl.CreateProducts(products);
        System.out.println(id);
    }


}

