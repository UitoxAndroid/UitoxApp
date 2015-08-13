package com.uitox.http;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

/**
 * Created by babyandy on 2015/8/13.
 */
public class ToJsonString {
    private static Gson gson =  new Gson();

    public String toJson(HashMap hashMap)
    {
        return gson.toJson(hashMap);
    }
    public String toJson(List list)
    {
        return gson.toJson(list);
    }
}
