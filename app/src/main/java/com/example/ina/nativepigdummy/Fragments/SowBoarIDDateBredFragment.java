package com.example.ina.nativepigdummy.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Adapters.BreedingRecordDataAdapter;
import com.example.ina.nativepigdummy.Data.BreedingRecordData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.BreedingRecordsDialog;
import com.example.ina.nativepigdummy.Dialog.ViewBreedingRecordDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class SowBoarIDDateBredFragment extends Fragment {

    ListView listView;
    private FloatingActionButton breedingrecord;
    DatabaseHelper dbHelper;
    ArrayList<BreedingRecordData> breedingRecordList;
    BreedingRecordData breedingRecordData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_sow_boar_iddate_bred, container, false);
        listView = view.findViewById(R.id.listview_breeding_record);
        breedingrecord = view.findViewById(R.id.floating_action_breeding_record);

        dbHelper = new DatabaseHelper(getActivity());

        breedingRecordList = new ArrayList<>();

        RequestParams requestParams = new RequestParams();
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getBreedingRecord(requestParams);
        } else{
            local_getBreedingRecord();
        }

        breedingrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreedingRecordsDialog dialog = new BreedingRecordsDialog();
                dialog.show(getFragmentManager(), "BreedingRecordsDialog");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sow_id = breedingRecordList.get(i).getSow_id();
                String boar_id = breedingRecordList.get(i).getBoar_id();
                String date_bred = breedingRecordList.get(i).getDate_bred();
                String status = breedingRecordList.get(i).getStatus();

                ViewBreedingRecordDialog dialog = new ViewBreedingRecordDialog();
                Bundle data = new Bundle();
                data.putString("sow_id", sow_id);
                data.putString("boar_id", boar_id);
                data.putString("date_bred", date_bred);
                data.putString("status", status);
                dialog.setArguments(data);
                dialog.show(getFragmentManager(),"ViewBreedingRecordDialog");
            }
        });

        return view;
    }

    private void local_getBreedingRecord(){
        Cursor data = dbHelper.getBreedingContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                breedingRecordData = new BreedingRecordData(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4));
                breedingRecordList.add(breedingRecordData);
            }
            BreedingRecordDataAdapter adapter = new BreedingRecordDataAdapter(getActivity(), R.layout.listview_breeding_record, breedingRecordList);
            listView.setAdapter(adapter);
        }
    }

    private void api_getBreedingRecord(RequestParams requestParams){
        ApiHelper.getBreedingRecord("getBreedingRecord", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                BreedingRecordDataAdapter adapter = new BreedingRecordDataAdapter(getActivity(), R.layout.listview_breeding_record, breedingRecordList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                //Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
                Log.d("API HANDLER FAIL", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                BreedingRecordData mData;
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    mData = new BreedingRecordData();
                    mData.setSow_id(jsonObject.getString("sow_registryid"));
                    mData.setBoar_id(jsonObject.getString("boar_registryid"));
                    mData.setDate_bred(jsonObject.getString("dateBred"));
                    mData.setEdf(jsonObject.getString("edf"));
                    mData.setStatus(jsonObject.getString("status"));
                    breedingRecordList.add(mData);
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
        if(item.getItemId() == R.id.action_search) {

        }
        return true;
    }
}
