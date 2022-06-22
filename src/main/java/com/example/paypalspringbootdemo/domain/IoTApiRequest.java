//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paypalspringbootdemo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IoTApiRequest {
    private String id = UUID.randomUUID().toString();
    private String version = "1.0";
    private Map<String, Object> request = new HashMap();
    private Map<String, Object> params = new HashMap();

    public IoTApiRequest() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getRequest() {
        return this.request;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setApiVer(String apiVer) {
        this.request.put("apiVer", apiVer);
    }

    public void setIotToken(String iotToken) {
        this.request.put("iotToken", iotToken);
    }

    public void setCloudToken(String cloudToken) {
        this.request.put("cloudToken", cloudToken);
    }

    public void putParam(String key, Object value) {
        this.params.put(key, value);
    }
}
