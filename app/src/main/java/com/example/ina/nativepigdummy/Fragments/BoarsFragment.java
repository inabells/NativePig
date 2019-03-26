package com.example.ina.nativepigdummy.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.ViewBreederActivity;
import com.example.ina.nativepigdummy.Adapters.BoarDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BoarsFragment extends Fragment {

    private DatabaseHelper dbHelper;
    ArrayList<BoarData> boarList;
    BoarData boarData;
    ListView nListView;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_boars, container, false);
        nListView = view.findViewById(R.id.listview_boar);
        dbHelper = new DatabaseHelper(getActivity());
        boarList = new ArrayList<>();

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getBoars();
        } else{
            local_getBoars();
        }

        nListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listview = boarList.get(i).getBoar_reg_id();
                Intent intent = new Intent(getActivity(), ViewBreederActivity.class);
                intent.putExtra("ListClickValue", listview);
                startActivity(intent);
            }
        });

        return view;
    }

    private void local_getBoars() {
        Cursor data = dbHelper.getBoarContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                boarData = new BoarData(data.getString(2));
                boarList.add(boarData);
            }
            BoarDataAdapter adapter = new BoarDataAdapter(getActivity(), R.layout.listview_breeder_grower, boarList);
            nListView.setAdapter(adapter);
        }
    }

    private void api_getBoars() {
        ApiHelper.getBoars("getAllBoars", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                BoarDataAdapter adapter = new BoarDataAdapter(getActivity(), R.layout.listview_breeder_grower, boarList);
                nListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
                Log.d("API HANDLER FAIL", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    boarList.add(new BoarData(jsonObject.getString("pig_registration_id")));
                }
                return null;
            }
        });
    }

//    private void addRegId(){
//        RequestParams requestParams = new RequestParams();
//        requestParams.add("registration_id", generateRegistrationId());
//
//        ApiHelper.addRegId("addRegId", requestParams, new BaseJsonHttpResponseHandler<Object>() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
//                Log.d("Reg ID", "Added reg ID to database");
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
//                Log.d("Reg ID", "Error occurred");
//            }
//
//            @Override
//            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return null;
//            }
//        });
//    }
//
//    private void addRegIdWeightRecords(){
//        RequestParams requestParams = new RequestParams();
//        requestParams.add("registration_id", generateRegistrationId());
//
//        ApiHelper.addRegIdWeightRecords("addRegIdWeightRecords", requestParams, new BaseJsonHttpResponseHandler<Object>() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
//                Log.d("Reg ID", "Added reg ID to database");
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
//                Log.d("Reg ID", "Error occurred");
//            }
//
//            @Override
//            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return null;
//            }
//        });
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
