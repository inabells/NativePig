package com.example.ina.nativepigdummy.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.R;

public class ViewGrowerDialog extends DialogFragment {
    private static final String TAG = "ViewGrowerDialog";
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
    private Button editButton;
    private ImageView exit_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_grower, container, false);
        editButton = view.findViewById(R.id.edit_button);
        exit_profile = view.findViewById(R.id.exit_dialog);

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

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "Weight Records has been updated", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });

        exit_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getDialog().dismiss();
            }
        });

        return view;
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
}
