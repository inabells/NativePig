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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

public class MortalityDialog extends DialogFragment {

    private static final String TAG = "MortalityDialog";

    private EditText choosepig;
    private EditText dateofdeath;
    private EditText causeofdeath;
    DatabaseHelper myDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mortality,null);

        choosepig = view.findViewById(R.id.choose_pig);
        dateofdeath = view.findViewById(R.id.date_of_death);
        causeofdeath = view.findViewById(R.id.cause_of_death);
        myDB = new DatabaseHelper(getActivity());

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editchoosepig = choosepig.getText().toString();
                        String editdateofdeath = dateofdeath.getText().toString();
                        String editcauseofdeath= causeofdeath.getText().toString();
                        String editage = "0 months, 0 days";

                        if(editchoosepig.length() != 0 && editdateofdeath.length() != 0 && editcauseofdeath.length() != 0){
                            addMortalityData(editchoosepig,editdateofdeath, editcauseofdeath,editage);
                            choosepig.setText("");
                            dateofdeath.setText("");
                            causeofdeath.setText("");
                        }else{
                            Toast.makeText(getActivity(),"Please fill out all the fields!",Toast.LENGTH_LONG).show();
                        }

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
        dateofdeath.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Mortality Fragment");
                }
            }
        });

    }

    public void addMortalityData(String choosepig,String datedied, String causeofdeath, String age ){
        boolean insertData = myDB.addMortalityData(choosepig,datedied,causeofdeath,age);

        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
        }
    }

}
