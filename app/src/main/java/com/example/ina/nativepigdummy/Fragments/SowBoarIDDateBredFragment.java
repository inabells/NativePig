package com.example.ina.nativepigdummy.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.ViewBreederActivity;
import com.example.ina.nativepigdummy.Activities.ViewBreedingActivity;
import com.example.ina.nativepigdummy.Adapters.BreedingRecordDataAdapter;
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Data.BreedingRecordData;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.BreedingRecordsDialog;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class SowBoarIDDateBredFragment extends Fragment {

    ListView listView;
    private FloatingActionButton breedingrecord;
    DatabaseHelper myDB;
    ArrayList<BreedingRecordData> breedingRecordList;
    BreedingRecordData breedingRecordData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_sow_boar_iddate_bred, container, false);
        listView = view.findViewById(R.id.listview_breeding_record);
        breedingrecord = view.findViewById(R.id.floating_action_breeding_record);

        myDB = new DatabaseHelper(getActivity());

        breedingRecordList = new ArrayList<>();

        if(ApiHelper.isInternetAvailable(getContext())) {
            ApiHelper.getBreedingRecord("getBreedingRecord", null, new BaseJsonHttpResponseHandler<Object>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    Log.d("API HANDLER Success", rawJsonResponse);
                    BreedingRecordDataAdapter adapter = new BreedingRecordDataAdapter(getActivity(), R.layout.listview_breeding_record, breedingRecordList);
                    listView.setAdapter(adapter);
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
                    BreedingRecordData mData;
                    for (int i = jsonArray.length() - 1; i >= 0; i--) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        mData = new BreedingRecordData();
                        mData.setSow_id(jsonObject.getString("sow_registration_id"));
                        mData.setBoar_id(jsonObject.getString("boar_registration_id"));
                        mData.setDate_bred(jsonObject.getString("date_bred"));
                        breedingRecordList.add(mData);
                    }
                    return null;
                }
            });

        } else{
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
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
                Intent intent_view_breeding = new Intent(getActivity(), ViewBreedingActivity.class);
                intent_view_breeding.putExtra("sow_id", breedingRecordList.get(i).getSow_id());
                intent_view_breeding.putExtra("boar_id", breedingRecordList.get(i).getBoar_id());
                startActivity(intent_view_breeding);
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
        if(item.getItemId() == R.id.action_search) {

        }
        return true;
    }
}
