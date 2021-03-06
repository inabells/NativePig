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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.GrossMorphologyFragment;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class GrossMorphologyDialog extends DialogFragment {
    private static final String TAG = "GrossMorphologyDialog";

    private DatabaseHelper dbHelper;
    private EditText datecollected, othermarks;
    private RadioGroup hairType, hairLength, coatColor, colorPattern, headShape, skinType, earType, tailType, backLine;
    private String reg_id, date_collected, hair_type, hair_length, coat_color, color_pattern, head_shape, skin_type, ear_type, tail_type, back_line, other_marks;
    public ViewGrossMorphListener onViewGrossMorphListener;

    public GrossMorphologyDialog(String reg_id){
        this.reg_id = reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_gross_morphology,null);

        dbHelper = new DatabaseHelper(getContext());
        //Radio Group
        datecollected = view.findViewById(R.id.date_collected_gross);
        hairType = view.findViewById(R.id.radioGroup_HairType);
        hairLength = view.findViewById(R.id.radioGroup_HairLength);
        coatColor = view.findViewById(R.id.radioGroup_CoatColor);
        colorPattern = view.findViewById(R.id.radioGroup_ColorPattern);
        headShape = view.findViewById(R.id.radioGroup_HeadShape);
        skinType = view.findViewById(R.id.radioGroup_SkinType);
        earType = view.findViewById(R.id.radioGroup_EarType);
        tailType = view.findViewById(R.id.radioGroup_TailType);
        backLine = view.findViewById(R.id.radioGroup_BackLine);
        othermarks = view.findViewById(R.id.other_marks);

        if(ApiHelper.isInternetAvailable(getContext())){
            api_getGrossMorphProfile(reg_id, view);
        }else{
            local_getGrossMorphProfile(reg_id, view);
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
                        api_updateGrossMorphology(requestParams);
                    }else{
                        local_updateGrossMorphology();
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
            if(fragList.get(i) instanceof GrossMorphologyFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void local_getGrossMorphProfile(String reg_id, final View view) {
        Cursor data = dbHelper.getSinglePig(reg_id);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "10": datecollected.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "11": hair_type = data.getString(data.getColumnIndex("value"));
                    break;
                case "12": hair_length = data.getString(data.getColumnIndex("value"));
                    break;
                case "13": coat_color = data.getString(data.getColumnIndex("value"));
                    break;
                case "14": color_pattern = data.getString(data.getColumnIndex("value"));
                    break;
                case "15": head_shape = data.getString(data.getColumnIndex("value"));
                    break;
                case "16": skin_type = data.getString(data.getColumnIndex("value"));
                    break;
                case "17": ear_type = data.getString(data.getColumnIndex("value"));
                    break;
                case "18": tail_type = data.getString(data.getColumnIndex("value"));
                    break;
                case "19": back_line = data.getString(data.getColumnIndex("value"));
                    break;
                case "20": othermarks.setText(setBlankIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
        setCheckedRadioButtons(view);
    }

    private void local_updateGrossMorphology() {
        int editHairType = hairType.getCheckedRadioButtonId();
        int editHairLength = hairLength.getCheckedRadioButtonId();
        int editCoatColor = coatColor.getCheckedRadioButtonId();
        int editColorPattern = colorPattern.getCheckedRadioButtonId();
        int editHeadShape = headShape.getCheckedRadioButtonId();
        int editSkinType = skinType.getCheckedRadioButtonId();
        int editEarType = earType.getCheckedRadioButtonId();
        int editTailType = tailType.getCheckedRadioButtonId();
        int editBackLine = backLine.getCheckedRadioButtonId();
        String editDateCollected = datecollected.getText().toString();
        String editOtherMarks= othermarks.getText().toString();

        boolean insertData = dbHelper.addGrossMorphologyData(reg_id,
                editDateCollected, (String) ((RadioButton) hairType.findViewById(editHairType)).getText(),
                (String) ((RadioButton) hairLength.findViewById(editHairLength)).getText(),
                (String) ((RadioButton) coatColor.findViewById(editCoatColor)).getText(),
                (String) ((RadioButton) colorPattern.findViewById(editColorPattern)).getText(),
                (String) ((RadioButton) headShape.findViewById(editHeadShape)).getText(),
                (String) ((RadioButton) skinType.findViewById(editSkinType)).getText(),
                (String) ((RadioButton) earType.findViewById(editEarType)).getText(),
                (String) ((RadioButton) tailType.findViewById(editTailType)).getText(),
                (String) ((RadioButton) backLine.findViewById(editBackLine)).getText(),
                editOtherMarks, "false");

        if(insertData) Toast.makeText(getContext(), "Data successfully inserted locally", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Local insert error", Toast.LENGTH_SHORT).show();
    }

    private void api_updateGrossMorphology(RequestParams params) {
        ApiHelper.fetchGrossMorphology("fetchGrossMorphology", params, new BaseJsonHttpResponseHandler<Object>() {
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
        int editHairType = hairType.getCheckedRadioButtonId();
        int editHairLength = hairLength.getCheckedRadioButtonId();
        int editCoatColor = coatColor.getCheckedRadioButtonId();
        int editColorPattern = colorPattern.getCheckedRadioButtonId();
        int editHeadShape = headShape.getCheckedRadioButtonId();
        int editSkinType = skinType.getCheckedRadioButtonId();
        int editEarType = earType.getCheckedRadioButtonId();
        int editTailType = tailType.getCheckedRadioButtonId();
        int editBackLine = backLine.getCheckedRadioButtonId();
        String editDateCollected = datecollected.getText().toString();
        String editOtherMarks= othermarks.getText().toString();

        requestParams.add("registry_id", reg_id);
        requestParams.add("date_collected_gross", editDateCollected);
        requestParams.add("hair_type", (String) ((RadioButton) hairType.findViewById(editHairType)).getText());
        requestParams.add("hair_length", (String) ((RadioButton) hairLength.findViewById(editHairLength)).getText());
        requestParams.add("coat_color", (String) ((RadioButton) coatColor.findViewById(editCoatColor)).getText());
        requestParams.add("color_pattern", (String) ((RadioButton) colorPattern.findViewById(editColorPattern)).getText());
        requestParams.add("head_shape", (String) ((RadioButton) headShape.findViewById(editHeadShape)).getText());
        requestParams.add("skin_type", (String) ((RadioButton) skinType.findViewById(editSkinType)).getText());
        requestParams.add("ear_type", (String) ((RadioButton) earType.findViewById(editEarType)).getText());
        requestParams.add("tail_type", (String) ((RadioButton) tailType.findViewById(editTailType)).getText());
        requestParams.add("backline", (String) ((RadioButton) backLine.findViewById(editBackLine)).getText());
        requestParams.add("other_marks", editOtherMarks);

        return requestParams;
    }

    private void api_getGrossMorphProfile(String id, final View view) {
        RequestParams params = new RequestParams();
        params.add("registry_id", id);

        ApiHelper.getAnimalProperties("getAnimalProperties", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                datecollected.setText(setBlankIfNull(date_collected));
                othermarks.setText(setBlankIfNull(other_marks));
                setCheckedRadioButtons(view);
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

    private void setCheckedRadioButtons(View view) {
        RadioButton hairtypecurly = view.findViewById(R.id.hair_type_curly);
        RadioButton hairtypestraight = view.findViewById(R.id.hair_type_straight);
        RadioButton hairlengthshort = view.findViewById(R.id.hair_length_short);
        RadioButton hairlengthlong = view.findViewById(R.id.hair_length_long);
        RadioButton coatcolorblack = view.findViewById(R.id.coat_color_black);
        RadioButton coatcolorothers = view.findViewById(R.id.coat_color_others);
        RadioButton colorpatternplain = view.findViewById(R.id.color_pattern_plain);
        RadioButton colorpatternsocks = view.findViewById(R.id.color_pattern_socks);
        RadioButton headshapeconcave = view.findViewById(R.id.head_shape_concave);
        RadioButton headshapestraight = view.findViewById(R.id.head_shape_straight);
        RadioButton skintypesmooth = view.findViewById(R.id.skin_type_smooth);
        RadioButton skintypewrinkled = view.findViewById(R.id.skin_type_wrinkled);
        RadioButton eartypedrooping = view.findViewById(R.id.ear_type_drooping);
        RadioButton eartypesemilop = view.findViewById(R.id.ear_type_semi_lop);
        RadioButton eartypeerect = view.findViewById(R.id.ear_type_erect);
        RadioButton tailtypecurly = view.findViewById(R.id.tail_type_curly);
        RadioButton tailtypestraight = view.findViewById(R.id.tail_type_straight);
        RadioButton backlineswayback = view.findViewById(R.id.backline_swayback);
        RadioButton backlinestraight = view.findViewById(R.id.backline_straight);

        if(hair_type != null)
            if(hair_type.equals("Curly")) hairtypecurly.setChecked(true);
            else hairtypestraight.setChecked(true);
        if(hair_length != null)
            if(hair_length.equals("Short")) hairlengthshort.setChecked(true);
            else hairlengthlong.setChecked(true);
        if(coat_color != null)
            if(coat_color.equals("Black")) coatcolorblack.setChecked(true);
            else coatcolorothers.setChecked(true);
        if(color_pattern != null)
            if(color_pattern.equals("Plain")) colorpatternplain.setChecked(true);
            else colorpatternsocks.setChecked(true);
        if(head_shape != null)
            if(head_shape.equals("Concave")) headshapeconcave.setChecked(true);
            else headshapestraight.setChecked(true);
        if(skin_type != null)
            if(skin_type.equals("Smooth")) skintypesmooth.setChecked(true);
            else skintypewrinkled.setChecked(true);
        if(ear_type != null)
            if(ear_type.equals("Drooping")) eartypedrooping.setChecked(true);
            else if (ear_type.equals("Semi-lop")) eartypesemilop.setChecked(true);
            else eartypeerect.setChecked(true);
        if(tail_type != null)
            if(tail_type.equals("Curly")) tailtypecurly.setChecked(true);
            else tailtypestraight.setChecked(true);
        if(back_line != null)
            if(back_line.equals("Swayback")) backlineswayback.setChecked(true);
            else backlinestraight.setChecked(true);
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
                    dialog.show(getActivity().getFragmentManager(),"Gross Morphology Dialog");
                }
            }
        });

    }

    public interface ViewGrossMorphListener{
        void applyDateCollected(String datecollected);
        void applyOtherMarks(String othermarks);
    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);
        try{
            onViewGrossMorphListener = (ViewGrossMorphListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("MorphCharListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }
}
