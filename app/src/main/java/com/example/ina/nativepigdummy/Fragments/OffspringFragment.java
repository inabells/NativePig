package com.example.ina.nativepigdummy.Fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Adapters.OffspringDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OffspringData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.EditOffspringDialog;
import com.example.ina.nativepigdummy.Dialog.GroupWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.IndividualWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.SowAndLitterDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class OffspringFragment extends Fragment {

    ListView listView;
    DatabaseHelper dbHelper;
    ArrayList<OffspringData> offspringList;
    OffspringData offspringData;
    String sowId, sowRegId, boarRegId, boarId, groupingId;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton group, individual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_offspring, container, false);
        listView = view.findViewById(R.id.listview_offspring);
        floatingActionMenu = view.findViewById(R.id.floating_action_menu);
        group = view.findViewById(R.id.group_weighing);
        individual = view.findViewById(R.id.individual_weighing);
        dbHelper = new DatabaseHelper(getActivity());
        offspringList = new ArrayList<>();

        sowRegId = getActivity().getIntent().getStringExtra("sow_idSLR");
        boarRegId = getActivity().getIntent().getStringExtra("boar_idSLR");

        sowId = dbHelper.getAnimalId(sowRegId);
        boarId = dbHelper.getAnimalId(boarRegId);
        groupingId = dbHelper.getGroupingId(sowId, boarId);

        RequestParams requestParams = new RequestParams();
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));
        requestParams.add("sow_id", sowRegId);
        requestParams.add("boar_id", boarRegId);

        if(ApiHelper.isInternetAvailable(getActivity()))
            api_getOffSprings(requestParams);
        else local_getOffsprings();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String offspring_id = offspringList.get(i).getOffspring_id();
                EditOffspringDialog dialog = new EditOffspringDialog();
                Bundle data = new Bundle();
                data.putString("OffspringRegId", offspring_id);
                dialog.setArguments(data);
                //dialog.setTargetFragment(OffspringFragment.this, 1);
                dialog.show(getFragmentManager(),"EditOffspringDialog");
            }
        });


        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupWeighingDialog dialog = new GroupWeighingDialog();
                //dialog.show(getActivity().getFragmentManager(), "GroupWeighingDialog");
                dialog.show(getActivity().getSupportFragmentManager(), "IndividualWeighingDialog");
            }
        });

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndividualWeighingDialog dialog = new IndividualWeighingDialog();
//                dialog.show(getActivity().getFragmentManager(), "IndividualWeighingDialog");
                dialog.show(getActivity().getSupportFragmentManager(), "IndividualWeighingDialog");
            }
        });

        return view;
    }

    private void local_getOffsprings() {
        String regId = "";
        String sex = "";
        String birthWeight = "";
        String weaningWeight = "";
        Cursor data = dbHelper.getOffspringContents(groupingId);
        int counter = 0;
        while(data.moveToNext()){
            switch(data.getString(data.getColumnIndex("property_id"))){
                case "4": regId = data.getString(data.getColumnIndex("value"));
                        counter++;
                        break;
                case "2": sex = data.getString(data.getColumnIndex("value"));
                        counter++;
                        break;
                case "5": birthWeight = data.getString(data.getColumnIndex("value"));
                        counter++;
                        break;
                case "7": weaningWeight = data.getString(data.getColumnIndex("value"));
                        counter++;
                        break;
            }

            if(counter==4) {
                offspringData = new OffspringData(regId, sex, birthWeight, weaningWeight);
                offspringList.add(offspringData);
                counter=0;
            }
        }

        OffspringDataAdapter adapter = new OffspringDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, offspringList);
        listView.setAdapter(adapter);
    }

    private void api_getOffSprings(RequestParams requestParams) {
        ApiHelper.viewOffsprings("viewOffsprings", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                OffspringDataAdapter adapter = new OffspringDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, offspringList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("SowLitter", "Error: " + String.valueOf(statusCode));
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                OffspringData offspringData;
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    offspringData = new OffspringData();
                    JSONArray innerJsonArray = new JSONArray(jsonArray.get(i).toString());
                    for(int j=0; j<innerJsonArray.length(); j++){
                        jsonObject = (JSONObject) innerJsonArray.get(j);
                        switch(jsonObject.getString("property_id")){
                            case "4": offspringData.setOffspring_id(jsonObject.getString("value")); break;
                            case "2": offspringData.setSex(jsonObject.getString("value")); break;
                            case "5": offspringData.setBirthweight(jsonObject.getString("value")); break;
                            case "7": offspringData.setWeaningweight(setBlankIfNull(jsonObject.getString("value"))); break;
                        }
                    }
                    offspringList.add(offspringData);
                }
                return null;
            }
        });
    }

    private String setBlankIfNull(String text) {
        if(text == null) return "Weaning weight unavailable";
        return ((text=="null" || text.isEmpty()) ? "No data available" : text);
    }
}
