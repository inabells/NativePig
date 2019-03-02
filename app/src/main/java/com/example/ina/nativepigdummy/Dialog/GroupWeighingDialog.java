package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

public class GroupWeighingDialog extends DialogFragment {

    private static final String TAG = "GroupWeighingDialog";
    private EditText datefarrowed;
    private EditText parity;
    private EditText nostillborn;
    private EditText nomummified;
    private EditText abnormalities;
    private EditText litterbirthweight;
    private EditText littersizealive;
    private EditText offspringearnotch;
    private Spinner sex;

    DatabaseHelper myDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_group_weighing,null);

        datefarrowed = view.findViewById(R.id.date_farrowed);
        parity = view.findViewById(R.id.parity);
        nostillborn = view.findViewById(R.id.number_stillborn);
        nomummified = view.findViewById(R.id.number_mummified);
        abnormalities = view.findViewById(R.id.abnormalities);
        litterbirthweight = view.findViewById(R.id.litter_birth_weight);
        offspringearnotch = view.findViewById(R.id.offspring_earnotch);
        sex = view.findViewById(R.id.offspring_sex);
        myDB = new DatabaseHelper(getActivity());

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editoffspringearnotch= offspringearnotch.getText().toString();
                        String editsex = sex.getSelectedItem().toString();
                        String editbirthweight= litterbirthweight.getText().toString();
                        String editweaningweight = "No data available";

//                        if(editoffspringearnotch.length() != 0 && editsex.length() != 0 && editbirthweight.length() != 0){
//                            addOffspringRecordsData(editoffspringearnotch,editsex, editbirthweight,editweaningweight);
//                            offspringearnotch.setText("");
//                            sex.setSelected(true);
//                            litterbirthweight.setText("");
//                        }else{
//                            Toast.makeText(getActivity(),"Please fill out all the fields!",Toast.LENGTH_LONG).show();
//                        }

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
        datefarrowed.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Group Weighing Dialog");
                }
            }
        });

    }

//    public void addOffspringRecordsData(String offspringid, String sex, String birthweight, String weaningweight){
//        boolean insertData = myDB.addOffspringRecordsData(offspringid,sex,birthweight,weaningweight);
//        if(insertData==true){
//            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
//        }
//    }
}
