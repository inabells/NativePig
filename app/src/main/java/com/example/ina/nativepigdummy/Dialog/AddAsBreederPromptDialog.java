package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.GrowerRecordsActivity;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class AddAsBreederPromptDialog extends DialogFragment{
    private static final String TAG = "AddAsBreederPromptDialog";

    private String reg_id;
    DatabaseHelper dbHelper;

    @SuppressLint("ValidFragment")
    public AddAsBreederPromptDialog(String reg_id){
        this.reg_id = reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_as_breeder_prompt,null);

        dbHelper = new DatabaseHelper(getActivity());

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
//                        Intent intent = new Intent(getActivity(), GrowerRecordsActivity.class);
//                        startActivity(intent);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestParams params = buildParams();

                        if(ApiHelper.isInternetAvailable(getContext())){
                            api_addAsBreeder(params);
                        } else{
                            dbHelper.addAsBreeder(reg_id);
                        }

                        Intent intent = new Intent(getActivity(), GrowerRecordsActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }

    private RequestParams buildParams(){
        RequestParams params = new RequestParams();
        params.add("pig_registration_id", reg_id);
        return params;
    }

    private void api_addAsBreeder(RequestParams params){
        ApiHelper.addAsBreeder("addAsBreeder", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("deletePigFromServer", "Successfully delete pigs from server");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("deletePigFromServer", "Error in deleting");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });

    }

}
