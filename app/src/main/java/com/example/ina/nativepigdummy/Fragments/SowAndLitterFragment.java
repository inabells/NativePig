package com.example.ina.nativepigdummy.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.SowAndLitterDialog;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class SowAndLitterFragment extends Fragment implements SowAndLitterDialog.ViewSowLitterListener{

    public SowAndLitterFragment() {
        // Required empty public constructor
    }

    private TextView textViewDateWeaned, textViewdateFarrowed, textViewSowUsed, textViewBoarUsed, textViewDateBred, textViewGestation, textViewLactation;
    private TextView textViewParity, textViewMales, textViewFemales, textViewSexRatio, textViewLSBA, textViewNumWeaned;
    private TextView textViewNoStillBorn, textViewAveBirthWeight, textViewAveWeanWeight, textViewTLSB;
    private TextView textViewNoMummified;
    private TextView textViewAbnormalities;
    private String editDateWeaned, groupingId, editLSBA, editTLSB, editMales, editFemales, editSexRatio, editAveBirthWeight, editAveWeanWeight, editNumWeaned, editNoStillBorn, editMummified, editAbnormalities, editParity, sow_id, boar_id, date_bred, date_farrowed;
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
        textViewTLSB = view.findViewById(R.id.TextViewLSB);
        textViewGestation = view.findViewById(R.id.TextViewGestationPeriod);
        textViewLactation = view.findViewById(R.id.TextViewLactationPeriod);
        textViewDateWeaned = view.findViewById(R.id.TextViewDateWeaned);

        sow_id = getActivity().getIntent().getStringExtra("sow_idSLR");
        boar_id = getActivity().getIntent().getStringExtra("boar_idSLR");
        date_bred = getActivity().getIntent().getStringExtra("date_bredSLR");
        date_farrowed = getActivity().getIntent().getStringExtra("date_farrowSLR");

        textViewGestation.setText(getNumberOfDaysBetween(date_bred, date_farrowed));

        textViewSowUsed.setText(getActivity().getIntent().getStringExtra("sow_idSLR"));
        textViewBoarUsed.setText(getActivity().getIntent().getStringExtra("boar_idSLR"));
        textViewDateBred.setText(getActivity().getIntent().getStringExtra("date_bredSLR"));
        textViewdateFarrowed.setText(getActivity().getIntent().getStringExtra("date_farrowSLR"));


        RequestParams requestParams = new RequestParams();
        requestParams.add("sow_id", sow_id);
        requestParams.add("boar_id", boar_id);
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getSowLitterValues(requestParams);
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

    private String getNumberOfDaysBetween(String fromDate, String toDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = format.parse(fromDate);
            Date date2 = format.parse(toDate);
            long diff = date2.getTime() - date1.getTime();
            return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + " days";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "No data available";
    }

    private void local_getSowLitterValues() {
        String sowId = dbHelper.getAnimalId(textViewSowUsed.getText().toString());
        String boarId = dbHelper.getAnimalId(textViewBoarUsed.getText().toString());

        Cursor data = dbHelper.getSowLitterRecords(sowId, boarId);
        while(data.moveToNext()) {
            switch (data.getString(data.getColumnIndex("property_id"))) {
                case "6":
                    textViewDateWeaned.setText(data.getString(data.getColumnIndex("value")));
                    textViewLactation.setText(getNumberOfDaysBetween(textViewDateWeaned.getText().toString(), date_farrowed));
                    break;
                case "51":
                    textViewMales.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "52":
                    textViewFemales.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "53":
                    textViewSexRatio.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "56":
                    textViewAveBirthWeight.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "58":
                    textViewAveWeanWeight.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "57":
                    textViewNumWeaned.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "45":
                    textViewNoStillBorn.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "46":
                    textViewNoMummified.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "47":
                    textViewAbnormalities.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "48":
                    textViewParity.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "49":
                    textViewTLSB.setText(data.getString(data.getColumnIndex("value")));
                    break;
                case "50":
                    textViewLSBA.setText(data.getString(data.getColumnIndex("value")));
                    break;
            }
        }
    }

    private void api_getSowLitterValues(RequestParams requestParams) {
        ApiHelper.getAddSowLitterRecordPage("getAddSowLitterRecordPage", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                textViewMales.setText(setDefaultTextIfNull(editMales));
                textViewFemales.setText(setDefaultTextIfNull(editFemales));
                textViewSexRatio.setText(setDefaultTextIfNull(editSexRatio));
                textViewAveBirthWeight.setText(setDefaultTextIfNull(editAveBirthWeight));
                textViewAveWeanWeight.setText(setDefaultTextIfNull(editAveWeanWeight));
                textViewNumWeaned.setText(setDefaultTextIfNull(editNumWeaned));
                textViewNoStillBorn.setText(setDefaultTextIfNull(editNoStillBorn));
                textViewNoMummified.setText(setDefaultTextIfNull(editMummified));
                textViewAbnormalities.setText(setDefaultTextIfNull(editAbnormalities));
                textViewParity.setText(setDefaultTextIfNull(editParity));
                textViewTLSB.setText(setDefaultTextIfNull(editTLSB));
                textViewLSBA.setText(setDefaultTextIfNull(editLSBA));
                Log.d("SowLitter", "Successfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("SowLitter", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONArray propertyArray = jsonObject.getJSONArray("properties");
                    JSONObject propertyObject;
                    for (int i = propertyArray.length() - 1; i >= 0; i--) {
                        propertyObject = (JSONObject) propertyArray.get(i);
                        switch (propertyObject.getInt("property_id")) {
                            case 51:
                                editMales = propertyObject.get("value").toString();
                                break;
                            case 52:
                                editFemales = propertyObject.get("value").toString();
                                break;
                            case 53:
                                editSexRatio = propertyObject.get("value").toString();
                                break;
                            case 56:
                                editAveBirthWeight = propertyObject.get("value").toString();
                                break;
                            case 58:
                                editAveWeanWeight = propertyObject.get("value").toString();
                                break;
                            case 57:
                                editNumWeaned = propertyObject.get("value").toString();
                                break;
                            case 45:
                                editNoStillBorn = propertyObject.get("value").toString();
                                break;
                            case 46:
                                editMummified = propertyObject.get("value").toString();
                                break;
                            case 47:
                                editAbnormalities = propertyObject.get("value").toString();
                                break;
                            case 48:
                                editParity = propertyObject.get("value").toString();
                                break;
                            case 49:
                                editTLSB = propertyObject.get("value").toString();
                                break;
                            case 50:
                                editLSBA = propertyObject.get("value").toString();
                                break;
                        }
                    }
                }
                    return null;
            }
        });
    }

    private String setDefaultTextIfNull(String text) {
        if(text==null) return "Not specified";
        return ((text=="null" || text.isEmpty()) ? "Not specified" : text);
    }

    @Override public void applyDateFarrowed(String datefarrowed){ textViewdateFarrowed.setText(datefarrowed); }

    @Override public void applyParity(String parity){ textViewParity.setText(parity); }

    @Override public void applyNoMummified(String nomummified){ textViewNoMummified.setText(nomummified); }

    @Override public void applyNoStillBorn(String nostillborn){ textViewNoStillBorn.setText(nostillborn); }

    @Override public void applyAbnormalities(String abnormalities){ textViewAbnormalities.setText(abnormalities); }

}
