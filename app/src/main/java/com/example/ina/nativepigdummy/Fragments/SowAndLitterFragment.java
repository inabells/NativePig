package com.example.ina.nativepigdummy.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MorphCharDialog;
import com.example.ina.nativepigdummy.Dialog.SowAndLitterDialog;
import com.example.ina.nativepigdummy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SowAndLitterFragment extends Fragment implements SowAndLitterDialog.ViewSowLitterListener{

    public SowAndLitterFragment() {
        // Required empty public constructor
    }

    private TextView textViewdateFarrowed, textViewSowUsed, textViewBoarUsed, textViewDateBred;
    private TextView textViewParity, textViewMales, textViewFemales, textViewSexRatio, textViewLSBA, textViewNumWeaned;
    private TextView textViewNoStillBorn, textViewAveBirthWeight, textViewAveWeanWeight;
    private TextView textViewNoMummified;
    private TextView textViewAbnormalities;
    private Button edit_profile;
    DatabaseHelper dbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @ Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sow_and_litter, container, false);


        dbHelper = new DatabaseHelper(getActivity());
        edit_profile = view.findViewById(R.id.edit_sow_litter);
        textViewdateFarrowed = view.findViewById(R.id.textView_dateFarrowed);
        textViewParity = view.findViewById(R.id.textView_Parity);
        textViewNoStillBorn = view.findViewById(R.id.textView_stillBorn);
        textViewNoMummified = view.findViewById(R.id.textView_mummified);
        textViewAbnormalities = view.findViewById(R.id.textView_abnormalities);
        textViewSowUsed = view.findViewById(R.id.TextViewSowUsed);
        textViewBoarUsed = view.findViewById(R.id.TextViewBoarUsed);
        textViewDateBred = view.findViewById(R.id.TextViewDateBred);
        textViewMales = view.findViewById(R.id.TextViewMales);
        textViewFemales = view.findViewById(R.id.TextViewFemales);
        textViewLSBA = view.findViewById(R.id.TextViewLSBA);
        textViewSexRatio = view.findViewById(R.id.TextViewSexRatio);
        textViewAveBirthWeight = view.findViewById(R.id.TextViewAveBirthWeight);
        textViewAveWeanWeight = view.findViewById(R.id.TextViewAveWeanWeight);
        textViewNumWeaned = view.findViewById(R.id.TextViewNumberWeaned);

        textViewSowUsed.setText(getActivity().getIntent().getStringExtra("sow_idSLR"));
        textViewBoarUsed.setText(getActivity().getIntent().getStringExtra("boar_idSLR"));
        textViewDateBred.setText(getActivity().getIntent().getStringExtra("date_bredSLR"));
        textViewdateFarrowed.setText(getActivity().getIntent().getStringExtra("date_farrowSLR"));

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getSowLitterValues();
        } else{
            local_getSowLitterValues();
        }


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

    private void local_getSowLitterValues() {
        String sowId = dbHelper.getAnimalId(textViewSowUsed.getText().toString());
        String boarId = dbHelper.getAnimalId(textViewBoarUsed.getText().toString());

        Cursor data = dbHelper.getSowLitterRecords(sowId, boarId);
        while(data.moveToNext()){
            switch (data.getString(data.getColumnIndex("property_id"))){
                case "51":  textViewMales.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "52":  textViewFemales.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "53":  textViewSexRatio.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "56":  textViewAveBirthWeight.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "58":  textViewAveWeanWeight.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "57":  textViewNumWeaned.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "45":  textViewNoStillBorn.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "46":  textViewNoMummified.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "47":  textViewAbnormalities.setText(data.getString(data.getColumnIndex("value")));
                            break;
                case "48":  textViewParity.setText(data.getString(data.getColumnIndex("value")));
                            break;
            }
        }


    }

    private void api_getSowLitterValues() {
        Log.d("TAG", "Log");
    }


    @Override public void applyDateFarrowed(String datefarrowed){ textViewdateFarrowed.setText(datefarrowed); }

    @Override public void applyParity(String parity){ textViewParity.setText(parity); }

    @Override public void applyNoMummified(String nomummified){ textViewNoMummified.setText(nomummified); }

    @Override public void applyNoStillBorn(String nostillborn){ textViewNoStillBorn.setText(nostillborn); }

    @Override public void applyAbnormalities(String abnormalities){ textViewAbnormalities.setText(abnormalities); }

}
