package com.example.ina.nativepigdummy.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.*;

import java.net.InetAddress;


public class ApiHelper {

    private final static String BASE_URL = "http://192.168.1.13:80/api/"; // try 80 port, find appropriate port nasa cloud yung web app GRRRRRR
    private static AsyncHttpClient client = new AsyncHttpClient();

    public ApiHelper(){

    }

    public static void getAllPigs(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void searchPig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getMortality(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getSales(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getOthers(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getSows(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getBoars(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getBreedingRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllFemaleGrowers(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllMaleGrowers(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getSowCount(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getBoarCount(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getFemaleGrowerCount(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getMaleGrowerCount(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addPig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addBreedingRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addPigMortalitySales(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
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
