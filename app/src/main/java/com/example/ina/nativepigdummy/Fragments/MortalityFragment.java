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
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MortalityFragment extends Fragment {

    DatabaseHelper dbHelper;
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
        dbHelper = new DatabaseHelper(getContext());
        mortalityList = new ArrayList<>();

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getMortalityData();
        }else{
            local_getMortalityData();
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

    private void local_getMortalityData(){
        Cursor data = dbHelper.getMortalityContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                mortalityData = new MortalityData(data.getString(1), data.getString(2), data.getString(3), data.getString(6));
                mortalityList.add(mortalityData);
            }
            MortalityDataAdapter adapter = new MortalityDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, mortalityList);
            listView.setAdapter(adapter);
        }
    }

    private void api_getMortalityData(){
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
                Log.d("API HANDLER FAIL", "Error occurred");
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
