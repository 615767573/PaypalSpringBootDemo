//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paypalspringbootdemo.utils;

import com.alibaba.cloudapi.sdk.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.client.ApacheHttpClient;
import com.alibaba.cloudapi.sdk.enums.HttpConnectionModel;
import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.alibaba.fastjson.JSONObject;
import com.example.paypalspringbootdemo.domain.IoTApiRequest;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@ThreadSafe
public final class SyncApiClient extends ApacheHttpClient {
    public SyncApiClient(HttpClientBuilderParams builderParams) {
        super.init(builderParams);
    }

    public ApiResponse postBody(String host, String path, IoTApiRequest request, boolean isHttps, Map<String, String> headers) throws UnsupportedEncodingException {
        byte[] body = JSONObject.toJSONString(request).getBytes("UTF-8");
        ApiRequest apiRequest = new ApiRequest(HttpMethod.POST_BODY, path, body);
        apiRequest.setHttpConnectionMode(HttpConnectionModel.MULTIPLE_CONNECTION);
        apiRequest.setScheme(isHttps ? Scheme.HTTPS : Scheme.HTTP);
        apiRequest.setHost(host);
        if (null != headers && headers.size() > 0) {
            Iterator var8 = headers.entrySet().iterator();

            while(var8.hasNext()) {
                Entry<String, String> header = (Entry)var8.next();
                apiRequest.getHeaders().put(header.getKey(), Arrays.asList((String)header.getValue()));
            }
        }

        return this.sendSyncRequest(apiRequest);
    }

    public ApiResponse postBody(String host, String path, IoTApiRequest request, boolean isHttps) throws UnsupportedEncodingException {
        return this.postBody(host, path, request, isHttps, (Map)null);
    }

    public ApiResponse postBody(String host, String path, IoTApiRequest request) throws UnsupportedEncodingException {
        return this.postBody(host, path, request, false, (Map)null);
    }
}
