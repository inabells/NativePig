package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class StatusDialog extends DialogFragment {

    private static final String TAG = "StatusDialog";

    private Spinner status, editSowStatus;
    public ViewStatusListener onViewStatusListener;
    private String sow_id, boar_id, sow_status;

    public StatusDialog(String sowId, String boarId, String sowStatus){
        this.sow_id = sowId;
        this.boar_id = boarId;
        this.sow_status = sowStatus;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_status,null);

        status = view.findViewById(R.id.status);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ApiHelper.isInternetAvailable(getContext())){
                            RequestParams requestParams = buildParams(sow_id, boar_id);
                            updateSowStatus(requestParams);
                        }else{
                            Toast.makeText(getActivity(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return builder.create();
    }

    private void updateSowStatus(RequestParams params) {
        ApiHelper.updateSowStatus("updateSowStatus", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("API HANDLER FAIL", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private RequestParams buildParams(String sow_id, String boar_id) {
        RequestParams requestParams = new RequestParams();

        String editSowStatus = status.getSelectedItem().toString();

        requestParams.add("sow_registration_id", sow_id);
        requestParams.add("boar_registration_id", boar_id);
        requestParams.add("sow_status", editSowStatus);

        return requestParams;
    }

    public interface ViewStatusListener{
        //void applyStatus(String status);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewStatusListener = (ViewStatusListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("StatusListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}