package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewProfileDialog extends AppCompatDialogFragment {
    private EditText farmname, contactno, region, town, barangay;
    private String editRegion, editContactNo, editTown, editBarangay;
    private DatabaseHelper dbHelper;
    private ViewProfileListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_profile,null);

//        farmname = view.findViewById(R.id.farm_name);
        contactno = view.findViewById(R.id.contact_no);
        region = view.findViewById(R.id.region);
        town = view.findViewById(R.id.town);
        barangay = view.findViewById(R.id.barangay);
        dbHelper = new DatabaseHelper(getActivity());

        RequestParams requestParams = new RequestParams();
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));

        if(ApiHelper.isInternetAvailable(getActivity()))
            api_getFarmProfile(requestParams);
        else
            local_getFarmProfile();

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ApiHelper.isInternetAvailable(getActivity()))
                            api_updateFarmProfile();
                        else
                            local_updateFarmProfile();

                    }
                });

        return builder.create();
    }

    private void local_getFarmProfile() {
        Cursor dataFromFarm = dbHelper.getFarmProfile(MyApplication.id);
        if(dataFromFarm.moveToFirst()) {
            editRegion = dataFromFarm.getString(dataFromFarm.getColumnIndex("region"));
            editTown = dataFromFarm.getString(dataFromFarm.getColumnIndex("town"));
            editBarangay = dataFromFarm.getString(dataFromFarm.getColumnIndex("barangay"));
//            editContactNo = dataFromUsers.getString(dataFromUsers.getColumnIndex("phone"));
        }

        Cursor dataFromUsers = dbHelper.getUserProfile(MyApplication.id);
        if(dataFromUsers.moveToFirst()){
            editContactNo = dataFromUsers.getString(dataFromUsers.getColumnIndex("phone"));
        }

        region.setText(editRegion);
        barangay.setText(editBarangay);
        town.setText(editTown);
        contactno.setText(editContactNo);
    }

    public void local_updateFarmProfile(){
        String contactNoString = contactno.getText().toString();
        String regionTextString = region.getText().toString();
        String townString = town.getText().toString();
        String barangayString = barangay.getText().toString();

        dbHelper.updateFarmProfile(contactNoString, regionTextString, townString, barangayString, MyApplication.id);
    }

   private void api_updateFarmProfile(){
        editBarangay = barangay.getText().toString();
        editContactNo = contactno.getText().toString();
        editRegion = region.getText().toString();
        editTown = town.getText().toString();

        RequestParams params = new RequestParams();
        params.add("farmable_id", Integer.toString(MyApplication.id));
        params.add("breedable_id", Integer.toString(MyApplication.id));
        params.add("region", editRegion);
        params.add("town", editTown);
        params.add("barangay", editBarangay);
        params.add("phone_number", editContactNo);

        updateFarmProfile(params);
    }

    private void updateFarmProfile(RequestParams params) {

        ApiHelper.editFarmProfile("editFarmProfile", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("editFarmProfile", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void api_getFarmProfile(RequestParams params) {
        ApiHelper.getFarmProfilePage("getFarmProfilePage", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                region.setText(editRegion);
                barangay.setText(editBarangay);
                contactno.setText(editContactNo);
                town.setText(editTown);
                Log.d("getFarmProfile", "Successfully fetched farm profile");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getFarmProfile", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONObject jsonObjectFarm = jsonObject.getJSONObject("farm");
                    JSONObject jsonObjectBreed = jsonObject.getJSONObject("breed");
                    editContactNo = jsonObjectFarm.getString("phone");
                    editTown = jsonObjectBreed.getString("town");
                    editBarangay = jsonObjectBreed.getString("barangay");
                    editRegion = jsonObjectBreed.getString("region");
                }
                return null;
            }
        });
    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (ViewProfileListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ViewProfileListener");

        }
    }

    public interface ViewProfileListener{
        void applyFarmName(String farmname);
        void applyContactNo(String contactno);
        void applyRegion(String region);
        void applyProvince(String province);
        void applyTown(String town);
        void applyBarangay(String barangay);
    }



//    public void addProfileRecordData(String farmname, String contactno, String region, String province, String town, String barangay){
//        boolean insertData = myDB.addProfileRecordData(farmname,contactno,region,province,town,barangay);
//
//        if(insertData==true){
//            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
//        }
//    }

}
