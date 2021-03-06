package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.BreederWeightRecordsFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class BreederWeightRecordsDialog extends DialogFragment {
    private static final String TAG = "BreederWeightRecordsDialog";

    private DatabaseHelper dbHelper;
    private String reg_id;
    private EditText weaningWeight, weightat45, weightat60, weightat90, weightat150, weightat180;
    private EditText datecollected45, datecollected60, datecollected90, datecollected150, datecollected180;
    private String editWeaningWeight, editWeight45, editWeight60, editWeight90, editWeight150, editWeight180;
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

        dbHelper = new DatabaseHelper(getContext());
        weaningWeight = view.findViewById(R.id.weaningweight);
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

        if(ApiHelper.isInternetAvailable(getContext())){
            getWeightProfile(reg_id);
        } else{
            local_getWeightProfile(reg_id);
        }

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
                            api_updateWeightRecords(requestParams);
                        }else{
                            local_updateWeightRecords();
                        }
                    }

                });

        return builder.create();
    }
    @Override
    public void onDismiss(final DialogInterface dialog) {
        List<Fragment> fragList = getFragmentManager().getFragments();
        Fragment fragment = null;
        for(int i=0; i<fragList.size(); i++)
            if(fragList.get(i) instanceof BreederWeightRecordsFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }


    private void local_getWeightProfile(String reg_id) {
        Cursor data = dbHelper.getSinglePig(reg_id);
        while(data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "7": weaningWeight.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "32": weightat45.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "33": weightat60.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "34": weightat90.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "35": weightat150.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "36": weightat180.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "37": datecollected45.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "38": datecollected60.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "39": datecollected90.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "40": datecollected150.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "41": datecollected180.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }

    private void local_updateWeightRecords() {
        String weaningweight = weaningWeight.getText().toString();
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

        boolean insertData = dbHelper.addWeightRecords(reg_id, weaningweight, editdate45, editdate60, editdate90, editdate150, editdate180,
                editweight45, editweight60, editweight90, editweight150, editweight180, "false");

        if(insertData) Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
    }

    private void api_updateWeightRecords(RequestParams params) {
        ApiHelper.fetchWeightRecords("fetchWeightRecords", params, new BaseJsonHttpResponseHandler<Object>() {
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
        String weaningweight = weaningWeight.getText().toString();
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

        requestParams.add("registry_id", reg_id);
        requestParams.add("weaning_weight", weaningweight);
        requestParams.add("body_weight_at_45_days", editweight45);
        requestParams.add("body_weight_at_60_days", editweight60);
        requestParams.add("body_weight_at_90_days", editweight90);
        requestParams.add("body_weight_at_150_days", editweight150);
        requestParams.add("body_weight_at_180_days", editweight180);
        requestParams.add("date_collected_45_days", editdate45);
        requestParams.add("date_collected_60_days", editdate60);
        requestParams.add("date_collected_90_days", editdate90);
        requestParams.add("date_collected_150_days", editdate150);
        requestParams.add("date_collected_180_days", editdate180);

        return requestParams;
    }

    private void getWeightProfile(String id) {
        RequestParams params = new RequestParams();
        params.add("registry_id", id);

        ApiHelper.getAnimalProperties("getAnimalProperties", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                weaningWeight.setText(setBlankIfNull(editWeaningWeight));
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

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONArray propertyArray = jsonObject.getJSONArray("properties");
                    JSONObject propertyObject;
                    for (int i = propertyArray.length() - 1; i >= 0; i--) {
                        propertyObject = (JSONObject) propertyArray.get(i);
                        switch (propertyObject.getInt("property_id")) {
                            case 7:
                                editWeaningWeight = propertyObject.get("value").toString();
                                break;
                            case 32:
                                editWeight45 = propertyObject.get("value").toString();
                                break;
                            case 33:
                                editWeight60 = propertyObject.get("value").toString();
                                break;
                            case 34:
                                editWeight90 = propertyObject.get("value").toString();
                                break;
                            case 35:
                                editWeight150 = propertyObject.get("value").toString();
                                break;
                            case 36:
                                editWeight180 = propertyObject.get("value").toString();
                                break;
                            case 37:
                                editDate45 = propertyObject.get("value").toString();
                                break;
                            case 38:
                                editDate60 = propertyObject.get("value").toString();
                                break;
                            case 39:
                                editDate90 = propertyObject.get("value").toString();
                                break;
                            case 40:
                                editDate150 = propertyObject.get("value").toString();
                                break;
                            case 41:
                                editDate180 = propertyObject.get("value").toString();
                                break;
                        }
                    }
                }
                return null;
            }
        });
    }

    private String setBlankIfNull(String text) {
        if(text==null) return "";
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
                    NewDateDialog dialog = new NewDateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected60.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    NewDateDialog dialog = new NewDateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected90.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    NewDateDialog dialog = new NewDateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected150.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    NewDateDialog dialog = new NewDateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected180.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    NewDateDialog dialog = new NewDateDialog(v);
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
