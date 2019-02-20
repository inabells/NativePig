package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.AddNewPigActivity;
import com.example.ina.nativepigdummy.Activities.MortalityAndSalesActivity;
import com.example.ina.nativepigdummy.Adapters.AutoAdapter;
import com.example.ina.nativepigdummy.Data.GetAllPigsData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.MortalityFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MortalityDialog extends DialogFragment {

    private static final String TAG = "MortalityDialog";

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoAdapter autoAdapter;
    private EditText dateofdeath;
    private EditText causeofdeath;

    ArrayList<GetAllPigsData> pigList;
    List<String> stringList = new ArrayList<>();
    DatabaseHelper myDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mortality,null);

        final AppCompatAutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.choose_pig);
        dateofdeath = view.findViewById(R.id.date_of_death);
        causeofdeath = view.findViewById(R.id.cause_of_death);
        myDB = new DatabaseHelper(getActivity());
        pigList = new ArrayList<>();

        //Setting up the adapter for AutoSuggest
        autoAdapter = new AutoAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoAdapter);
        autoCompleteTextView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selectedText.setText(autoAdapter.getObject(position));
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestParams requestParams = new RequestParams();
                        final String editchoosepig = autoCompleteTextView.getText().toString();
                        final String editdateofdeath = dateofdeath.getText().toString();
                        final String editcauseofdeath= causeofdeath.getText().toString();
                        final String editage = "0 months, 0 days";

                        if(ApiHelper.isInternetAvailable(getContext())) {
                                requestParams.add("pig_registration_id", editchoosepig);
                                requestParams.add("date_died_removed", editdateofdeath);
                                requestParams.add("cause_of_death", editcauseofdeath);
                                requestParams.add("age", editage);

                                ApiHelper.addPigMortalitySales("addPigMortalitySales", requestParams, new BaseJsonHttpResponseHandler<Object>() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
    //                                    Toast.makeText(getActivity(), "Pig added successfully", Toast.LENGTH_SHORT).show();
    //                                    addMortalityData(editchoosepig, editdateofdeath, editcauseofdeath,editage);
    //                                    autoCompleteTextView.setText("");
    //                                    dateofdeath.setText("");
    //                                    causeofdeath.setText("");
                                        Log.d("addMortality", "Succesfully added");

                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                                       // Toast.makeText(getActivity(), "Error in adding pig", Toast.LENGTH_SHORT);
                                        Log.d("addMortality", "ERROR BITCH");
                                    }

                                    @Override
                                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                        return null;
                                    }
                                });

                        } else{
                            Toast.makeText(getActivity(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return builder.create();
    }

    private void makeApiCall(String text) {
        ApiHelper.getAllPigs("getAllPigs", null, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                autoAdapter.setData(stringList);
                autoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
                Log.d("API HANDLER FAIL", errorResponse.toString());
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    stringList.add(jsonObject.getString("pig_registration_id"));
                }
                return null;
            }
        });

    }

    private void requestFocus (View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void onStart(){
        super.onStart();
        dateofdeath.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Mortality Fragment");
                }
            }
        });
    }

    public void addMortalityData(String choosepig,String datedied, String causeofdeath, String age){
        boolean insertData = myDB.addMortalityData(choosepig,datedied,causeofdeath,age);

        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    public void computeAge(){

    }

}
