package com.example.paypalspringbootdemo.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


/**
 * 将Request转换成Map
 * @author hsl
 *
 */
public class RequestToMapUtil {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    // Simple helper method to help you extract the headers from HttpServletRequest object.
    private static Map < String, String > getHeadersInfo(HttpServletRequest request) {
        Map < String, String > map = new HashMap < String, String > ();
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


    public static Map<String, Object> getPrepayMapInfo(String Str) {
        String notityXml = Str.replaceAll("</?xml>", "");
        Pattern pattern = Pattern.compile("<.*?/.*?>");
        Matcher matcher = pattern.matcher(notityXml);
        Pattern pattern2 = Pattern.compile("!.*]");
        Map<String, Object> mapInfo = new HashMap<>();
        while (matcher.find()) {
            String key = matcher.group().replaceAll(".*/", "");
            key = key.substring(0, key.length() - 1);
            Matcher matcher2 = pattern2.matcher(matcher.group());
            String value = matcher.group().replaceAll("</?.*?>", "");
            if (matcher2.find() && !value.equals("DATA")) {
                value = matcher2.group().replaceAll("!.*\\[", "");
                value = value.substring(0, value.length() - 2);
            }
            mapInfo.put(key, value);
        }
        return mapInfo;
    }
}
