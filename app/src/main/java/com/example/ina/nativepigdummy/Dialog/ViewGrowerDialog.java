package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
import android.database.Cursor;
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

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

@SuppressLint("ValidFragment")
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
    private String reg_id;
    private ImageView exit_profile;
    DatabaseHelper dbHelper;

    public ViewGrowerDialog(String reg_id){
        this.reg_id = reg_id;
    }

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
        dbHelper = new DatabaseHelper(getActivity());

        local_getWeightProfile(reg_id);

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

    private void local_getWeightProfile(String reg_id) {
        Cursor data = dbHelper.getWeightRecords(reg_id);
        if (data.moveToFirst()) {
            weightat45.setText(setBlankIfNull(data.getString(data.getColumnIndex("weight_at_45"))));
            weightat60.setText(setBlankIfNull(data.getString(data.getColumnIndex("weight_at_60"))));
            weightat90.setText(setBlankIfNull(data.getString(data.getColumnIndex("weight_at_90"))));
            weightat150.setText(setBlankIfNull(data.getString(data.getColumnIndex("weight_at_150"))));
            weightat180.setText(setBlankIfNull(data.getString(data.getColumnIndex("weight_at_180"))));
            datecollected45.setText(setBlankIfNull(data.getString(data.getColumnIndex("date_collected_at_45"))));
            datecollected60.setText(setBlankIfNull(data.getString(data.getColumnIndex("date_collected_at_60"))));
            datecollected90.setText(setBlankIfNull(data.getString(data.getColumnIndex("date_collected_at_90"))));
            datecollected150.setText(setBlankIfNull(data.getString(data.getColumnIndex("date_collected_at_150"))));
            datecollected180.setText(setBlankIfNull(data.getString(data.getColumnIndex("date_collected_at_180"))));
        }
    }

    private String setBlankIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "" : text);
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
