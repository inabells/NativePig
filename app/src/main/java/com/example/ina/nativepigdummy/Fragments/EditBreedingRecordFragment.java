package com.example.ina.nativepigdummy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Activities.SowLitterActivity;
import com.example.ina.nativepigdummy.Dialog.ExpectedDateofFarrowingDialog;
import com.example.ina.nativepigdummy.Dialog.StatusDialog;
import com.example.ina.nativepigdummy.R;


public class EditBreedingRecordFragment extends Fragment implements ExpectedDateofFarrowingDialog.ViewFarrowListener, StatusDialog.ViewStatusListener{

    public EditBreedingRecordFragment() {
        // Required empty public constructor
    }

    private TextView TextViewFarrow;
    private TextView TextViewStatus;
    private ImageView edit_breeding_record;
    private ImageView view_sow_litter_record;
    private ImageView status;
    private ImageView farrowing;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_breeding_record, container, false);

        TextViewFarrow = view.findViewById(R.id.textViewFarrowing);
        TextViewStatus = view.findViewById(R.id.textViewStatus);
        farrowing = view.findViewById(R.id.edit_farrow);
        status = view.findViewById(R.id.edit_status);
        view_sow_litter_record = view.findViewById(R.id.view_sow_litter_record);

        farrowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpectedDateofFarrowingDialog dialog = new ExpectedDateofFarrowingDialog();
                dialog.setTargetFragment(EditBreedingRecordFragment.this, 1);
                dialog.show(getFragmentManager(), "ExpectedDateofFarrowingDialog");
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusDialog dialog = new StatusDialog();
                dialog.setTargetFragment(EditBreedingRecordFragment.this, 1);
                dialog.show(getFragmentManager(), "StatusDialog");
            }
        });

        view_sow_litter_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sow_litter = new Intent(getActivity(), SowLitterActivity.class);
                startActivity(intent_sow_litter);
            }
        });

        return view;
    }

    @Override public void applyText(String dateoffarrowing){
        TextViewFarrow.setText(dateoffarrowing);
    }

    @Override public void applyStatus(String status){
        TextViewStatus.setText(status);
    }

}
