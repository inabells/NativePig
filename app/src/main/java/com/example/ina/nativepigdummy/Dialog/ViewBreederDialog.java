package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.R;

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
                        String editbirthday = birthday.getText().toString();
                        String editsex = sex.getText().toString();
                        String editbirthweight = birthweight.getText().toString();
                        String editweaningweight = weaningweight.getText().toString();
                        String editlittersizebornweight = littersizebornweight.getText().toString();
                        String editageatfirstmating = ageatfirstmating.getText().toString();
                        String editageatweaning = ageatweaning.getText().toString();
                        String editpedigreemother = pedigreemother.getText().toString();
                        String editpedigreefather = pedigreefather.getText().toString();

                        if(!editbirthday.equals("")) onViewBreederListener.applyBirthdayText(editbirthday);
                        if(!editsex.equals("")) onViewBreederListener.applySexText(editsex);
                        if(!editbirthweight.equals("")) onViewBreederListener.applyBirthWeightText(editbirthweight);
                        if(!editweaningweight.equals("")) onViewBreederListener.applyWeaningWeight(editweaningweight);
                        if(!editlittersizebornweight.equals("")) onViewBreederListener.applyLitterSize(editlittersizebornweight);
                        if(!editageatfirstmating.equals("")) onViewBreederListener.applyAgeMating(editageatfirstmating);
                        if(!editageatweaning.equals("")) onViewBreederListener.applyAgeWeaning(editageatweaning);
                        if(!editpedigreemother.equals("")) onViewBreederListener.applyMother(editpedigreemother);
                        if(!editpedigreefather.equals("")) onViewBreederListener.applyFather(editpedigreefather);
                    }

                });

        return builder.create();
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
