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

public class ExpectedDateofFarrowingDialog extends DialogFragment {

    private static final String TAG = "ExpectedDateofFarrowingDialog";

    private EditText dateoffarrowing;
    public ViewFarrowListener onViewFarrowListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_expected_dateof_farrowing,null);

        dateoffarrowing = view.findViewById(R.id.date_of_farrowing);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editfarrowing = dateoffarrowing.getText().toString();

                        if(!editfarrowing.equals("")) onViewFarrowListener.applyText(editfarrowing);
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
        dateoffarrowing.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Expected Date of Farrowing");
                }
            }
        });

    }



    public interface ViewFarrowListener{
        void applyText(String dateoffarrowing);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewFarrowListener = (ViewFarrowListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("FarrowListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}
