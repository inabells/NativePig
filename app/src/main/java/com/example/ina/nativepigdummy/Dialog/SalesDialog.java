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

public class SalesDialog extends DialogFragment {

    private static final String TAG = "SalesDialog";

    private EditText choosepig;
    private EditText datesold;
    private EditText weightsold;
    DatabaseHelper myDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sales,null);

        choosepig = view.findViewById(R.id.choose_pig);
        datesold = view.findViewById(R.id.date_sold);
        weightsold = view.findViewById(R.id.weight_sold);
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
                        String editdatesold = datesold.getText().toString();
                        String editweightsold= weightsold.getText().toString();
                        String editage = "123";

                        if(editchoosepig.length() != 0 && editdatesold.length() != 0 && editweightsold.length() != 0){
                            addSalesData(editchoosepig,editdatesold, editweightsold,editage);
                            choosepig.setText("");
                            datesold.setText("");
                            weightsold.setText("");
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
        datesold.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Sales Fragment");
                }
            }
        });

    }

    public void addSalesData(String choosepig, String datesold, String weightsold, String age){
        boolean insertData = myDB.addSalesData(choosepig,datesold,weightsold,age);
        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
        }
    }
}
