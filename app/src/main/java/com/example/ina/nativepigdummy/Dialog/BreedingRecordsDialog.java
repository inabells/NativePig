package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Adapters.AutoAdapter;
import com.example.ina.nativepigdummy.Adapters.BoarDataAdapter;
import com.example.ina.nativepigdummy.Adapters.SowDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Data.GetAllPigsData;
import com.example.ina.nativepigdummy.Data.SowData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class BreedingRecordsDialog extends DialogFragment {

    private static final String TAG = "BreedingRecordsDialog";

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoAdapter autoAdapter;
    private EditText datebred;

    ArrayList<SowData> sowDataList;
    ArrayList<BoarData> boarDataList;
    List<String> sowStringList = new ArrayList<>();
    List<String> boarStringList = new ArrayList<>();
    DatabaseHelper myDB;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_breeding_records,null);

        final AppCompatAutoCompleteTextView sowid = view.findViewById(R.id.sow_id);
        final AppCompatAutoCompleteTextView boarid = view.findViewById(R.id.boar_id);
        datebred = view.findViewById(R.id.date_bred);
        myDB = new DatabaseHelper(getActivity());
        sowDataList = new ArrayList<>();
        boarDataList = new ArrayList<>();

        //Setting up the adapter for AutoSuggest
        autoAdapter = new AutoAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line);
        sowid.setThreshold(2);
        boarid.setThreshold(2);
        sowid.setAdapter(autoAdapter);
        boarid.setAdapter(autoAdapter);
        sowid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selectedText.setText(autoAdapter.getObject(position));
            }
        });

        boarid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selectedText.setText(autoAdapter.getObject(position));
            }
        });

        sowid.addTextChangedListener(new TextWatcher() {
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

        boarid.addTextChangedListener(new TextWatcher() {
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
                    if (!TextUtils.isEmpty(sowid.getText())) {
                        makeApiCallSow(sowid.getText().toString());
                    }
                    if (!TextUtils.isEmpty(boarid.getText())) {
                        makeApiCallBoar(boarid.getText().toString());
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
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editsowid = sowid.getText().toString();
                        String editboarid = boarid.getText().toString();
                        String editdatebred = datebred.getText().toString();

                        if(editsowid.length() != 0 && editboarid.length() != 0 && editdatebred.length() != 0){
                            addBreedingRecordsData(editsowid, editboarid, editdatebred);
                            sowid.setText("");
                            boarid.setText("");
                            datebred.setText("");
                        }else{
                            Toast.makeText(getActivity(),"Please fill out all the fields!",Toast.LENGTH_LONG).show();
                        }

                    }

                });

        return builder.create();
    }

    private void makeApiCallSow(String text) {
        ApiHelper.getSows("getAllSows", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                autoAdapter.setData(sowStringList);
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
                    sowStringList.add(jsonObject.getString("pig_registration_id"));
                }
                return null;
            }
        });

    }

    private void makeApiCallBoar(String text) {
        ApiHelper.getBoars("getAllBoars", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                autoAdapter.setData(boarStringList);
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
                    boarStringList.add(jsonObject.getString("pig_registration_id"));
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
        datebred.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"BreedingRecordsDialog");
                }
            }
        });

    }

    public void addBreedingRecordsData(String sowid, String boarid, String datebred){
        boolean insertData = myDB.addBreedingRecordsData(sowid, boarid, datebred);
        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
        }
    }

}
