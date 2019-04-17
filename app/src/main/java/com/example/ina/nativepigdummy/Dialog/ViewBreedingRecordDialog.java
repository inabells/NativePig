package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.SowLitterActivity;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.EditBreedingRecordFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ViewBreedingRecordDialog extends DialogFragment {
    private static final String TAG = "EditOffspringDialog";

    public ViewBreedingRecordDialog() {
        // Required empty public constructor
    }

    private String sowIdHolder, boarIdHolder, editDateBred, editExpectedFarrowDate, editStatus, expectedDate;
    private TextView sow_reg_id, boar_reg_id, TextViewStatus, TextViewFarrow, TextViewDate;
    private ImageView edit_breeding_record;
    private ImageView view_sow_litter_record;
    private ImageView status;
    private TextView farrowing;
    DatabaseHelper dbHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_breeding_record, null);

        TextViewFarrow = view.findViewById(R.id.textViewFarrowing);
        TextViewStatus = view.findViewById(R.id.textViewStatus);
        TextViewDate = view.findViewById(R.id.textViewDateBred);
        dbHelper = new DatabaseHelper(getActivity());
        status = view.findViewById(R.id.edit_status);
        view_sow_litter_record = view.findViewById(R.id.view_sow_litter_record);
        sow_reg_id = view.findViewById(R.id.textViewSowId);
        boar_reg_id = view.findViewById(R.id.textViewBoarId);

        sowIdHolder = getArguments().getString("sow_id");
        sow_reg_id.setText(sowIdHolder);
        boarIdHolder = getArguments().getString("boar_id");
        boar_reg_id.setText(boarIdHolder);
        editDateBred = getArguments().getString("date_bred");
        TextViewDate.setText(editDateBred);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date edf = null;
        try {
            edf = sdf.parse(editDateBred);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String exDateFarrow = sdf.format(addDays(edf, 114));
        TextViewFarrow.setText(exDateFarrow);

        RequestParams params = buildParams();
        if(ApiHelper.isInternetAvailable(getContext())){
            api_getBreedingProfile(params);
        } else{
            local_getBreedingProfile(sowIdHolder,boarIdHolder);
        }

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusDialog dialog = new StatusDialog(sowIdHolder, boarIdHolder, editStatus);
                dialog.setTargetFragment(ViewBreedingRecordDialog.this, 1);
                dialog.show(getFragmentManager(), "StatusDialog");
            }
        });

        view_sow_litter_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sow_litter = new Intent(getActivity(), SowLitterActivity.class);
                intent_sow_litter.putExtra("sow_idSLR", sowIdHolder);
                intent_sow_litter.putExtra("boar_idSLR", boarIdHolder);
                intent_sow_litter.putExtra("date_bredSLR", TextViewDate.getText().toString());
                intent_sow_litter.putExtra("date_farrowSLR", TextViewFarrow.getText().toString());
                startActivity(intent_sow_litter);
            }
        });

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
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

    public static Date addDays(Date date, int days) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }
}
