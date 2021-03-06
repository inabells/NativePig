package com.example.ina.nativepigdummy.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.*;


public class ApiHelper {

    private final static String BASE_URL = "http://nativepigs.pab-is.cf/api/";
//    private final static String BASE_URL = "http://192.168.1.8:8080/api/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public ApiHelper(){

    }

    public static void getEmail(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void searchPig(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
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

    public static void getBreedingProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    //POST/ADD FUNCTIONS------------------------------------------------------------------
    public static void addBreedingRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
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

    public static void updateExpectedDateFarrow(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateSowStatus(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAllCount(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void fetchWeightRecords(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    //=======================================NEW API======================================================================

    public static void fetchNewPigRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getSows(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getViewSowPage(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAnimalProperties(String url, RequestParams request, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void fetchMorphometricCharacteristics(String url, RequestParams request, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void fetchGrossMorphology(String url, RequestParams request, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getMortalityPage(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getSalesPage(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getOthersPage(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addMortalityRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addSalesRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addRemovedAnimalRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void searchSows(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void searchBoars(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAddSowLitterRecordPage(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void editSowLitterRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateStatus(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addIndividualSowLitterRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void addGroupSowLitterRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void viewOffsprings(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void editRegistryId(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void updateOffspringRecord(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void editFarmProfile(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getFarmProfilePage(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    /*
     *function for syncing from local to server
     */
    public static void syncData(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), request, responseHandler);
    }

    /*
     *functions for getting from server to local
     */
    public static void getAnimalDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getAnimalPropertiesDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getGroupingsDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getGroupingMembersDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getGroupingPropertiesDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getMortalitiesDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getRemovedAnimalsDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }

    public static void getSalesDb(String url, RequestParams request, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), request, responseHandler);
    }
    //URL=================================================================================================================

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
