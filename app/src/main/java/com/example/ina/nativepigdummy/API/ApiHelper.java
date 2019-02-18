package com.example.ina.nativepigdummy.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.*;

import java.net.InetAddress;


public class ApiHelper {

    private final static String BASE_URL = "http://192.168.1.8:80/api/"; // try 80 port, find appropriate port nasa cloud yung web app GRRRRRR
    private static AsyncHttpClient client = new AsyncHttpClient();

    public ApiHelper(){

    }

    public static void getSows(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getBoars(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllFemaleGrowers(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllMaleGrowers(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addPig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    private static String getAbsoluteUrl(String url) {
        return BASE_URL + url;
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }

        return isConnected;
    }
}
