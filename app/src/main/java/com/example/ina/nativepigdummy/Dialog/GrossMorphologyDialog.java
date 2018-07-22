package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ina.nativepigdummy.R;

public class GrossMorphologyDialog extends DialogFragment {
    private static final String TAG = "GrossMorphologyDialog";
    private EditText datecollected;
    private RadioButton hairtypecurly;
    private RadioButton hairtypestraight;
    private RadioButton hairlengthshort;
    private RadioButton hairlengthlong;
    private RadioButton coatcolorblack;
    private RadioButton coatcolorothers;
    private RadioButton colorpatternplain;
    private RadioButton colorpatternsocks;
    private RadioButton headshapeconcave;
    private RadioButton headshapestraight;
    private RadioButton skintypesmooth;
    private RadioButton skintypewrinkled;
    private RadioButton eartypedrooping;
    private RadioButton eartypesemilop;
    private RadioButton eartypeerect;
    private RadioButton tailtypecurly;
    private RadioButton tailtypestraight;
    private RadioButton backlineswayback;
    private RadioButton backlinestraight;
    private EditText othermarks;
    public ViewGrossMorphListener onViewGrossMorphListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_gross_morphology,null);

        datecollected = view.findViewById(R.id.date_collected_gross);
        hairtypecurly = view.findViewById(R.id.hair_type_curly);
        hairtypestraight = view.findViewById(R.id.hair_type_straight);
        hairlengthshort = view.findViewById(R.id.hair_length_short);
        hairlengthlong = view.findViewById(R.id.hair_length_long);
        coatcolorblack = view.findViewById(R.id.coat_color_black);
        coatcolorothers = view.findViewById(R.id.coat_color_others);
        colorpatternplain = view.findViewById(R.id.color_pattern_plain);
        colorpatternsocks = view.findViewById(R.id.color_pattern_socks);
        headshapeconcave = view.findViewById(R.id.head_shape_concave);
        headshapestraight = view.findViewById(R.id.head_shape_straight);
        skintypesmooth = view.findViewById(R.id.skin_type_smooth);
        skintypewrinkled = view.findViewById(R.id.skin_type_wrinkled);
        eartypedrooping = view.findViewById(R.id.ear_type_drooping);
        eartypesemilop = view.findViewById(R.id.ear_type_semi_lop);
        eartypeerect = view.findViewById(R.id.ear_type_erect);
        tailtypecurly = view.findViewById(R.id.tail_type_curly);
        tailtypestraight = view.findViewById(R.id.tail_type_straight);
        backlineswayback = view.findViewById(R.id.backline_swayback);
        backlinestraight = view.findViewById(R.id.backline_straight);
        othermarks = view.findViewById(R.id.other_marks);
;

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
                        String editdothermarks = othermarks.getText().toString();

                        if(!editdatecollected.equals("")) onViewGrossMorphListener.applyDateCollected(editdatecollected);
                        if(!editdothermarks.equals("")) onViewGrossMorphListener.applyOtherMarks(editdothermarks);

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
                    dialog.show(getActivity().getFragmentManager(),"Gross Morphology Dialog");
                }
            }
        });

    }

    public interface ViewGrossMorphListener{
        void applyDateCollected(String datecollected);
        void applyOtherMarks(String othermarks);

    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewGrossMorphListener = (ViewGrossMorphListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("MorphCharListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }
}
