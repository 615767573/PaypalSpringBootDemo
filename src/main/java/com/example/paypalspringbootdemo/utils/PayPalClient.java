package com.example.paypalspringbootdemo.utils;

import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * PayPalClient（请求PayPal api的工具类）
 * @author hsl
 */
@Slf4j
@Component
public class PayPalClient {

    @Value("${paypal.clientId}")
    private String clientId ;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    public PayPalHttpClient client() {
        log.info("mode={}, clientId={}, clientSecret={}", mode, clientId, clientSecret);
        PayPalEnvironment environment = mode.equals("live") ? new PayPalEnvironment.Live(this.clientId, this.clientSecret) : new PayPalEnvironment.Sandbox(clientId, clientSecret);
        return new PayPalHttpClient(environment);
    }

    /**
     * @param jo
     * @param pre
     * @return
     */
    public String prettyPrint(JSONObject jo, String pre) {
        Iterator<?> keys = jo.keys();
        StringBuilder pretty = new StringBuilder();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            pretty.append(String.format("%s%s: ", pre, StringUtils.capitalize(key)));
            if (jo.get(key) instanceof JSONObject) {
                pretty.append(prettyPrint(jo.getJSONObject(key), pre + "\t"));
            } else if (jo.get(key) instanceof JSONArray) {
                int sno = 1;
                for (Object jsonObject : jo.getJSONArray(key)) {
                    pretty.append(String.format("\n%s\t%d:\n", pre, sno++));
                    pretty.append(prettyPrint((JSONObject) jsonObject, pre + "\t\t"));
                }
            } else {
                pretty.append(String.format("%s\n", jo.getString(key)));
            }
        }
        return pretty.toString();
    }

    public String getAccessToken() throws PayPalRESTException {
        Map<String, String> configurationMap = new HashMap<String, String>();
        configurationMap.put("service.EndPoint",
                "https://api.sandbox.paypal.com");
        OAuthTokenCredential merchantTokenCredential = new OAuthTokenCredential(
                "AXHbJATli3D1GdVjpr2bLlIgLPxPAUlTIUhdna9oNVhJIsF3jH_p5bxgrMtBi3n87OSKWLIQtGYLb9zX", "EFPb6uk7fj_s6vdpNYN3-pqY5UjX7-6sdxkbUNJoVSV1ONlor89N0xLOgFqHBqswtQ6m9_i0LlFYa1H3", configurationMap);
        String accessToken = merchantTokenCredential.getAccessToken();
        return accessToken;
    }

    public HttpHeaders setHttpHeaders() throws PayPalRESTException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", getAccessToken());
        httpHeaders.set("PayPal-Request-Id", UUID.randomUUID().toString());
        return httpHeaders;
    }
}
 