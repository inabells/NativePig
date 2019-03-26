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
import com.example.ina.nativepigdummy.Fragments.ViewBreederFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class ViewBreederDialog extends DialogFragment {
    private EditText birthday;
    private EditText sex;
    private EditText birthweight;
    private EditText weaningweight;
    private EditText littersizebornweight;
    private EditText ageatfirstmating;
    private EditText ageatweaning;
    private EditText pedigreemother;
    private EditText pedigreefather;
    public ViewBreederListener onViewBreederListener;
    private String pig_reg_id, pig_bday, pig_sex, pig_birthweight, pig_weaningweight, pig_littersize,
                pig_ageatfirstmate, pig_ageatweaning, pig_pedigreemother, pig_pedigreefather;
    private String editBirthday, editSex, editBirthWeight, editWeaningWeight, editLitterSize, editAgeMating, editAgeWean, editPedigreeMother, editPedigreeFather;
    private DatabaseHelper dbHelper;

    public ViewBreederDialog(String pig_reg_id){
        this.pig_reg_id = pig_reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_breeder,null);

        dbHelper = new DatabaseHelper(getContext());
        birthday = view.findViewById(R.id.birthday);
        sex = view.findViewById(R.id.sex);
        birthweight = view.findViewById(R.id.birth_weight);
        weaningweight = view.findViewById(R.id.weaning_weight);
        littersizebornweight = view.findViewById(R.id.litter_size_born_alive);
        ageatfirstmating = view.findViewById(R.id.age_at_first_mating);
        ageatweaning = view.findViewById(R.id.age_at_weaning);
        pedigreemother = view.findViewById(R.id.pedigree_mother);
        pedigreefather = view.findViewById(R.id.pedigree_father);

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getSinglePig(pig_reg_id);
        } else{
            local_getSinglePig(pig_reg_id);
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
                            RequestParams requestParams = buildParams(pig_reg_id);
                            api_updateBreederPigProfile(requestParams);
                        }else{
                            local_updateBreederPigProfile();
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
           if(fragList.get(i) instanceof ViewBreederFragment)
               fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void local_updateBreederPigProfile() {
       boolean insertData = dbHelper.addBreederDetails(pig_reg_id,
                    birthday.getText().toString(),
                    sex.getText().toString(),
                    birthweight.getText().toString(),
                    weaningweight.getText().toString(),
                    littersizebornweight.getText().toString(),
                    ageatfirstmating.getText().toString(),
                    ageatweaning.getText().toString(),
                    pedigreemother.getText().toString(),
                    pedigreefather.getText().toString(),
                    "false");

        if(insertData) Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
    }

    private void api_updateBreederPigProfile(RequestParams params) {
        ApiHelper.updateBreederPigProfile("updateBreederPigProfile", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                birthday.setText(pig_bday);
                sex.setText(pig_sex);
                birthweight.setText(pig_birthweight);
                weaningweight.setText(pig_weaningweight);
                littersizebornweight.setText(pig_littersize);
                ageatfirstmating.setText(pig_ageatfirstmate);
                ageatweaning.setText(pig_ageatweaning);
                pedigreemother.setText(pig_pedigreemother);
                pedigreefather.setText(pig_pedigreefather);
                Log.d("API HANDLER Success", rawJsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("API HANDLER FAIL", errorResponse.toString());
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                pig_bday = jsonObject.get("pig_birthdate").toString();
                pig_sex = jsonObject.get("sex_ratio").toString();
                pig_birthweight = jsonObject.get("pig_birthweight").toString();
                pig_weaningweight = jsonObject.get("pig_weaningweight").toString();
                pig_littersize = jsonObject.get("litter_size_born_alive").toString();
                pig_ageatfirstmate = jsonObject.get("age_first_mating").toString();
                pig_ageatweaning = jsonObject.get("age_at_weaning").toString();
                pig_pedigreemother = jsonObject.get("pig_mother_earnotch").toString();
                pig_pedigreefather = jsonObject.get("pig_father_earnotch").toString();

                return null;
            }
        });
    }

    private RequestParams buildParams(String pig_reg_id) {
        RequestParams requestParams = new RequestParams();

        String editbirthday = birthday.getText().toString();
        String editsex = sex.getText().toString();
        String editbirthweight = birthweight.getText().toString();
        String editweaningweight = weaningweight.getText().toString();
        String editlittersizebornweight = littersizebornweight.getText().toString();
        String editageatfirstmating = ageatfirstmating.getText().toString();
        String editageatweaning = ageatweaning.getText().toString();
        String editpedigreemother = pedigreemother.getText().toString();
        String editpedigreefather = pedigreefather.getText().toString();

        requestParams.add("pig_registration_id", pig_reg_id);
        requestParams.add("pig_birthdate", editbirthday);
        requestParams.add("sex_ratio", editsex);
        requestParams.add("pig_birthweight", editbirthweight);
        requestParams.add("pig_weaningweight", editweaningweight);
        requestParams.add("litter_size_born_alive", editlittersizebornweight);
        requestParams.add("age_first_mating", editageatfirstmating);
        requestParams.add("age_at_weaning", editageatweaning);
        requestParams.add("pig_mother_earnotch", editpedigreemother);
        requestParams.add("pig_father_earnotch", editpedigreefather);

        return requestParams;
    }

    private void local_getSinglePig(String id){
        String birthDate = "", weaningDate = "";
        Cursor data = dbHelper.getSinglePig(id);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "3":   birthday.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "53":  sex.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "5":   birthweight.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "7":   weaningweight.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "50":  littersizebornweight.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "8":   pedigreemother.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "9":   pedigreefather.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }

    private void api_getSinglePig(String id) {
        RequestParams params = new RequestParams();
        params.add("pig_registration_id", id);

        ApiHelper.getSinglePig("api_getSinglePig", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                birthday.setText(setBlankIfNull(editBirthday));
                sex.setText(setBlankIfNull(editSex));
                birthweight.setText(setBlankIfNull(editBirthWeight));
                weaningweight.setText(setBlankIfNull(editWeaningWeight));
                littersizebornweight.setText(setBlankIfNull(editLitterSize));
                ageatfirstmating.setText(setBlankIfNull(editAgeMating));
                ageatweaning.setText(setBlankIfNull(editAgeWean));
                pedigreemother.setText(setBlankIfNull(editPedigreeMother));
                pedigreefather.setText(setBlankIfNull(editPedigreeFather));
                Log.d("ViewBreeder", "Successfully added data");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("ViewBreeder", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                editBirthday = jsonObject.get("pig_birthdate").toString();
                editSex = jsonObject.get("sex_ratio").toString();
                editBirthWeight = jsonObject.get("pig_birthweight").toString();
                editWeaningWeight = jsonObject.get("pig_weaningweight").toString();
                editLitterSize = jsonObject.get("litter_size_born_alive").toString();
                editAgeMating = jsonObject.get("age_first_mating").toString();
                editAgeWean = jsonObject.get("age_at_weaning").toString();
                editPedigreeMother = jsonObject.get("pig_mother_earnotch").toString();
                editPedigreeFather = jsonObject.get("pig_father_earnotch").toString();
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
        birthday.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Breeder Birthday");
                }
            }
        });

    }

    public interface ViewBreederListener{

    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewBreederListener = (ViewBreederListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("breederlistener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }


}
