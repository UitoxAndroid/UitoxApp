package com.uitox.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private Map<String,String> headers;
    private HashMap<String, String> params;
    private final Response.Listener<T> listener;
    private final OnListResponseListener<T> listListener;
    private final boolean isArray;
    private final Context context;
    private String url;
    public interface OnListResponseListener<T>{
        void onResponse(List<T> response);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String,String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener,HashMap<String, String> params) {
        super(method, url, errorListener);
        isArray = false;
        this.context = null;
        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
        this.listener = listener;
        this.listListener = null;
        this.url = url;
    }

    public GsonRequest(Context context, int method, String url, Class<T> clazz, Map<String,String> headers,
                       OnListResponseListener<T> listener, Response.ErrorListener errorListener,HashMap<String, String> params){
        super(method,url,errorListener);
        isArray = true;
        this.context = context;
        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
        this.listListener = listener;
        this.listener = null;
        this.url = url;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json;
            if("gzip".equals(networkResponse.headers.get("Content-Encoding"))) {
                json = NetWorkTool.GZipDecoderToString(networkResponse.data);
            }else {
                json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            }
            Log.i("GsonRequest",url);
            Log.i("GsonRequest",json);
            if(isArray){
                Log.i("GsonRequest", "array");
                final List<T> list = new ArrayList<T>();
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(json).getAsJsonArray();
                for(JsonElement obj : jsonArray){
                    T t = gson.fromJson(obj, clazz);
                    list.add(t);
                }
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (listListener != null) {
                            listListener.onResponse(list);
                        }
                    }
                });

                return Response.success(list.get(0), HttpHeaderParser.parseCacheHeaders(networkResponse));
            }else {
                Log.i("GsonRequest","not array");
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(networkResponse));
            }
        } catch (UnsupportedEncodingException e) {
            Log.i("GsonRequest","UnsupportedEncodingException");
            Log.i("GsonRequest",e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.i("GsonRequest","JsonSyntaxException");
            Log.i("GsonRequest",e.getMessage());
            return Response.error(new ParseError(e));
        }
    }

    //帶參數
    @Override
    protected HashMap<String, String> getParams() throws AuthFailureError {
        return this.params;
    }

    @Override
    protected void deliverResponse(T t) {
        if(listener != null) {
            listener.onResponse(t);
        }
    }
}