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

import com.example.ina.nativepigdummy.R;

public class SowAndLitterDialog extends DialogFragment {
    private static final String TAG = "SowAndLitterDialog";

    private EditText datefarrowed;
    private EditText parity;
    private EditText nostillborn;
    private EditText nomummified;
    private EditText abnormalities;
    public ViewSowLitterListener onViewSowLitterListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sow_and_litter, null);

        datefarrowed = view.findViewById(R.id.date_farrowed);
        parity = view.findViewById(R.id.parity);
        nostillborn = view.findViewById(R.id.number_stillborn);
        nomummified = view.findViewById(R.id.number_mummified);
        abnormalities = view.findViewById(R.id.abnormalities);

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editdatefarrowed = datefarrowed.getText().toString();
                        String editparity = parity.getText().toString();
                        String editnostillborn = nostillborn.getText().toString();
                        String editnomummified = nomummified.getText().toString();
                        String editabnormalities = abnormalities.getText().toString();

                        if(!editdatefarrowed.equals("")) onViewSowLitterListener.applyDateFarrowed(editdatefarrowed);
                        if(!editparity.equals("")) onViewSowLitterListener.applyParity(editparity);
                        if(!editnostillborn.equals("")) onViewSowLitterListener.applyNoStillBorn(editnostillborn);
                        if(!editnomummified.equals("")) onViewSowLitterListener.applyNoMummified(editnomummified);
                        if(!editabnormalities.equals("")) onViewSowLitterListener.applyAbnormalities(editabnormalities);

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
                    dialog.show(getActivity().getFragmentManager(),"SowAndLitterDialog");
                }
            }
        });

    }

    public interface ViewSowLitterListener{
        void applyDateFarrowed(String datefarrowed);
        void applyParity(String parity);
        void applyNoMummified(String nomummified);
        void applyNoStillBorn(String nostillborn);
        void applyAbnormalities(String abnormalities);
    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewSowLitterListener = (ViewSowLitterListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("SowLitterListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}
