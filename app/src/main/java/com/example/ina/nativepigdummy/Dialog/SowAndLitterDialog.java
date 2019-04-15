package com.example.ina.nativepigdummy.Dialog;

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
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.SowAndLitterFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SowAndLitterDialog extends DialogFragment {
    private static final String TAG = "SowAndLitterDialog";

    private EditText parity;
    private EditText nostillborn;
    private EditText nomummified;
    private EditText abnormalities;
    private String sowRegId, boarRegId;
    private DatabaseHelper dbHelper;
    public ViewSowLitterListener onViewSowLitterListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sow_and_litter, null);

        dbHelper = new DatabaseHelper(getContext());
        sowRegId = getActivity().getIntent().getStringExtra("sow_idSLR");
        boarRegId = getActivity().getIntent().getStringExtra("boar_idSLR");

        parity = view.findViewById(R.id.parity);
        nostillborn = view.findViewById(R.id.number_stillborn);
        nomummified = view.findViewById(R.id.number_mummified);
        abnormalities = view.findViewById(R.id.abnormalities);

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editparity = parity.getText().toString();
                String editnostillborn = nostillborn.getText().toString();
                String editnomummified = nomummified.getText().toString();
                String editabnormalities = abnormalities.getText().toString();

                RequestParams requestParams = new RequestParams();
                requestParams.add("farmable_id", Integer.toString(MyApplication.id));
                requestParams.add("breedable_id", Integer.toString(MyApplication.id));
                requestParams.add("sow_id", sowRegId);
                requestParams.add("boar_id", boarRegId);
                requestParams.add("number_stillborn", editnostillborn);
                requestParams.add("number_mummified", editnomummified);
                requestParams.add("abnormalities", editabnormalities);
                requestParams.add("parity", editparity);

                if(ApiHelper.isInternetAvailable(getActivity()))
                    api_updateSowLitterRecord(requestParams);
                else
                    local_updateSowLitterRecord();
            }
        });

        if(ApiHelper.isInternetAvailable(getContext())){
            Log.d("sowLitterDialog", "mayInternet!!");
        }else{
            local_getSowLitterRecord(sowRegId, boarRegId);
        }

        return builder.create();

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        List<Fragment> fragList = getFragmentManager().getFragments();
        Fragment fragment = null;
        for(int i=0; i<fragList.size(); i++)
            if(fragList.get(i) instanceof SowAndLitterFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void local_updateSowLitterRecord() {
        String editparity = parity.getText().toString();
        String editnostillborn = nostillborn.getText().toString();
        String editnomummified = nomummified.getText().toString();
        String editabnormalities = abnormalities.getText().toString();

        String sowId = dbHelper.getAnimalId(sowRegId);
        String boarId = dbHelper.getAnimalId(boarRegId);

        boolean insertData = dbHelper.updateSowLitter(sowId, boarId, editparity, editnostillborn, editnomummified, editabnormalities, "false");

        if(insertData) Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
    }

    private void local_getSowLitterRecord(String sowRegId, String boarRegId) {
        String sowId = dbHelper.getAnimalId(sowRegId);
        String boarId = dbHelper.getAnimalId(boarRegId);

        Cursor data = dbHelper.getSowLitterRecords(sowId, boarId);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "45": nostillborn.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "46": nomummified.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "47": abnormalities.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "48": parity.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;

            }
        }
    }

    private void api_updateSowLitterRecord(RequestParams requestParams){
        ApiHelper.editSowLitterRecord("editSowLitterRecord", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("SowLitter", "Successfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("SowLitter", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
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

    public interface ViewSowLitterListener{
        void applyDateFarrowed(String datefarrowed);
        void applyParity(String parity);
        void applyNoMummified(String nomummified);
        void applyNoStillBorn(String nostillborn);
        void applyAbnormalities(String abnormalities);
    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewSowLitterListener = (ViewSowLitterListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("SowLitterListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}
