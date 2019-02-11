package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

public class BreederWeightRecordsDialog extends DialogFragment {
    private static final String TAG = "BreederWeightRecordsDialog";
    private EditText weightat45;
    private EditText weightat60;
    private EditText weightat90;
    private EditText weightat150;
    private EditText weightat180;
    private EditText datecollected45;
    private EditText datecollected60;
    private EditText datecollected90;
    private EditText datecollected150;
    private EditText datecollected180;
    public ViewWeightRecordListener onViewWeightRecordListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_breeder_weight_records,null);

        weightat45 = view.findViewById(R.id.weight_45_days);
        weightat60 = view.findViewById(R.id.weight_60_days);
        weightat90 = view.findViewById(R.id.weight_90_days);
        weightat150 = view.findViewById(R.id.weight_150_days);
        weightat180 = view.findViewById(R.id.weight_180_days);
        datecollected45 = view.findViewById(R.id.date_collected_45_days);
        datecollected60 = view.findViewById(R.id.date_collected_60_days);
        datecollected90 = view.findViewById(R.id.date_collected_90_days);
        datecollected150 = view.findViewById(R.id.date_collected_150_days);
        datecollected180 = view.findViewById(R.id.date_collected_180_days);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editweight45 = weightat45.getText().toString();
                        String editweight60 = weightat60.getText().toString();
                        String editweight90 = weightat90.getText().toString();
                        String editweight150 = weightat150.getText().toString();
                        String editweight180 = weightat180.getText().toString();
                        String editdate45 = datecollected45.getText().toString();
                        String editdate60 = datecollected60.getText().toString();
                        String editdate90 = datecollected90.getText().toString();
                        String editdate150 = datecollected150.getText().toString();
                        String editdate180 = datecollected180.getText().toString();

                        if(!editweight45.equals("")) onViewWeightRecordListener.applyWeight45(editweight45);
                        if(!editweight60.equals("")) onViewWeightRecordListener.applyWeight60(editweight60);
                        if(!editweight90.equals("")) onViewWeightRecordListener.applyWeight90(editweight90);
                        if(!editweight150.equals("")) onViewWeightRecordListener.applyWeight150(editweight150);
                        if(!editweight180.equals("")) onViewWeightRecordListener.applyWeight180(editweight180);
                        if(!editdate45.equals("")) onViewWeightRecordListener.applyDate45(editdate45);
                        if(!editdate60.equals("")) onViewWeightRecordListener.applyDate60(editdate60);
                        if(!editdate90.equals("")) onViewWeightRecordListener.applyDate90(editdate90);
                        if(!editdate150.equals("")) onViewWeightRecordListener.applyDate150(editdate150);
                        if(!editdate180.equals("")) onViewWeightRecordListener.applyDate180(editdate180);
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
        datecollected45.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected60.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected90.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected150.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

        datecollected180.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Date Collected");
                }
            }
        });

    }

    public interface ViewWeightRecordListener{
        void applyWeight45(String weightat45);
        void applyWeight60(String weightat60);
        void applyWeight90(String weightat90);
        void applyWeight150(String weightat150);
        void applyWeight180(String weightat180);
        void applyDate45(String datecollected45);
        void applyDate60(String datecollected60);
        void applyDate90(String datecollected90);
        void applyDate150(String datecollected150);
        void applyDate180(String datecollected180);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewWeightRecordListener = (ViewWeightRecordListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("WeightRecordListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }
}