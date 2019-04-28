package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class ViewGrowerDialog extends DialogFragment {
    private static final String TAG = "ViewGrowerDialog";
    private EditText weightat45;
    private EditText weightat60;
    private EditText weightat90;
    private EditText weightat150;
    private EditText weightat180;
    private EditText datecollected45;
    private EditText datecollected60;
    private EditText datecollected90;
    private EditText datecollected150;
    private EditText datecollected180;
    private Button editButton;
    private String reg_id;
    private String editweight45, editweight60, editweight90, editweight150, editweight180;
    private String editdate45, editdate60, editdate90, editdate150, editdate180;
    private ImageView exit_profile;
    DatabaseHelper dbHelper;

    public ViewGrowerDialog(String reg_id){
        this.reg_id = reg_id;
    }

   // @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_view_grower, null);
        //editButton = view.findViewById(R.id.edit_button);
        //exit_profile = view.findViewById(R.id.exit_dialog);

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
        dbHelper = new DatabaseHelper(getActivity());

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

    private void local_getWeightProfile(String reg_id) {
        Cursor data = dbHelper.getSinglePig(reg_id);
        while(data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
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

    private void local_updateWeightRecords() {
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

        boolean insertData = dbHelper.addWeightRecords(reg_id, editdate45, editdate60, editdate90, editdate150, editdate180,
                editweight45, editweight60, editweight90, editweight150, editweight180, "false");

        if(insertData) Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
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

        requestParams.add("registry_id", reg_id);
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
                weightat45.setText(setBlankIfNull(editweight45));
                weightat60.setText(setBlankIfNull(editweight60));
                weightat90.setText(setBlankIfNull(editweight90));
                weightat150.setText(setBlankIfNull(editweight150));
                weightat180.setText(setBlankIfNull(editweight180));
                datecollected45.setText(setBlankIfNull(editdate45));
                datecollected60.setText(setBlankIfNull(editdate60));
                datecollected90.setText(setBlankIfNull(editdate90));
                datecollected150.setText(setBlankIfNull(editdate150));
                datecollected180.setText(setBlankIfNull(editdate180));
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
                            case 32:
                                editweight45 = propertyObject.get("value").toString();
                                break;
                            case 33:
                                editweight60 = propertyObject.get("value").toString();
                                break;
                            case 34:
                                editweight90 = propertyObject.get("value").toString();
                                break;
                            case 35:
                                editweight150 = propertyObject.get("value").toString();
                                break;
                            case 36:
                                editweight180 = propertyObject.get("value").toString();
                                break;
                            case 37:
                                editdate45 = propertyObject.get("value").toString();
                                break;
                            case 38:
                                editdate60 = propertyObject.get("value").toString();
                                break;
                            case 39:
                                editdate90 = propertyObject.get("value").toString();
                                break;
                            case 40:
                                editdate150 = propertyObject.get("value").toString();
                                break;
                            case 41:
                                editdate180 = propertyObject.get("value").toString();
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
}
