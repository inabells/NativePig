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
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Data.SalesData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.Dialog.OthersDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class OthersFragment extends Fragment {

    ListView listView;
    private FloatingActionButton others;
    DatabaseHelper dbHelper;
    ArrayList<OthersData> otherList;
    OthersData othersData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_others, container, false);
        listView = view.findViewById(R.id.listview_others);
        others = view.findViewById(R.id.floating_action_others);
        dbHelper = new DatabaseHelper(getActivity());
        otherList = new ArrayList<>();

        RequestParams requestParams = new RequestParams();
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getOthersData(requestParams);
        } else{
            local_getOthersData();
        }

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OthersDialog dialog = new OthersDialog();
                dialog.show(getFragmentManager(), "OthersDialog");
            }
        });

        return view;
    }

    private void local_getOthersData(){
        Cursor data = dbHelper.getOthersContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                othersData = new OthersData(data.getString(0), data.getString(5), data.getString(6), data.getString(7));
                otherList.add(othersData);
            }
            OthersDataAdapter adapter = new OthersDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, otherList);
            listView.setAdapter(adapter);
        }
    }

    private void api_getOthersData(RequestParams requestParams){
        ApiHelper.getOthersPage("getOthersPage", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                OthersDataAdapter adapter = new OthersDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, otherList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
//                Log.d("API HANDLER FAIL", errorResponse.toString());
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                OthersData mData;
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    mData = new OthersData();
                    mData.setOthers_reg_id(jsonObject.getString("registry_id"));
                    mData.setDate_removed(jsonObject.getString("dateremoved"));
                    mData.setReason(jsonObject.getString("reason"));
                    mData.setAge(jsonObject.getString("age"));
                    otherList.add(mData);
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
