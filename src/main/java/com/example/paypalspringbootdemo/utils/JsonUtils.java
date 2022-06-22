//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paypalspringbootdemo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonUtils() {
    }

    public static String encodeToString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return mapper.writeValueAsString(obj);
        } catch (Exception var3) {
            logger.error("encode object to string failed", var3);
            return "";
        }
    }

    public static <T> T parse(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr)) {
            logger.error("json string is empty");
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();

            try {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                T request = mapper.readValue(jsonStr, clazz);
                return request;
            } catch (Exception var4) {
                logger.error("parse failed, json string:" + jsonStr, var4);
                return null;
            }
        }
    }

    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, new Class[]{beanType});

        try {
            List<T> list = (List)MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
