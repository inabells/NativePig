package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.MorphCharFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class MorphCharDialog extends DialogFragment {

    private static final String TAG = "MorphometricCharacteristicsDialog";

    private DatabaseHelper dbHelper;
    private EditText datecollected, earlength, headlength, snoutlength, bodylength, heartgirth;
    private EditText pelvicwidth, taillength, heightatwithers;
    private String editDateCollected, editEarLength, editHeadLength, editSnoutLength, editBodyLength, editHeartGirth, editPelvicWidth, editTailLength, editHeightWithers, editNormalTeats;
    private SeekBar numberofnormalteats;
    private TextView progressText;
    public ViewMorphCharListener onViewMorphCharListener;
    private String reg_id;

    public MorphCharDialog(String reg_id){
        this.reg_id = reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_morph_char,null);

        dbHelper = new DatabaseHelper(getContext());
        datecollected = view.findViewById(R.id.date_collected);
        earlength = view.findViewById(R.id.ear_length);
        headlength = view.findViewById(R.id.head_length);
        snoutlength = view.findViewById(R.id.snout_length);
        bodylength = view.findViewById(R.id.body_length);
        heartgirth = view.findViewById(R.id.heart_girth);
        pelvicwidth = view.findViewById(R.id.pelvic_width);
        taillength = view.findViewById(R.id.tail_length);
        heightatwithers = view.findViewById(R.id.height_at_withers);
        numberofnormalteats = view.findViewById(R.id.number_of_normal_teats);
        progressText = view.findViewById(R.id.textViewNormalTeats);
        numberofnormalteats.setMax(18);
        numberofnormalteats.setProgress(6);

        numberofnormalteats.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 6;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                progressText.setText(String.valueOf(progressChangedValue));
            }
        });

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getMorphCharProfile(reg_id);
        }else{
            local_getMorphCharProfile(reg_id);
        }

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ApiHelper.isInternetAvailable(getContext())){
                            RequestParams requestParams = buildParams(reg_id);
                            api_updateMorphChar(requestParams);
                        }else{
                            local_updateMorphChar();
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        List<Fragment> fragList = getFragmentManager().getFragments();
        Fragment fragment = null;
        for(int i=0; i<fragList.size(); i++)
            if(fragList.get(i) instanceof MorphCharFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void local_updateMorphChar() {
        String editDateCollected = datecollected.getText().toString();
        String editEarLength = earlength.getText().toString();
        String editHeadLength = headlength.getText().toString();
        String editSnoutLength = snoutlength.getText().toString();
        String editBodyLength = bodylength.getText().toString();
        String editHeartGirth = heartgirth.getText().toString();
        String editPelvicWidth = pelvicwidth.getText().toString();
        String editTailLength = taillength.getText().toString();
        String editHeightWithers = heightatwithers.getText().toString();
        String editNormalTeats = progressText.getText().toString();

        boolean insertData = dbHelper.addMorphCharData(reg_id,
                editDateCollected, editEarLength, editHeadLength, editSnoutLength, editBodyLength,
                editHeartGirth, editPelvicWidth, editTailLength, editHeightWithers, editNormalTeats, "false");

        if(insertData) Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
    }

    private void api_updateMorphChar(RequestParams params) {
        ApiHelper.fetchMorphometricCharacteristics("fetchMorphometricCharacteristics", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("API HANDLER FAIL", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private RequestParams buildParams(String reg_id) {
        RequestParams requestParams = new RequestParams();
        String editDateCollected = datecollected.getText().toString();
        String editEarLength = earlength.getText().toString();
        String editHeadLength = headlength.getText().toString();
        String editSnoutLength = snoutlength.getText().toString();
        String editBodyLength = bodylength.getText().toString();
        String editHeartGirth = heartgirth.getText().toString();
        String editPelvicWidth = pelvicwidth.getText().toString();
        String editTailLength = taillength.getText().toString();
        String editHeightWithers = heightatwithers.getText().toString();
        String editNormalTeats = progressText.getText().toString();

        requestParams.add("registry_id", reg_id);
        requestParams.add("date_collected_morpho", editDateCollected);
        requestParams.add("ear_length", editEarLength);
        requestParams.add("head_length", editHeadLength);
        requestParams.add("snout_length", editSnoutLength);
        requestParams.add("body_length", editBodyLength);
        requestParams.add("heart_girth", editHeartGirth);
        requestParams.add("pelvic_width", editPelvicWidth);
        requestParams.add("tail_length", editTailLength);
        requestParams.add("height_at_withers", editHeightWithers);
        requestParams.add("number_normal_teats", editNormalTeats);

        return requestParams;
    }

    private void api_getMorphCharProfile(String id) {
        RequestParams params = new RequestParams();
        params.add("registry_id", id);

        ApiHelper.getAnimalProperties("getAnimalProperties", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                datecollected.setText(setBlankIfNull(editDateCollected));
                earlength.setText(setBlankIfNull(editEarLength));
                headlength.setText(setBlankIfNull(editHeadLength));
                snoutlength.setText(setBlankIfNull(editSnoutLength));
                bodylength.setText(setBlankIfNull(editBodyLength));
                heartgirth.setText(setBlankIfNull(editHeartGirth));
                pelvicwidth.setText(setBlankIfNull(editPelvicWidth));
                taillength.setText(setBlankIfNull(editTailLength));
                heightatwithers.setText(setBlankIfNull(editHeightWithers));
                progressText.setText(setBlankIfNull(editNormalTeats));
                Log.d("MorphChar", "Succesfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("MorphChar", "Error: " + String.valueOf(statusCode));
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
                            case 21:
                                editDateCollected = propertyObject.get("value").toString();
                                break;
                            case 22:
                                editEarLength = propertyObject.get("value").toString();
                                break;
                            case 23:
                                editHeadLength = propertyObject.get("value").toString();
                                break;
                            case 24:
                                editSnoutLength = propertyObject.get("value").toString();
                                break;
                            case 25:
                                editBodyLength = propertyObject.get("value").toString();
                                break;
                            case 26:
                                editHeartGirth = propertyObject.get("value").toString();
                                break;
                            case 27:
                                editPelvicWidth = propertyObject.get("value").toString();
                                break;
                            case 28:
                                editTailLength = propertyObject.get("value").toString();
                                break;
                            case 29:
                                editHeightWithers = propertyObject.get("value").toString();
                                break;
                            case 30:
                                editNormalTeats = propertyObject.get("value").toString();
                                break;
                        }
                    }
                }
                return null;
            }
        });
    }

    private void local_getMorphCharProfile(String reg_id) {
        Cursor data = dbHelper.getSinglePig(reg_id);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "21": datecollected.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "22": earlength.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "23": headlength.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "24": snoutlength.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "25": bodylength.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "26": heartgirth.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "27": pelvicwidth.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "28": taillength.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "29": heightatwithers.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "30": progressText.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }

    private String setBlankIfNull(String text) {
        if(text==null) return "";
        return ((text=="null" || text.isEmpty()) ? "" : text);
    }

    private void requestFocus (View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onStart(){
        super.onStart();
        datecollected.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    NewDateDialog dialog = new NewDateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Morph Char Dialog");
                }
            }
        });

    }

    public interface ViewMorphCharListener{
        void applyDateCollected(String datecollected);
        void applyEarLength(String earlength);
        void applyHeadLength(String headlength);
        void applySnoutLength(String snoutlength);
        void applyBodyLength(String bodylength);
        void applyHeartGirth(String heartgirth);
        void applyPelvicWidth(String pelvicwidth);
        void applyTailLength(String taillength);
        void applyHeightAtWithers(String heightatwithers);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewMorphCharListener = (ViewMorphCharListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("MorphCharListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}
