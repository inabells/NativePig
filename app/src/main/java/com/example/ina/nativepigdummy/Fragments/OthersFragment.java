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
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Adapters.OthersDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.Dialog.OthersDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class OthersFragment extends Fragment {

    ListView listView;
    private FloatingActionButton others;
    DatabaseHelper myDB;
    ArrayList<OthersData> otherList;
    OthersData othersData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_others, container, false);
        listView = view.findViewById(R.id.listview_others);
        others = view.findViewById(R.id.floating_action_others);

        myDB = new DatabaseHelper(getActivity());

        otherList = new ArrayList<>();
        /*Cursor data  = myDB.getOthersContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                othersData = new OthersData(data.getString(1), data.getString(2),data.getString(3), data.getString(4));
                otherList.add(i, othersData);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4));
                System.out.println(otherList.get(i).getOthers_reg_id());
                i++;
            }
            OthersDataAdapter adapter = new OthersDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, otherList);
            listView.setAdapter(adapter);
        }
*/
        if(ApiHelper.isInternetAvailable(getContext())) {
            ApiHelper.getOthers("getOthers", null, new BaseJsonHttpResponseHandler<Object>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    Log.d("API HANDLER Success", rawJsonResponse);
                    OthersDataAdapter adapter = new OthersDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, otherList);
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
                    OthersData mData;
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        mData = new OthersData();
                        mData.setOthers_reg_id(jsonObject.getString("pig_registration_id"));
                        mData.setDate_removed(jsonObject.getString("date_removed_died"));
                        mData.setReason(jsonObject.getString("reason_removed"));
                        mData.setAge(jsonObject.getString("age"));
                        otherList.add(mData);
                    }
                    return null;
                }
            });

        } else{
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
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
