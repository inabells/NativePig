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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class BreederWeightRecordsDialog extends DialogFragment {
    private static final String TAG = "BreederWeightRecordsDialog";
    private String reg_id;
    private EditText weightat45, weightat60, weightat90, weightat150, weightat180;
    private EditText datecollected45, datecollected60, datecollected90, datecollected150, datecollected180;
    private String editWeight45, editWeight60, editWeight90, editWeight150, editWeight180;
    private String editDate45, editDate60, editDate90, editDate150, editDate180;
    public ViewWeightRecordListener onViewWeightRecordListener;

    public BreederWeightRecordsDialog(String reg_id){
        this.reg_id = reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_breeder_weight_records,null);

        weightat45 = view.findViewById(R.id.weight_45_days);
        weightat60 = view.findViewById(R.id.weight_60_days);
        weightat90 = view.findViewById(R.id.weight_90_days);
        weightat150 = view.findViewById(R.id.weight_150_days);
        weightat180 = view.findViewById(R.id.weight_180_days);
        datecollected45 = view.findViewById(R.id.date_collected_45_days);
        datecollected60 = view.findViewById(R.id.date_collected_60_days);
        datecollected90 = view.findViewById(R.id.date_collected_90_days);
        datecollected150 = view.findViewById(R.id.date_collected_150_days);
        datecollected180 = view.findViewById(R.id.date_collected_180_days);

        getWeightProfile(reg_id);

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
                            RequestParams requestParams = buildParams(reg_id);
                            updateWeightRecords(requestParams);
                        }else{
                            Toast.makeText(getActivity(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return builder.create();
    }

    private void updateWeightRecords(RequestParams params) {
        ApiHelper.updateWeightRecords("updateWeightRecords", params, new BaseJsonHttpResponseHandler<Object>() {
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

    private RequestParams buildParams(String reg_id) {
        RequestParams requestParams = new RequestParams();
        String editweight45 = weightat45.getText().toString();
        String editweight60 = weightat60.getText().toString();
        String editweight90 = weightat90.getText().toString();
        String editweight150 = weightat150.getText().toString();
        String editweight180 = weightat180.getText().toString();
        String editdate45 = datecollected45.getText().toString();
        String editdate60 = datecollected60.getText().toString();
        String editdate90 = datecollected90.getText().toString();
        String editdate150 = datecollected150.getText().toString();
        String editdate180 = datecollected180.getText().toString();

        requestParams.add("registration_id", reg_id);
        requestParams.add("weight_at_45", editweight45);
        requestParams.add("weight_at_60", editweight60);
        requestParams.add("weight_at_90", editweight90);
        requestParams.add("weight_at_150", editweight150);
        requestParams.add("weight_at_180", editweight180);
        requestParams.add("date_collected_at_45", editdate45);
        requestParams.add("date_collected_at_60", editdate60);
        requestParams.add("date_collected_at_90", editdate90);
        requestParams.add("date_collected_at_150", editdate150);
        requestParams.add("date_collected_at_180", editdate180);

        return requestParams;
    }

    private void getWeightProfile(String id) {
        RequestParams params = new RequestParams();
        params.add("registration_id", id);

        ApiHelper.getWeightProfile("getWeightProfile", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                weightat45.setText(setBlankIfNull(editWeight45));
                weightat60.setText(setBlankIfNull(editWeight60));
                weightat90.setText(setBlankIfNull(editWeight90));
                weightat150.setText(setBlankIfNull(editWeight150));
                weightat180.setText(setBlankIfNull(editWeight180));
                datecollected45.setText(setBlankIfNull(editDate45));
                datecollected60.setText(setBlankIfNull(editDate60));
                datecollected90.setText(setBlankIfNull(editDate90));
                datecollected150.setText(setBlankIfNull(editDate150));
                datecollected180.setText(setBlankIfNull(editDate180));
                Log.d("BreederWeightRecords", "Successfully fetched");
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

    private String setBlankIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "" : text);
    }

    private void requestFocus (View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onStart(){
        super.onStart();
        datecollected45.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected60.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected90.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected150.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected180.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

    }

    public interface ViewWeightRecordListener{
        void applyWeight45(String weightat45);
        void applyWeight60(String weightat60);
        void applyWeight90(String weightat90);
        void applyWeight150(String weightat150);
        void applyWeight180(String weightat180);
        void applyDate45(String datecollected45);
        void applyDate60(String datecollected60);
        void applyDate90(String datecollected90);
        void applyDate150(String datecollected150);
        void applyDate180(String datecollected180);
    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewWeightRecordListener = (ViewWeightRecordListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("WeightRecordListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }
}
