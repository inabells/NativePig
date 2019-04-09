package com.example.ina.nativepigdummy.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Adapters.OthersDataAdapter;
import com.example.ina.nativepigdummy.Adapters.SalesDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Data.SalesData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.Dialog.SalesDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SalesFragment extends Fragment {

    ListView listView;
    private FloatingActionButton sales;
    SalesData salesData;
    DatabaseHelper dbHelper;
    ArrayList<SalesData> salesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_sales, container, false);
        listView = view.findViewById(R.id.listview_sales);
        sales = view.findViewById(R.id.floating_action_sales);
        dbHelper = new DatabaseHelper(getActivity());
        salesList = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getSalesData(requestParams);
        } else{
            local_getSalesData();
        }

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalesDialog dialog = new SalesDialog();
                dialog.show(getFragmentManager(), "SalesDialog");
            }
        });

        return view;
    }

    private void local_getSalesData(){
        Cursor data = dbHelper.getSalesContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                salesData = new SalesData(data.getString(0), data.getString(5), data.getString(6), data.getString(7), data.getString(8));
                salesList.add(salesData);
            }
            SalesDataAdapter adapter = new SalesDataAdapter(getActivity(), R.layout.listview_sales, salesList);
            listView.setAdapter(adapter);
        }
    }

    private void api_getSalesData(RequestParams requestParams){
        ApiHelper.getSalesPage("getSalesPage", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                SalesDataAdapter adapter = new SalesDataAdapter(getActivity(), R.layout.listview_sales, salesList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                SalesData mData;
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    mData = new SalesData();
                    mData.setSales_reg_id(jsonObject.getString("registry_id"));
                    mData.setDate_sold(jsonObject.getString("datesold"));
                    mData.setWeight(jsonObject.getString("weight"));
                    mData.setPrice(jsonObject.getString("price"));
                    mData.setAge(jsonObject.getString("age"));
                    salesList.add(mData);
                }
                return null;
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        }
        return true;
    }


}
