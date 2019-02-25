package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GrossMorphologyDialog extends DialogFragment {
    private static final String TAG = "GrossMorphologyDialog";
    private EditText datecollected;
    private RadioGroup hairType, hairLength, coatColor, colorPattern, headShape, skinType, earType, tailType, backLine;
    private EditText othermarks;
    public ViewGrossMorphListener onViewGrossMorphListener;
    private String reg_id;

    public GrossMorphologyDialog(String reg_id){
        this.reg_id = reg_id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_gross_morphology,null);

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
                            updateGrossMorphology(requestParams);
                        }else{
                            Toast.makeText(getActivity(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return builder.create();
    }

    private void updateGrossMorphology(RequestParams params) {
        ApiHelper.updateGrossMorphology("updateGrossMorphology", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("API HANDLER FAIL", errorResponse.toString());
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

        requestParams.add("registration_id", reg_id);
        requestParams.add("date_collected", editDateCollected);
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
                    DateDialog dialog = new DateDialog(v);
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
