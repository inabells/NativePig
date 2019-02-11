package com.example.ina.nativepigdummy.API;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.entity.mime.Header;

public class ApiHelper {

    private final static String BASE_URL = "http://192.168.1.10:8000/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public ApiHelper(){

    }

    public static void getSows(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    private static String getAbsoluteUrl(String url) {
        return BASE_URL + url;
    }
}
