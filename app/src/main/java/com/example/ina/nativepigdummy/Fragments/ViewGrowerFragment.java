package com.example.ina.nativepigdummy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.AddAsBreederPromptDialog;
import com.example.ina.nativepigdummy.Dialog.ViewGrowerDialog;
import com.example.ina.nativepigdummy.R;


public class ViewGrowerFragment extends Fragment {

    public ViewGrowerFragment() {
        // Required empty public constructor
    }

    private ImageView edit_profile;
    private TextView registration_id;
    private Switch add_candidate, add_breeder;
    private String pigRegIdHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_grower, container, false);
        edit_profile = view.findViewById(R.id.edit_view_grower);

        registration_id = view.findViewById(R.id.registration_id);
        add_candidate = view.findViewById(R.id.add_as_candidate);
        add_breeder = view.findViewById(R.id.add_as_breeder);

        pigRegIdHolder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(pigRegIdHolder);

        add_breeder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    AddAsBreederPromptDialog(pigRegIdHolder);
                    add_breeder.setChecked(false);
                }
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGrowerDialog growerDialog = new ViewGrowerDialog(pigRegIdHolder);
                growerDialog.setTargetFragment(ViewGrowerFragment.this, 1);
                growerDialog.show(getFragmentManager(),"ViewGrowerDialog");
            }
        });

        return view;
    }

    public void AddAsBreederPromptDialog(String regId){
        AddAsBreederPromptDialog dialog = new AddAsBreederPromptDialog(regId);
        dialog.setTargetFragment(ViewGrowerFragment.this,1);
        dialog.show(getFragmentManager(),"AddAsBreederPromptDialog");
    }
}
