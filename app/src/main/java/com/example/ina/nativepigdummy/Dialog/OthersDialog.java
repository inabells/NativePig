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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

public class OthersDialog extends DialogFragment {

    private static final String TAG = "MortalityDialog";

    private EditText choosepig;
    private EditText dateremoved;
    private Spinner choosereason;
    DatabaseHelper myDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_others,null);

        choosepig = view.findViewById(R.id.choose_pig);
        dateremoved = view.findViewById(R.id.date_removed);
        choosereason = view.findViewById(R.id.reason);
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
                        String editdateremoved = dateremoved.getText().toString();
                        String editreason= choosereason.getSelectedItem().toString();
                        String editage = "123";

                        if(editchoosepig.length() != 0 && editdateremoved.length() != 0 && editreason.length() != 0){
                            addOthersData(editchoosepig,editdateremoved, editreason,editage);
                            choosepig.setText("");
                            dateremoved.setText("");
                            choosereason.setSelected(true);
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
        dateremoved.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Others Fragment");
                }
            }
        });

    }

    public void addOthersData(String choosepig,String dateremoved, String reason, String age){
        boolean insertData = myDB.addOthersData(choosepig,dateremoved,reason,age);

        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
        }
    }
}


