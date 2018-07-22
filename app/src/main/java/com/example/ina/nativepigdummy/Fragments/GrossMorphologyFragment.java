package com.example.ina.nativepigdummy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.GrossMorphologyDialog;
import com.example.ina.nativepigdummy.R;


public class GrossMorphologyFragment extends Fragment implements GrossMorphologyDialog.ViewGrossMorphListener{

    public GrossMorphologyFragment() {
        // Required empty public constructor
    }

    private TextView TextViewDateCollected;
    private TextView TextViewOtherMarks;
    private TextView registration_id;
    private ImageView edit_profile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gross_morphology, container, false);
        edit_profile = view.findViewById(R.id.edit_gross_morphology);

        registration_id = view.findViewById(R.id.registration_id);

        String tempholder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(tempholder);

        TextViewDateCollected = (TextView) view.findViewById(R.id.textView_dateCollected);
        TextViewOtherMarks = (TextView) view.findViewById(R.id.textView_otherMarks);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GrossMorphologyDialog dialog = new GrossMorphologyDialog();
                dialog.setTargetFragment(GrossMorphologyFragment.this, 1);
                dialog.show(getFragmentManager(),"GrossMorphologyDialog");
            }
        });

        return view;
    }

    @Override public void applyDateCollected(String datecollected){ TextViewDateCollected.setText(datecollected); }

    @Override public void applyOtherMarks(String othermarks){ TextViewOtherMarks.setText(othermarks); }

}
