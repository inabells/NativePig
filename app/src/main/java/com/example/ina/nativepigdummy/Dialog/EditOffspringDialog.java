package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.OffspringFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EditOffspringDialog extends DialogFragment{
    private static final String TAG = "EditOffspringDialog";

    String offspringRegId;
    EditText textViewOffspring, textViewDateWeaned, textViewWeaningWeight, textViewBirthWeight;
    Spinner textViewSex;
    DatabaseHelper dbHelper;
    String editOffspringEarnotch;
    String editSex;
    String editDateWeaned;
    String editWeaningWeight, editBirthWeight;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_offspring, null);

        offspringRegId = getArguments().getString("OffspringRegId");
        textViewOffspring = view.findViewById(R.id.offspring_earnotch);
        textViewSex = view.findViewById(R.id.offspring_sex);
        textViewDateWeaned = view.findViewById(R.id.date_weaned);
        textViewWeaningWeight = view.findViewById(R.id.weaning_weight);
        textViewBirthWeight = view.findViewById(R.id.birth_weight);

        dbHelper = new DatabaseHelper(getActivity());

        if(ApiHelper.isInternetAvailable(getActivity()))
            api_getOffspringRecord(offspringRegId);
        else
            local_getOffspringRecord();

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ApiHelper.isInternetAvailable(getActivity()))
                    api_updateOffspringRecord();
//                    updateRegistryId()
                else
                    local_updateOffspringRecord();
            }
        });

        return builder.create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        List<Fragment> fragList = getFragmentManager().getFragments();
        Fragment fragment = null;
        for(int i=0; i<fragList.size(); i++)
            if(fragList.get(i) instanceof OffspringFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void local_getOffspringRecord() {
        Cursor data = dbHelper.getSinglePig(offspringRegId);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "1": textViewOffspring.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "6": textViewDateWeaned.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "7": textViewWeaningWeight.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }


    private void local_updateOffspringRecord() {
        editOffspringEarnotch = textViewOffspring.getText().toString();
        editSex = textViewSex.getSelectedItem().toString();
        editDateWeaned = textViewDateWeaned.getText().toString();
        editWeaningWeight = textViewWeaningWeight.getText().toString();

        String animalId = dbHelper.getAnimalId(offspringRegId);
        int animalIdInt = Integer.parseInt(animalId);

        dbHelper.insertOrReplaceInAnimalPropertyDB(1, animalIdInt, editOffspringEarnotch, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(2, animalIdInt, editSex, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(6, animalIdInt, editDateWeaned, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(7, animalIdInt, editWeaningWeight, "false");
    }

    private void api_updateOffspringRecord(){
        String tempSex = textViewSex.getSelectedItem().toString();
        String tempWeaningWeight = textViewWeaningWeight.getText().toString();
        String tempDateWeaned = textViewDateWeaned.getText().toString();
        String tempBirthWeight = textViewBirthWeight.getText().toString();
        String tempEarnotch = textViewOffspring.getText().toString();
        String tempRegistryId = offspringRegId;

        RequestParams params = new RequestParams();
//        params.add("registry_id", tempRegistryId);
        params.add("farmable_id", Integer.toString(MyApplication.id));
        params.add("breedable_id", Integer.toString(MyApplication.id));
        params.add("new_sex", tempSex);
        params.add("weaning_weight", tempWeaningWeight);
        params.add("date_weaned", tempDateWeaned);
        params.add("new_birth_weight", tempBirthWeight);
        params.add("old_earnotch", editOffspringEarnotch);
        params.add("new_earnotch", tempEarnotch);
        params.add("earnotch", editOffspringEarnotch);

        updateOffspringRecord(params);
        updateRegistryId(params);
    }

    private void updateOffspringRecord(RequestParams params) {
        ApiHelper.updateOffspringRecord("updateOffspringRecord", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("MorphChar", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void updateRegistryId(RequestParams params) {
        ApiHelper.editRegistryId("editRegistryId", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("MorphChar", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void api_getOffspringRecord(String id) {
        RequestParams params = new RequestParams();
        params.add("registry_id", id);

        ApiHelper.getAnimalProperties("getAnimalProperties", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                textViewOffspring.setText(editOffspringEarnotch);
                textViewSex.setSelection(getIndex(textViewSex, editSex));
                textViewDateWeaned.setText(editDateWeaned);
                textViewWeaningWeight.setText(editWeaningWeight);
                textViewBirthWeight.setText(editBirthWeight);
                Log.d("MorphChar", "Successfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("MorphChar", "Error: " + String.valueOf(statusCode));
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
                            case 1:
                                editOffspringEarnotch = propertyObject.get("value").toString();
                                break;
                            case 2:
                                editSex = propertyObject.get("value").toString();
                                break;
                            case 5:
                                editBirthWeight = propertyObject.get("value").toString();
                                break;
                            case 6:
                                editDateWeaned = propertyObject.get("value").toString();
                                break;
                            case 7:
                                editWeaningWeight = propertyObject.get("value").toString();
                                break;
                        }
                    }
                }
                return null;
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private String setDefaultTextIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "Not specified" : text);
    }

    public void onStart(){
        super.onStart();
        textViewDateWeaned.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Birth date");
                }
            }
        });
    }
}
