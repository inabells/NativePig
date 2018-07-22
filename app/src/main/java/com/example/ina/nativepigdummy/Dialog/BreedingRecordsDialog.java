package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

public class BreedingRecordsDialog extends DialogFragment {

    private static final String TAG = "BreedingRecordsDialog";

    private EditText sowid;
    private EditText boarid;
    private EditText datebred;
    DatabaseHelper myDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_breeding_records,null);

        sowid = view.findViewById(R.id.sow_id);
        boarid = view.findViewById(R.id.boar_id);
        datebred = view.findViewById(R.id.date_bred);
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
                        String editsowid = sowid.getText().toString();
                        String editboarid = boarid.getText().toString();
                        String editdatebred = datebred.getText().toString();

                        if(editsowid.length() != 0 && editboarid.length() != 0 && editdatebred.length() != 0){
                            addBreedingRecordsData(editsowid, editboarid, editdatebred);
                            sowid.setText("");
                            boarid.setText("");
                            datebred.setText("");
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
        datebred.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"BreedingRecordsDialog");
                }
            }
        });

    }

    public void addBreedingRecordsData(String sowid, String boarid, String datebred){
        boolean insertData = myDB.addBreedingRecordsData(sowid, boarid, datebred);
        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
        }
    }

}
