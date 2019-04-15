package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.OffspringFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class IndividualWeighingDialog extends DialogFragment {

    private static final String TAG = "IndividualWeighingDialog";
    private EditText birthweight;
    private EditText offspringearnotch;
    private Spinner sex;
    private String addOffspringEarnotch, addBirthWeight, addSex, birthdate, sowRegId, boarRegId, sowId, boarId, groupingId, regId;

    DatabaseHelper dbHelper;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_individual_weighing,null);

        birthweight = view.findViewById(R.id.birth_weight);
        offspringearnotch = view.findViewById(R.id.offspring_earnotch);
        sex = view.findViewById(R.id.offspring_sex);
        dbHelper = new DatabaseHelper(getActivity());

        sowRegId = getActivity().getIntent().getStringExtra("sow_idSLR");
        boarRegId = getActivity().getIntent().getStringExtra("boar_idSLR");
        sowId = dbHelper.getAnimalId(sowRegId);
        boarId = dbHelper.getAnimalId(boarRegId);
        groupingId = dbHelper.getGroupingId(sowId, boarId);

        builder.setView(view)
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){

                }
            })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addOffspringEarnotch = offspringearnotch.getText().toString();
                    addBirthWeight = birthweight.getText().toString();
                    addSex = sex.getSelectedItem().toString();
                    if(addOffspringEarnotch.equals(""))
                        Toast.makeText(getActivity(), "Please fill out Animal Earnotch", Toast.LENGTH_SHORT).show();
                    else if(addOffspringEarnotch.length() > 6)
                        Toast.makeText(getActivity(), "Earnotch is too long", Toast.LENGTH_SHORT).show();
                    else{
                        if(!addOffspringEarnotch.equals("") && addOffspringEarnotch.length() < 6)
                            addOffspringEarnotch = padLeftZeros(addOffspringEarnotch, 6);

                        if(!addOffspringEarnotch.equals("") && !addBirthWeight.equals("")){
                            RequestParams requestParams = new RequestParams();
                            requestParams.add("farmable_id", Integer.toString(MyApplication.id));
                            requestParams.add("breedable_id", Integer.toString(MyApplication.id));
                            requestParams.add("sow_id", sowRegId);
                            requestParams.add("boar_id", boarRegId);
                            requestParams.add("offspring_earnotch", addOffspringEarnotch);
                            requestParams.add("sex",addSex);
                            requestParams.add("birth_weight", addBirthWeight);

                            if(ApiHelper.isInternetAvailable(getActivity()))
                                api_addOffspringIndividual(requestParams);
                            else
                                local_addOffspringIndividual();
                        }
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
            if(fragList.get(i) instanceof OffspringFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void requestFocus (View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void api_addOffspringIndividual(RequestParams requestParams){
        ApiHelper.addIndividualSowLitterRecord("addIndividualSowLitterRecord", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
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

    private void local_addOffspringIndividual() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        birthdate = formatter.format(date);

        regId = generateRegistrationId(addOffspringEarnotch);

        dbHelper.addToAnimalDB(regId, "Grower", "false");

        String animalId = dbHelper.getAnimalId(regId);
        int animalIdInt = Integer.parseInt(animalId);

        dbHelper.insertOrReplaceInAnimalPropertyDB(1, animalIdInt, addOffspringEarnotch, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(2, animalIdInt, sex.getSelectedItem().toString(), "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(3, animalIdInt, birthdate, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(4, animalIdInt, regId, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(5, animalIdInt, birthweight.getText().toString(), "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(7, animalIdInt, "Weight unavailable", "false");

        dbHelper.addToGroupingMembersDB(groupingId, animalId, "false");
    }

    private String generateRegistrationId(String addOffspringEarnotch) {
        return dbHelper.getFarmCode() + dbHelper.getFarmBreed() +"-"+ getYear(birthdate) + sex.getSelectedItem().toString() + addOffspringEarnotch;
    }

    public static String padLeftZeros(String str, int n) {
        return String.format("%1$" + n + "s", str).replace(' ', '0');
    }

    private String getYear(String dateText){
        String[] textArray = dateText.split("-");
        return textArray[0];
    }
}
