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
import com.example.ina.nativepigdummy.Dialog.MorphCharDialog;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MorphCharFragment extends Fragment implements MorphCharDialog.ViewMorphCharListener{

    public MorphCharFragment() {
        // Required empty public constructor
    }

    private DatabaseHelper dbHelper;
    private String pigRegIdHolder, editDateCollected, editEarLength, editHeadLength, editSnoutLength, editBodyLength,
            editHeartGirth, editPelvicWidth, editTailLength, editHeightWithers, editNormalTeats;
    private TextView TextViewDateCollected, TextViewEarLength, TextViewHeadLength, TextViewSnoutLength, TextViewBodyLength;
    private TextView TextViewHeartGirth, TextViewPelvicWidth, TextViewTailLength, TextViewHeightAtWithers, TextViewNormalTeats, registration_id;
    private ImageView edit_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @ Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_morph_char, container, false);
        edit_profile = view.findViewById(R.id.edit_morph_char);

        dbHelper = new DatabaseHelper(getContext());
        TextViewDateCollected = (TextView) view.findViewById(R.id.textView_dateCollected);
        TextViewEarLength = (TextView) view.findViewById(R.id.textView_earLength);
        TextViewHeadLength = (TextView) view.findViewById(R.id.textView_headLength);
        TextViewSnoutLength = (TextView) view.findViewById(R.id.textView_snoutLength);
        TextViewBodyLength = (TextView) view.findViewById(R.id.textView_bodyLength);
        TextViewHeartGirth = (TextView) view.findViewById(R.id.textView_heartGirth);
        TextViewPelvicWidth = (TextView) view.findViewById(R.id.textView_pelvicWidth);
        TextViewTailLength = (TextView) view.findViewById(R.id.textView_tailLength);
        TextViewHeightAtWithers = (TextView) view.findViewById(R.id.textView_heightWithers);
        TextViewNormalTeats = (TextView) view.findViewById(R.id.textView_normalTeats);

        registration_id = view.findViewById(R.id.registration_id);
        pigRegIdHolder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(pigRegIdHolder);
        RequestParams params = buildParams();

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getMorphCharProfile(params);
        } else{
            local_getMorphCharProfile(pigRegIdHolder);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMorphCharDialog(pigRegIdHolder);
            }
        });

        return view;
    }

    private RequestParams buildParams() {
        RequestParams params = new RequestParams();
        params.add("registration_id", pigRegIdHolder);
        return params;
    }

    private void local_getMorphCharProfile(String reg_id) {
        Cursor data = dbHelper.getMorphCharProfile(reg_id);

        if (data.moveToFirst()) {
            TextViewDateCollected.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("date_collected"))));
            TextViewEarLength.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("ear_length"))));
            TextViewHeadLength.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("head_length"))));
            TextViewSnoutLength.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("snout_length"))));
            TextViewBodyLength.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("body_length"))));
            TextViewHeartGirth.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("heart_girth"))));
            TextViewPelvicWidth.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("pelvic_width"))));
            TextViewTailLength.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("tail_length"))));
            TextViewHeightAtWithers.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("height_at_withers"))));
            TextViewNormalTeats.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("normal_teats"))));
        }
    }

    private void api_getMorphCharProfile(RequestParams params) {
        ApiHelper.getMorphCharProfile("getMorphCharProfile", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                TextViewDateCollected.setText(setDefaultTextIfNull(editDateCollected));
                TextViewEarLength.setText(setDefaultTextIfNull(editEarLength));
                TextViewHeadLength.setText(setDefaultTextIfNull(editHeadLength));
                TextViewSnoutLength.setText(setDefaultTextIfNull(editSnoutLength));
                TextViewBodyLength.setText(setDefaultTextIfNull(editBodyLength));
                TextViewHeartGirth.setText(setDefaultTextIfNull(editHeartGirth));
                TextViewPelvicWidth.setText(setDefaultTextIfNull(editPelvicWidth));
                TextViewTailLength.setText(setDefaultTextIfNull(editTailLength));
                TextViewHeightAtWithers.setText(setDefaultTextIfNull(editHeightWithers));
                TextViewNormalTeats.setText(setDefaultTextIfNull(editNormalTeats));
                Log.d("MorphChar", "Succesfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("MorphChar", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                editDateCollected = jsonObject.get("date_collected").toString();
                editEarLength = jsonObject.get("ear_length").toString();
                editHeadLength = jsonObject.get("head_length").toString();
                editSnoutLength = jsonObject.get("snout_length").toString();
                editBodyLength = jsonObject.get("body_length").toString();
                editHeartGirth = jsonObject.get("heart_girth").toString();
                editPelvicWidth = jsonObject.get("pelvic_width").toString();
                editTailLength = jsonObject.get("tail_length").toString();
                editHeightWithers = jsonObject.get("height_at_withers").toString();
                editNormalTeats = jsonObject.get("normal_teats").toString();
                return null;
            }
        });
    }

    public void openMorphCharDialog(String regId){
        MorphCharDialog dialog = new MorphCharDialog(regId);
        dialog.setTargetFragment(MorphCharFragment.this,1);
        dialog.show(getFragmentManager(),"MorphCharDialog");
    }

    private String setDefaultTextIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "Not specified" : text);
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
