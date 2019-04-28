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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GroupWeighingDialog extends DialogFragment {

    private static final String TAG = "GroupWeighingDialog";

    private EditText offspringearnotch, litterbirthweight, littersizebornalive, dateweaned;
    private Spinner sex;
    String sowId, sowRegId, boarRegId, boarId, groupingId, addOffspringEarnotch, addDateWeaned, addLSBA, addLBW, birthdate, regId;
    int birthweight;

    DatabaseHelper dbHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_group_weighing,null);

        litterbirthweight = view.findViewById(R.id.litter_birth_weight);
//        offspringearnotch = view.findViewById(R.id.offspring_earnotch);
//        sex = view.findViewById(R.id.offspring_sex);
        littersizebornalive = view.findViewById(R.id.litter_size_born_alive);
        dateweaned = view.findViewById(R.id.dateweaned);
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
                    addDateWeaned = dateweaned.getText().toString();
//                    addSex = sex.getSelectedItem().toString();
                    addLBW = litterbirthweight.getText().toString();
                    addLSBA = littersizebornalive.getText().toString();

                    if(!addDateWeaned.equals("") && !addLSBA.equals("") && !addLBW.equals("")){
                        int counter=0;
                        String temp = "TMP";
                        String tempEarnotch = temp + Integer.toString(counter);
                        RequestParams requestParams = new RequestParams();
                        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
                        requestParams.add("breedable_id", Integer.toString(MyApplication.id));
                        requestParams.add("sow_id", sowRegId);
                        requestParams.add("boar_id", boarRegId);
                        requestParams.add("litter_birth_weight", addLBW);
                        requestParams.add("lsba", addLSBA);
                        requestParams.add("offspring_earnotch", tempEarnotch);
                        requestParams.add("sex", "F");

                        for(int i = 0; i < Integer.parseInt(addLSBA); i++){
                            counter++;
                            tempEarnotch = temp + Integer.toString(counter);
                            requestParams.remove("offspring_earnotch");
                            requestParams.add("offspring_earnotch", tempEarnotch);
                            if(ApiHelper.isInternetAvailable(getActivity())) {
                                api_addOffspringGroup(requestParams);
                            } else
                                local_addOffspringGroup(i);
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

    private void api_addOffspringGroup(RequestParams requestParams){
        ApiHelper.addGroupSowLitterRecord("addGroupSowLitterRecord", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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

    private void requestFocus (View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void local_addOffspringGroup(int counter) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        birthdate = formatter.format(date);

        regId = generateRegistrationId(addOffspringEarnotch);

        String groupingId = dbHelper.getGroupingId(sowId, boarId);
        int groupingIdInt = Integer.parseInt(groupingId);

        if(addLBW != null || !addLBW.equals(""))
            dbHelper.insertOrReplaceInGroupingsPropertyDB(55, groupingIdInt, addLBW, "false");
        if(addLSBA != null || !addLSBA.equals(""))
            dbHelper.insertOrReplaceInGroupingsPropertyDB(50, groupingIdInt, addLSBA, "false");

        dbHelper.addToAnimalDB(regId, "Temporary", "false");

        String animalId = dbHelper.getAnimalId(regId);
        int animalIdInt = Integer.parseInt(animalId);
        animalIdInt += counter;

        dbHelper.insertOrReplaceInAnimalPropertyDB(4, animalIdInt, regId, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(3, animalIdInt,  birthdate, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(2, animalIdInt, sex.getSelectedItem().toString(), "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(5, animalIdInt, Long.toString(Math.round(Double.parseDouble(addLBW)/Double.parseDouble(addLSBA))), "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(7, animalIdInt, "Weight unavailable", "false");

        dbHelper.addToGroupingMembersDB(groupingId, animalId, "false");

        dbHelper.insertOrReplaceInGroupingsPropertyDB(3, groupingIdInt, birthdate, "false");
        dbHelper.insertOrReplaceInGroupingsPropertyDB(60, groupingIdInt, "Farrowed", "false");
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
