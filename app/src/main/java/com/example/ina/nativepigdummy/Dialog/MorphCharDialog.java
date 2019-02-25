package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MorphCharDialog extends DialogFragment {

    private static final String TAG = "MorphometricCharacteristicsDialog";
    private EditText datecollected, earlength, headlength, snoutlength, bodylength, heartgirth;
    private EditText pelvicwidth, taillength, heightatwithers;
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
                            updateMorphChar(requestParams);
                        }else{
                            Toast.makeText(getActivity(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return builder.create();
    }

    private void updateMorphChar(RequestParams params) {
        ApiHelper.updateMorphChar("updateMorphChar", params, new BaseJsonHttpResponseHandler<Object>() {
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

        requestParams.add("registration_id", reg_id);
        requestParams.add("date_collected", editDateCollected);
        requestParams.add("ear_length", editEarLength);
        requestParams.add("head_length", editHeadLength);
        requestParams.add("snout_length", editSnoutLength);
        requestParams.add("body_girth", editBodyLength);
        requestParams.add("heart_girth", editHeartGirth);
        requestParams.add("pelvic_width", editPelvicWidth);
        requestParams.add("tail_length", editTailLength);
        requestParams.add("height_at_withers", editHeightWithers);
        requestParams.add("normal_teats", editNormalTeats);

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
