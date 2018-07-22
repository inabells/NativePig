package com.example.ina.nativepigdummy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.MorphCharDialog;
import com.example.ina.nativepigdummy.R;

public class MorphCharFragment extends Fragment implements MorphCharDialog.ViewMorphCharListener{

    public MorphCharFragment() {
        // Required empty public constructor
    }

    private TextView TextViewDateCollected;
    private TextView TextViewEarLength;
    private TextView TextViewHeadLength;
    private TextView TextViewSnoutLength;
    private TextView TextViewBodyLength;
    private TextView TextViewHeartGirth;
    private TextView TextViewPelvicWidth;
    private TextView TextViewTailLength;
    private TextView TextViewHeightAtWithers;
    private ImageView edit_profile;
    private TextView registration_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_morph_char, container, false);

        edit_profile = view.findViewById(R.id.edit_morph_char);
        TextViewDateCollected = (TextView) view.findViewById(R.id.textView_dateCollected);
        TextViewEarLength = (TextView) view.findViewById(R.id.textView_earLength);
        TextViewHeadLength = (TextView) view.findViewById(R.id.textView_headLength);
        TextViewSnoutLength = (TextView) view.findViewById(R.id.textView_snoutLength);
        TextViewBodyLength = (TextView) view.findViewById(R.id.textView_bodyLength);
        TextViewHeartGirth = (TextView) view.findViewById(R.id.textView_heartGirth);
        TextViewPelvicWidth = (TextView) view.findViewById(R.id.textView_pelvicWidth);
        TextViewTailLength = (TextView) view.findViewById(R.id.textView_tailLength);
        TextViewHeightAtWithers = (TextView) view.findViewById(R.id.textView_heightWithers);

        registration_id = view.findViewById(R.id.registration_id);

        String tempholder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(tempholder);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorphCharDialog dialog = new MorphCharDialog();
                dialog.setTargetFragment(MorphCharFragment.this,1);
                dialog.show(getFragmentManager(),"MorphCharDialog");
            }
        });

        return view;
    }

    @Override public void applyDateCollected(String datecollected){ TextViewDateCollected.setText(datecollected); }

    @Override public void applyEarLength(String earlength){ TextViewEarLength.setText(earlength); }

    @Override public void applyHeadLength(String headlength){ TextViewHeadLength.setText(headlength); }

    @Override public void applySnoutLength(String snoutlength){ TextViewSnoutLength.setText(snoutlength); }

    @Override public void applyBodyLength(String bodylength){ TextViewBodyLength.setText(bodylength); }

    @Override public void applyHeartGirth(String heartgirth){ TextViewHeartGirth.setText(heartgirth); }

    @Override public void applyPelvicWidth(String pelvicwidth){ TextViewPelvicWidth.setText(pelvicwidth); }

    @Override public void applyTailLength(String taillength){ TextViewTailLength.setText(taillength); }

    @Override public void applyHeightAtWithers(String heightatwithers){ TextViewHeightAtWithers.setText(heightatwithers); }




}
