package com.example.ina.nativepigdummy.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.BreederWeightRecordsDialog;
import com.example.ina.nativepigdummy.Dialog.GrossMorphologyDialog;
import com.example.ina.nativepigdummy.Dialog.MorphCharDialog;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class BreederWeightRecordsFragment extends Fragment implements BreederWeightRecordsDialog.ViewWeightRecordListener{

    public BreederWeightRecordsFragment() {
        // Required empty public constructor
    }

    private DatabaseHelper dbHelper;
    private String pigRegIdHolder;
    private String editWeight45, editWeight60, editWeight90, editWeight150, editWeight180;
    private String editDate45, editDate60, editDate90, editDate150, editDate180;
    private TextView TextViewWeight45, TextViewWeight60, TextViewWeight90, TextViewWeight150, TextViewWeight180;
    private TextView TextViewDate45, TextViewDate60, TextViewDate90, TextViewDate150, TextViewDate180;
    private TextView registration_id;
    private ImageView edit_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breeder_weight_records, container, false);
        edit_profile = view.findViewById(R.id.edit_breeder_weight_records);

        dbHelper = new DatabaseHelper(getContext());
        TextViewWeight45 = (TextView) view.findViewById(R.id.textView_45days);
        TextViewWeight60 = (TextView) view.findViewById(R.id.textView_60days);
        TextViewWeight90 = (TextView) view.findViewById(R.id.textView_90days);
        TextViewWeight150 = (TextView) view.findViewById(R.id.textView_150days);
        TextViewWeight180 = (TextView) view.findViewById(R.id.textView_180days);
        TextViewDate45 = (TextView) view.findViewById(R.id.textView_45date);
        TextViewDate60 = (TextView) view.findViewById(R.id.textView_60date);
        TextViewDate90 = (TextView) view.findViewById(R.id.textView_90date);
        TextViewDate150 = (TextView) view.findViewById(R.id.textView_150date);
        TextViewDate180 = (TextView) view.findViewById(R.id.textView_180date);

        registration_id = view.findViewById(R.id.registration_id);

        pigRegIdHolder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(pigRegIdHolder);
        RequestParams params = buildParams();

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getWeightProfile(params);
        } else{
            local_getWeightProfile(pigRegIdHolder);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBreederWeightRecordsDialog(pigRegIdHolder);
            }
        });

        return view;
    }

    private RequestParams buildParams() {
        RequestParams params = new RequestParams();
        params.add("registration_id", pigRegIdHolder);
        return params;
    }

    private void local_getWeightProfile(String reg_id) {
        Cursor data = dbHelper.getSinglePig(reg_id);
        while(data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "32": TextViewWeight45.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "33": TextViewWeight60.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "34": TextViewWeight90.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "35": TextViewWeight150.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "36": TextViewWeight180.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "37": TextViewDate45.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "38": TextViewDate60.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "39": TextViewDate90.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "40": TextViewDate150.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "41": TextViewDate180.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }

    private void api_getWeightProfile(RequestParams params) {
        ApiHelper.getWeightProfile("getWeightProfile", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                TextViewWeight45.setText(setDefaultTextIfNull(editWeight45));
                TextViewWeight60.setText(setDefaultTextIfNull(editWeight60));
                TextViewWeight90.setText(setDefaultTextIfNull(editWeight90));
                TextViewWeight150.setText(setDefaultTextIfNull(editWeight150));
                TextViewWeight180.setText(setDefaultTextIfNull(editWeight180));
                TextViewDate45.setText(setDefaultTextIfNull(editDate45));
                TextViewDate60.setText(setDefaultTextIfNull(editDate60));
                TextViewDate90.setText(setDefaultTextIfNull(editDate90));
                TextViewDate150.setText(setDefaultTextIfNull(editDate150));
                TextViewDate180.setText(setDefaultTextIfNull(editDate180));
                Log.d("BreederWeightRecords", "Succesfully fetched");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("BreederWeightRecords", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                editWeight45 = jsonObject.get("weight_at_45").toString();
                editWeight60 = jsonObject.get("weight_at_60").toString();
                editWeight90 = jsonObject.get("weight_at_90").toString();
                editWeight150 = jsonObject.get("weight_at_150").toString();
                editWeight180 = jsonObject.get("weight_at_180").toString();
                editDate45 = jsonObject.get("date_collected_at_45").toString();
                editDate60 = jsonObject.get("date_collected_at_60").toString();
                editDate90 = jsonObject.get("date_collected_at_90").toString();
                editDate150 = jsonObject.get("date_collected_at_150").toString();
                editDate180 = jsonObject.get("date_collected_at_180").toString();
                return null;
            }
        });
    }

    private String setDefaultTextIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "Not specified" : text);
    }

    public void openBreederWeightRecordsDialog(String regId){
        BreederWeightRecordsDialog dialog = new BreederWeightRecordsDialog(regId);
        dialog.setTargetFragment(BreederWeightRecordsFragment.this, 1);
        dialog.show(getFragmentManager(),"BreederWeightRecordsDialog");
    }

    @Override public void applyWeight45(String weightat45){ TextViewWeight45.setText(weightat45); }

    @Override public void applyWeight60(String weightat60){ TextViewWeight60.setText(weightat60); }

    @Override public void applyWeight90(String weightat90){ TextViewWeight90.setText(weightat90); }

    @Override public void applyWeight150(String weightat150){ TextViewWeight150.setText(weightat150); }

    @Override public void applyWeight180(String weightat180){ TextViewWeight180.setText(weightat180); }

    @Override public void applyDate45(String datecollected45){ TextViewDate45.setText(datecollected45); }

    @Override public void applyDate60(String datecollected60){ TextViewDate60.setText(datecollected60); }

    @Override public void applyDate90(String datecollected90){ TextViewDate90.setText(datecollected90); }

    @Override public void applyDate150(String datecollected150){ TextViewDate150.setText(datecollected150); }

    @Override public void applyDate180(String datecollected180){ TextViewDate180.setText(datecollected180); }



}
