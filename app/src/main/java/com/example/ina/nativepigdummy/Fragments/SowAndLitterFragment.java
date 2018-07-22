package com.example.ina.nativepigdummy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.MorphCharDialog;
import com.example.ina.nativepigdummy.Dialog.SowAndLitterDialog;
import com.example.ina.nativepigdummy.R;

public class SowAndLitterFragment extends Fragment implements SowAndLitterDialog.ViewSowLitterListener{

    public SowAndLitterFragment() {
        // Required empty public constructor
    }

    private TextView textViewdateFarrowed;
    private TextView textViewParity;
    private TextView textViewNoStillBorn;
    private TextView textViewNoMummified;
    private TextView textViewAbnormalities;
    private Button edit_profile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sow_and_litter, container, false);

        edit_profile = view.findViewById(R.id.edit_sow_litter);
        textViewdateFarrowed = view.findViewById(R.id.textView_dateFarrowed);
        textViewParity = view.findViewById(R.id.textView_Parity);
        textViewNoStillBorn = view.findViewById(R.id.textView_stillBorn);
        textViewNoMummified = view.findViewById(R.id.textView_mummified);
        textViewAbnormalities = view.findViewById(R.id.textView_abnormalities);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SowAndLitterDialog dialog = new SowAndLitterDialog();
                dialog.setTargetFragment(SowAndLitterFragment.this, 1);
                dialog.show(getFragmentManager(),"SowAndLitterDialog");
            }
        });
        return view;
    }

    @Override public void applyDateFarrowed(String datefarrowed){ textViewdateFarrowed.setText(datefarrowed); }

    @Override public void applyParity(String parity){ textViewParity.setText(parity); }

    @Override public void applyNoMummified(String nomummified){ textViewNoMummified.setText(nomummified); }

    @Override public void applyNoStillBorn(String nostillborn){ textViewNoStillBorn.setText(nostillborn); }

    @Override public void applyAbnormalities(String abnormalities){ textViewAbnormalities.setText(abnormalities); }

}
