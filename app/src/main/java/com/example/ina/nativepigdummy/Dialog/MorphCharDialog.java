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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ina.nativepigdummy.R;

public class MorphCharDialog extends DialogFragment {

    private static final String TAG = "MorphometricCharacteristicsDialog";
    private EditText datecollected;
    private EditText earlength;
    private EditText headlength;
    private EditText snoutlength;
    private EditText bodylength;
    private EditText heartgirth;
    private EditText pelvicwidth;
    private EditText taillength;
    private EditText heightatwithers;
    private SeekBar numberofnormalteats;
    public ViewMorphCharListener onViewMorphCharListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_morph_char,null);

        datecollected = view.findViewById(R.id.date_collected);
        earlength = view.findViewById(R.id.ear_length);
        headlength = view.findViewById(R.id.head_length);
        snoutlength = view.findViewById(R.id.snout_length);
        bodylength = view.findViewById(R.id.body_length);
        heartgirth = view.findViewById(R.id.heart_girth);
        pelvicwidth = view.findViewById(R.id.pelvic_width);
        taillength = view.findViewById(R.id.tail_length);
        heightatwithers = view.findViewById(R.id.height_at_withers);
        numberofnormalteats = view.findViewById(R.id.number_of_normal_teats);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editdatecollected = datecollected.getText().toString();
                        String editearlength = earlength.getText().toString();
                        String editheadlength = headlength.getText().toString();
                        String editsnoutlength = snoutlength.getText().toString();
                        String editbodylength = bodylength.getText().toString();
                        String editheartgirth = heartgirth.getText().toString();
                        String editpelvicwidth = pelvicwidth.getText().toString();
                        String edittaillength = taillength.getText().toString();
                        String editheightatwithers = heightatwithers.getText().toString();

                        if(!editdatecollected.equals("")) onViewMorphCharListener.applyDateCollected(editdatecollected);
                        if(!editearlength.equals("")) onViewMorphCharListener.applyEarLength(editearlength);
                        if(!editheadlength.equals("")) onViewMorphCharListener.applyHeadLength(editheadlength);
                        if(!editsnoutlength.equals("")) onViewMorphCharListener.applySnoutLength(editsnoutlength);
                        if(!editbodylength.equals("")) onViewMorphCharListener.applyBodyLength(editbodylength);
                        if(!editheartgirth.equals("")) onViewMorphCharListener.applyHeartGirth(editheartgirth);
                        if(!editpelvicwidth.equals("")) onViewMorphCharListener.applyPelvicWidth(editpelvicwidth);
                        if(!edittaillength.equals("")) onViewMorphCharListener.applyTailLength(edittaillength);
                        if(!editheightatwithers.equals("")) onViewMorphCharListener.applyHeightAtWithers(editheightatwithers);
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
        datecollected.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Morph Char Dialog");
                }
            }
        });

    }

    public interface ViewMorphCharListener{
        void applyDateCollected(String datecollected);
        void applyEarLength(String earlength);
        void applyHeadLength(String headlength);
        void applySnoutLength(String snoutlength);
        void applyBodyLength(String bodylength);
        void applyHeartGirth(String heartgirth);
        void applyPelvicWidth(String pelvicwidth);
        void applyTailLength(String taillength);
        void applyHeightAtWithers(String heightatwithers);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewMorphCharListener = (ViewMorphCharListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("MorphCharListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}
