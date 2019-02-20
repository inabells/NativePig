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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Adapters.SowDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.SowData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.DateDialog;
import com.example.ina.nativepigdummy.Dialog.GroupWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MortalityFragment extends Fragment {

    DatabaseHelper myDB;
    ArrayList<MortalityData> mortalityList;
    MortalityData mortalityData;
    ListView listView;
    private FloatingActionButton mortality;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_mortality, container, false);
        listView = view.findViewById(R.id.listview_mortality);
        mortality = view.findViewById(R.id.floating_action_mortality);
        myDB = new DatabaseHelper(getActivity());
        mortalityList = new ArrayList<>();

        if(ApiHelper.isInternetAvailable(getContext())) {
            ApiHelper.getMortality("getMortality", null, new BaseJsonHttpResponseHandler<Object>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    Log.d("API HANDLER Success", rawJsonResponse);
                    MortalityDataAdapter adapter = new MortalityDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, mortalityList);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                    Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
                    Log.d("API HANDLER FAIL", errorResponse.toString());
                }

                @Override
                protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    JSONArray jsonArray = new JSONArray(rawJsonData);
                    JSONObject jsonObject;
                    MortalityData mData;
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        mData = new MortalityData();
                        mData.setMortality_reg_id(jsonObject.getString("pig_registration_id"));
                        mData.setDate_of_death(jsonObject.getString("date_removed_died"));
                        mData.setCause_of_death(jsonObject.getString("cause_of_death"));
                        mData.setAge(jsonObject.getString("age"));
                        mortalityList.add(mData);
                    }
                    return null;
                }
            });

        } else{
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }

        mortality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MortalityDialog dialog = new MortalityDialog();
                dialog.show(getFragmentManager(), "MortalityDialog");
            }
        });

        return view;
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
