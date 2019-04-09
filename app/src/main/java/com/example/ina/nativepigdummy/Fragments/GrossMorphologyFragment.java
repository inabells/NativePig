package com.example.ina.nativepigdummy.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.GrossMorphologyDialog;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class GrossMorphologyFragment extends Fragment implements GrossMorphologyDialog.ViewGrossMorphListener{

    public GrossMorphologyFragment() {
        // Required empty public constructor
    }

    private DatabaseHelper dbHelper;
    private String pigRegIdHolder;
    private TextView TextViewDateCollected, TextViewOtherMarks;
    private TextView registration_id;
    private ImageView edit_profile;
    private TextView hairType, hairLength, coatColor, colorPattern, headShape, skinType, earType, tailType, backLine;
    private String date_collected, hair_type, hair_length, coat_color, color_pattern, head_shape, skin_type, ear_type, tail_type, back_line, other_marks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @ Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gross_morphology, container, false);
        edit_profile = view.findViewById(R.id.edit_gross_morphology);

        dbHelper = new DatabaseHelper(getContext());
        TextViewDateCollected = (TextView) view.findViewById(R.id.textView_dateCollected);
        TextViewOtherMarks = (TextView) view.findViewById(R.id.textView_otherMarks);
        hairType  = (TextView) view.findViewById(R.id.textView_hairType);
        hairLength  = (TextView) view.findViewById(R.id.textView_hairLength);
        coatColor  = (TextView) view.findViewById(R.id.textView_coatColor);
        colorPattern  = (TextView) view.findViewById(R.id.textView_colorPattern);
        headShape  = (TextView) view.findViewById(R.id.textView_headShape);
        skinType  = (TextView) view.findViewById(R.id.textView_skinType);
        earType  = (TextView) view.findViewById(R.id.textView_earType);
        tailType  = (TextView) view.findViewById(R.id.textView_tailType);
        backLine  = (TextView) view.findViewById(R.id.textView_backLine);
        registration_id = view.findViewById(R.id.registration_id);

        pigRegIdHolder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(pigRegIdHolder);
        RequestParams params = buildParams();

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getGrossMorphProfile(params);
        } else {
            local_getGrossMorphProfile(pigRegIdHolder);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGrossMorphologyDialog(pigRegIdHolder);
            }
        });

        return view;
    }

    private RequestParams buildParams() {
        RequestParams params = new RequestParams();
        params.add("registry_id", pigRegIdHolder);
        return params;
    }

    private void local_getGrossMorphProfile(String reg_id) {
        Cursor data = dbHelper.getSinglePig(reg_id);
        while(data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "10": TextViewDateCollected.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "11": hairType.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "12": hairLength.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "13": coatColor.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "14": colorPattern.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "15": headShape.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "16": skinType.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "17": earType.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "18": tailType.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "19": backLine.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "20": TextViewOtherMarks.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }

    private void api_getGrossMorphProfile(RequestParams params) {
        ApiHelper.getAnimalProperties("getAnimalProperties", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                TextViewDateCollected.setText(setDefaultTextIfNull(date_collected));
                TextViewOtherMarks.setText(setDefaultTextIfNull(other_marks));
                hairType.setText(setDefaultTextIfNull(hair_type));
                hairLength.setText(setDefaultTextIfNull(hair_length));
                coatColor.setText(setDefaultTextIfNull(coat_color));
                colorPattern.setText(setDefaultTextIfNull(color_pattern));
                headShape.setText(setDefaultTextIfNull(head_shape));
                skinType.setText(setDefaultTextIfNull(skin_type));
                earType.setText(setDefaultTextIfNull(ear_type));
                tailType.setText(setDefaultTextIfNull(tail_type));
                backLine.setText(setDefaultTextIfNull(back_line));
                Log.d("GrossMorphology", "Succesfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("GrossMorphology", "Error: " + String.valueOf(statusCode));
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
                            case 10:
                                date_collected = propertyObject.get("value").toString();
                                break;
                            case 11:
                                hair_type = propertyObject.get("value").toString();
                                break;
                            case 12:
                                hair_length = propertyObject.get("value").toString();
                                break;
                            case 13:
                                coat_color = propertyObject.get("value").toString();
                                break;
                            case 14:
                                color_pattern = propertyObject.get("value").toString();
                                break;
                            case 15:
                                head_shape = propertyObject.get("value").toString();
                                break;
                            case 16:
                                skin_type = propertyObject.get("value").toString();
                                break;
                            case 17:
                                ear_type = propertyObject.get("value").toString();
                                break;
                            case 18:
                                tail_type = propertyObject.get("value").toString();
                                break;
                            case 19:
                                back_line = propertyObject.get("value").toString();
                                break;
                            case 20:
                                other_marks = propertyObject.get("value").toString();
                                break;
                        }
                    }
                }
                return null;
            }
        });
    }

    private String setDefaultTextIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "Not specified" : text);
    }

    public void openGrossMorphologyDialog(String regId){
        GrossMorphologyDialog dialog = new GrossMorphologyDialog(regId);
        dialog.setTargetFragment(GrossMorphologyFragment.this, 1);
        dialog.show(getFragmentManager(),"GrossMorphologyDialog");
    }

    @Override public void applyDateCollected(String datecollected){ TextViewDateCollected.setText(datecollected); }

    @Override public void applyOtherMarks(String othermarks){ TextViewOtherMarks.setText(othermarks); }

}
