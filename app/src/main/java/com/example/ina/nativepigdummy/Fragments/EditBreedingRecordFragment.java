package com.example.ina.nativepigdummy.Fragments;

import android.app.DownloadManager;
import android.content.Intent;
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
import com.example.ina.nativepigdummy.Activities.SowLitterActivity;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.ExpectedDateofFarrowingDialog;
import com.example.ina.nativepigdummy.Dialog.StatusDialog;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class EditBreedingRecordFragment extends Fragment implements ExpectedDateofFarrowingDialog.ViewFarrowListener, StatusDialog.ViewStatusListener{

    public EditBreedingRecordFragment() {
        // Required empty public constructor
    }

    private String sowIdHolder, boarIdHolder, editDateBred, editExpectedFarrowDate, editStatus, expectedDate;
    private TextView sow_reg_id, boar_reg_id, TextViewStatus, TextViewFarrow, TextViewDate;
    private ImageView edit_breeding_record;
    private ImageView view_sow_litter_record;
    private ImageView status;
    private TextView farrowing;
    DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_breeding_record, container, false);

        TextViewFarrow = view.findViewById(R.id.textViewFarrowing);
        TextViewStatus = view.findViewById(R.id.textViewStatus);
        TextViewDate = view.findViewById(R.id.textViewDateBred);
//        farrowing = view.findViewById(R.id.textViewFarrowing);
        dbHelper = new DatabaseHelper(getActivity());
        status = view.findViewById(R.id.edit_status);
        view_sow_litter_record = view.findViewById(R.id.view_sow_litter_record);
        sow_reg_id = view.findViewById(R.id.textViewSowId);
        boar_reg_id = view.findViewById(R.id.textViewBoarId);

        sowIdHolder = getActivity().getIntent().getStringExtra("sow_id");
        sow_reg_id.setText(sowIdHolder);
        boarIdHolder = getActivity().getIntent().getStringExtra("boar_id");
        boar_reg_id.setText(boarIdHolder);

        RequestParams params = buildParams();
        if(ApiHelper.isInternetAvailable(getContext())){
            api_getBreedingProfile(params);
        } else{
            local_getBreedingProfile(sowIdHolder,boarIdHolder);
        }


//        farrowing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ExpectedDateofFarrowingDialog dialog = new ExpectedDateofFarrowingDialog(sowIdHolder, boarIdHolder, editExpectedFarrowDate);
//                dialog.setTargetFragment(EditBreedingRecordFragment.this, 1);
//                dialog.show(getFragmentManager(), "ExpectedDateofFarrowingDialog");
//            }
//        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusDialog dialog = new StatusDialog(sowIdHolder, boarIdHolder, editStatus);
                dialog.setTargetFragment(EditBreedingRecordFragment.this, 1);
                dialog.show(getFragmentManager(), "StatusDialog");
            }
        });

        view_sow_litter_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sow_litter = new Intent(getActivity(), SowLitterActivity.class);
                startActivity(intent_sow_litter);
            }
        });

        return view;
    }

    private RequestParams buildParams() {
        RequestParams params = new RequestParams();
        params.add("sow_registration_id", sowIdHolder);
        params.add("boar_registration_id", boarIdHolder);
        return params;
    }

    private void local_getBreedingProfile(String sow_id, String boar_id) {
        Cursor data = dbHelper.getBreedingProfile(sow_id, boar_id);

        if (data.moveToFirst()) {
            sow_reg_id.setText(data.getString(data.getColumnIndex("sow_registration_id")));
            boar_reg_id.setText(data.getString(data.getColumnIndex("boar_registration_id")));
            TextViewDate.setText(data.getString(data.getColumnIndex("date_bred")));
            TextViewFarrow.setText(data.getString(data.getColumnIndex("expected_date_farrow")));
        }
    }

    private void api_getBreedingProfile(RequestParams params) {
        ApiHelper.getBreedingProfile("getBreedingProfile", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                TextViewDate.setText(editDateBred);
                TextViewFarrow.setText(editExpectedFarrowDate);
                TextViewStatus.setText(editStatus);
                Log.d("BreedingRecord", "Successfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("BreedingRecord", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                editDateBred = jsonObject.get("date_bred").toString();
                editExpectedFarrowDate = jsonObject.get("expected_date_farrow").toString();
                editStatus = jsonObject.get("sow_status").toString();
                return null;
            }
        });
    }



    @Override public void applyText(String dateoffarrowing){
        TextViewFarrow.setText(dateoffarrowing);
    }

    @Override
    public void applyStatus(String status) {
        TextViewStatus.setText(status);
    }

}
