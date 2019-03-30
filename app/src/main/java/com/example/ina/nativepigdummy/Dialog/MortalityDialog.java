package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MortalityAndSalesActivity;
import com.example.ina.nativepigdummy.Activities.SowLitterActivity;
import com.example.ina.nativepigdummy.Adapters.AutoAdapter;
import com.example.ina.nativepigdummy.Adapters.SalesDataAdapter;
import com.example.ina.nativepigdummy.Data.GetAllPigsData;
import com.example.ina.nativepigdummy.Data.SalesData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.AuthProvider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class MortalityDialog extends DialogFragment {

    private static final String TAG = "MortalityDialog";

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoAdapter autoAdapter;
    private EditText causeofdeath;
    private EditText dateofdeath;
    private String pigDateOfBirth;
    GetAllPigsData pigData;

    ArrayList<GetAllPigsData> pigList;
    List<String> stringList = new ArrayList<>();
    DatabaseHelper dbHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mortality,null);

        final AppCompatAutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.choose_pig);
        dateofdeath = view.findViewById(R.id.date_of_death);
        causeofdeath = view.findViewById(R.id.cause_of_death);
        dbHelper = new DatabaseHelper(getActivity());
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
                        stringList.clear();
                        generateAutocompletePigList(autoCompleteTextView.getText().toString());
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
                        RequestParams requestParams = buildRequest(autoCompleteTextView);
                        if(ApiHelper.isInternetAvailable(getContext())) {
                            deleteAddedPigFromPigTable(requestParams);
                            api_addMortalityRecord(requestParams);
                        }else{
                            local_addMortalityData(autoCompleteTextView);
                        }
                    }
                });

        return builder.create();
    }

    private void local_addMortalityData(AppCompatAutoCompleteTextView autoCompleteTextView) {
        final String editchoosepig = autoCompleteTextView.getText().toString();
        String editdateofdeath = dateofdeath.getText().toString();
        String editcauseofdeath = causeofdeath.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = java.util.Calendar.getInstance().getTime();
        String editage = "";
        String birthDate = "";

        Cursor data = dbHelper.getSinglePig(editchoosepig);
        while (data.moveToNext()) {
            switch (data.getString(data.getColumnIndex("property_id"))) {
                case "3":
                    birthDate = data.getString(data.getColumnIndex("value"));
                    break;
            }
        }

        if(editcauseofdeath.equals("")) editcauseofdeath = "Not specified";
        if(editdateofdeath.equals("")) editdateofdeath = sdf.format(date);
        if(birthDate.equals("Not specified")){
            editage = "Age unavailable";
        }else{
            try {
                editage = computeAge(editchoosepig, birthDate, editdateofdeath);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        boolean insertData = dbHelper.addToMortalitiesDB(editchoosepig, editdateofdeath, editcauseofdeath, editage, "false");

        if(insertData){
            Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MortalityAndSalesActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
    }


    private RequestParams buildRequest(AppCompatAutoCompleteTextView autoCompleteTextView) {
        RequestParams requestParams = new RequestParams();
        final String editchoosepig = autoCompleteTextView.getText().toString();
        final String editdateofdeath = dateofdeath.getText().toString();
        final String editcauseofdeath = causeofdeath.getText().toString();

        requestParams.add("pig_registration_id", editchoosepig);
        requestParams.add("date_removed_died", editdateofdeath);
        requestParams.add("cause_of_death", editcauseofdeath);

        getAgeMortality(requestParams);

        requestParams.add("age", "Age unavailable");

        return requestParams;
    }

    private String computeAge(String regId, String birthDate, String dateDied) throws ParseException {
        String date = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date firstDate = format.parse(birthDate);
        Date secondDate = format.parse(dateDied);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if ((Long.toString(diff / 30)).equals("1") && (Long.toString(diff % 30)).equals("1")) {
            date = Long.toString(diff / 30) + " month, " + Long.toString(diff % 30) + " day";
        } else if ((Long.toString(diff / 30).equals("1"))) {
            date = Long.toString(diff / 30) + " month, " + Long.toString(diff % 30) + " days";
        } else if ((Long.toString(diff % 30).equals("1"))) {
            date = Long.toString(diff / 30) + " months, " + Long.toString(diff % 30) + " day";
        } else {
            date = Long.toString(diff / 30) + " months, " + Long.toString(diff % 30) + " days";
        }
        return date;
    }

    private void deleteAddedPigFromPigTable(RequestParams requestParams) {
        ApiHelper.deletePig("deletePig", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                //Toast.makeText(getContext(), "Pig added successfully", Toast.LENGTH_SHORT);
                Log.d("DeletePig", "Succesfully deleted from pig table");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                //Toast.makeText(getActivity(), "Error in deleting pig", Toast.LENGTH_SHORT);
                Log.d("DeletePig", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void getAgeMortality(RequestParams requestParams){
        ApiHelper.getSinglePig("getSinglePig", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getPigAge", "Succesfully added");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getPigAge", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    pigDateOfBirth = jsonObject.getString("pig_birthdate");
                    jsonObject = (JSONObject) jsonArray.get(i);
                    stringList.add(jsonObject.getString("pig_birthdate"));
                }
                return null;
            }
        });
    }

    private void api_addMortalityRecord(RequestParams requestParams) {
        ApiHelper.addPigMortalitySales("addPigMortalitySales", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("addMortality", "Succesfully added");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("addMortality", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void generateAutocompletePigList(String text) {
        if(ApiHelper.isInternetAvailable(getContext())) {
            ApiHelper.searchPig("searchPig", null, new BaseJsonHttpResponseHandler<Object>() {

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
        } else{
            Cursor data = dbHelper.generatePigList(text);
            int numRows = data.getCount();
            if(numRows == 0){
                Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
            }else {
                while (data.moveToNext()) {
                    stringList.add(data.getString(0));
                }
                autoAdapter.setData(stringList);
                autoAdapter.notifyDataSetChanged();
            }
        }
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

}
