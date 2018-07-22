package com.example.ina.nativepigdummy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.BreederWeightRecordsDialog;
import com.example.ina.nativepigdummy.Dialog.GrossMorphologyDialog;
import com.example.ina.nativepigdummy.R;


public class BreederWeightRecordsFragment extends Fragment implements BreederWeightRecordsDialog.ViewWeightRecordListener{

    public BreederWeightRecordsFragment() {
        // Required empty public constructor
    }
    private TextView TextViewWeight45;
    private TextView TextViewWeight60;
    private TextView TextViewWeight90;
    private TextView TextViewWeight150;
    private TextView TextViewWeight180;
    private TextView TextViewDate45;
    private TextView TextViewDate60;
    private TextView TextViewDate90;
    private TextView TextViewDate150;
    private TextView TextViewDate180;
    private ImageView edit_profile;
    private TextView registration_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breeder_weight_records, container, false);
        edit_profile = view.findViewById(R.id.edit_breeder_weight_records);
        registration_id = view.findViewById(R.id.registration_id);

        String tempholder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(tempholder);

        TextViewWeight45 = (TextView) view.findViewById(R.id.textView_45days);
        TextViewWeight60 = (TextView) view.findViewById(R.id.textView_60days);
        TextViewWeight90 = (TextView) view.findViewById(R.id.textView_90days);
        TextViewWeight150 = (TextView) view.findViewById(R.id.textView_150days);
        TextViewWeight180 = (TextView) view.findViewById(R.id.textView_180days);
        TextViewDate45 = (TextView) view.findViewById(R.id.textView_45date);
        TextViewDate60 = (TextView) view.findViewById(R.id.textView_60date);
        TextViewDate90 = (TextView) view.findViewById(R.id.textView_90date);
        TextViewDate150 = (TextView) view.findViewById(R.id.textView_150date);
        TextViewDate180 = (TextView) view.findViewById(R.id.textView_180date);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreederWeightRecordsDialog dialog = new BreederWeightRecordsDialog();
                dialog.setTargetFragment(BreederWeightRecordsFragment.this, 1);
                dialog.show(getFragmentManager(),"BreederWeightRecordsDialog");
            }
        });

        return view;
    }

    @Override public void applyWeight45(String weightat45){ TextViewWeight45.setText(weightat45); }

    @Override public void applyWeight60(String weightat60){ TextViewWeight60.setText(weightat60); }

    @Override public void applyWeight90(String weightat90){ TextViewWeight90.setText(weightat90); }

    @Override public void applyWeight150(String weightat150){ TextViewWeight150.setText(weightat150); }

    @Override public void applyWeight180(String weightat180){ TextViewWeight180.setText(weightat180); }

    @Override public void applyDate45(String datecollected45){ TextViewDate45.setText(datecollected45); }

    @Override public void applyDate60(String datecollected60){ TextViewDate60.setText(datecollected60); }

    @Override public void applyDate90(String datecollected90){ TextViewDate90.setText(datecollected90); }

    @Override public void applyDate150(String datecollected150){ TextViewDate150.setText(datecollected150); }

    @Override public void applyDate180(String datecollected180){ TextViewDate180.setText(datecollected180); }



}
