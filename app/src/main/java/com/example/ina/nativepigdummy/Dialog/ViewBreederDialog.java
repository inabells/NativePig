package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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

    public ViewBreederDialog(String pig_reg_id){
        this.pig_reg_id = pig_reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_breeder,null);

        birthday = view.findViewById(R.id.birthday);
        sex = view.findViewById(R.id.sex);
        birthweight = view.findViewById(R.id.birth_weight);
        weaningweight = view.findViewById(R.id.weaning_weight);
        littersizebornweight = view.findViewById(R.id.litter_size_born_alive);
        ageatfirstmating = view.findViewById(R.id.age_at_first_mating);
        ageatweaning = view.findViewById(R.id.age_at_weaning);
        pedigreemother = view.findViewById(R.id.pedigree_mother);
        pedigreefather = view.findViewById(R.id.pedigree_father);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestParams requestParams = buildParams(pig_reg_id);
                        updateBreederPigProfile(requestParams);

//                        if(!editbirthday.equals("")) onViewBreederListener.applyBirthdayText(editbirthday);
//                        if(!editsex.equals("")) onViewBreederListener.applySexText(editsex);
//                        if(!editbirthweight.equals("")) onViewBreederListener.applyBirthWeightText(editbirthweight);
//                        if(!editweaningweight.equals("")) onViewBreederListener.applyWeaningWeight(editweaningweight);
//                        if(!editlittersizebornweight.equals("")) onViewBreederListener.applyLitterSize(editlittersizebornweight);
//                        if(!editageatfirstmating.equals("")) onViewBreederListener.applyAgeMating(editageatfirstmating);
//                        if(!editageatweaning.equals("")) onViewBreederListener.applyAgeWeaning(editageatweaning);
//                        if(!editpedigreemother.equals("")) onViewBreederListener.applyMother(editpedigreemother);
//                        if(!editpedigreefather.equals("")) onViewBreederListener.applyFather(editpedigreefather);
                    }

                });

        return builder.create();
    }

    private void updateBreederPigProfile(RequestParams params) {
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
//                Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
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
        void applyBirthdayText(String birthday);
        void applySexText(String sex);
        void applyBirthWeightText(String birthweight);
        void applyWeaningWeight(String weaningweight);
        void applyLitterSize(String littersizebornweight);
        void applyAgeMating(String ageatfirstmating);
        void applyAgeWeaning(String ageatweaning);
        void applyMother(String pedigreemother);
        void applyFather(String pedigreefather);
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
