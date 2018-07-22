package com.example.ina.nativepigdummy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.ViewBreederDialog;
import com.example.ina.nativepigdummy.Dialog.ViewGrowerDialog;
import com.example.ina.nativepigdummy.R;


public class ViewGrowerFragment extends Fragment {

    public ViewGrowerFragment() {
        // Required empty public constructor
    }

    private ImageView edit_profile;
    private TextView registration_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_grower, container, false);
        edit_profile = view.findViewById(R.id.edit_view_grower);

        registration_id = view.findViewById(R.id.registration_id);

        String tempholder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(tempholder);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGrowerDialog dialog = new ViewGrowerDialog();
                dialog.show(getFragmentManager(),"ViewGrowerDialog");
            }
        });

        return view;
    }

}
