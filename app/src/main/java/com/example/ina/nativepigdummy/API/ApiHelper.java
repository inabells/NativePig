package com.example.ina.nativepigdummy.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.*;


public class ApiHelper {

    private final static String BASE_URL = "http://192.168.1.99:80/api/"; // try 80 port, find appropriate port nasa cloud yung web app GRRRRRR
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

    public static void getSinglePig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getGrossMorphProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getMorphCharProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllGrossMorphProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllMorphCharProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getWeightProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getBreedingProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllCount(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    //POST/ADD FUNCTIONS------------------------------------------------------------------
    public static void addPig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addBreedingRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addPigMortalitySales(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addRegId(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addRegIdWeightRecords(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addAsBreeder(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    //DELETE FUNCTION--------------------------------------------------------------------
    public static void deletePig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.delete(getAbsoluteUrl(url), request, responseHandler);
    }

    //UPDATE FUNCTION====================================================================
    public static void updateBreederPigProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateGrossMorphology(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateMorphChar(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateWeightRecords(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateExpectedDateFarrow(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateSowStatus(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    //URL================================================================================
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
