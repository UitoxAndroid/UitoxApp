package com.uitox.http;

/**
 * Created by babyandy on 2015/8/4.
 */
public class NetParams {
    public static final String APP_BASE_URL = "http://www.weather.com.cn";
    public static final String SHIHJIE_BASE_URL = "http://www.shihjie.com";
    public static String getAPPUrl(String action) {
        return APP_BASE_URL + "/" + action;
    }

    public static String getSHIHJIEUrl(String action) {
        return SHIHJIE_BASE_URL + "/" + action;
    }
}
