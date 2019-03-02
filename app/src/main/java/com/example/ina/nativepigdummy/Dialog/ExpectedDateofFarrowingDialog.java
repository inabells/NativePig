package com.example.ina.nativepigdummy.Dialog;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

@SuppressLint("ValidFragment")
public class ExpectedDateofFarrowingDialog extends DialogFragment {

    private static final String TAG = "ExpectedDateofFarrowingDialog";

    private EditText dateoffarrowing;
    public ViewFarrowListener onViewFarrowListener;
    private String sow_id, boar_id;
    private String date_farrow, editDateFarrowed;


    public ExpectedDateofFarrowingDialog(String sowId, String boarId, String dateFarrow){
        this.sow_id = sowId;
        this.boar_id = boarId;
        this.date_farrow = dateFarrow;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_expected_dateof_farrowing,null);

        dateoffarrowing = view.findViewById(R.id.date_of_farrowing);
        dateoffarrowing.setText(date_farrow);

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
                            RequestParams requestParams = buildParams(sow_id, boar_id);
                            updateExpectedDateFarrow(requestParams);
                        }else{
                            Toast.makeText(getActivity(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return builder.create();
    }

    private void updateExpectedDateFarrow(RequestParams params) {
        ApiHelper.updateExpectedDateFarrow("updateExpectedDateFarrow", params, new BaseJsonHttpResponseHandler<Object>() {
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

    private RequestParams buildParams(String sow_id, String boar_id) {
        RequestParams requestParams = new RequestParams();

        String editDateFarrowed = dateoffarrowing.getText().toString();

        requestParams.add("sow_registration_id", sow_id);
        requestParams.add("boar_registration_id", boar_id);
        requestParams.add("expected_date_farrow", editDateFarrowed);

        return requestParams;
    }

    private void requestFocus (View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onStart(){
        super.onStart();
        dateoffarrowing.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    dialog.show(getActivity().getFragmentManager(),"Expected Date of Farrowing");
                }
            }
        });

    }

    public interface ViewFarrowListener{
        void applyText(String dateoffarrowing);

        void applyStatus(String status);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewFarrowListener = (ViewFarrowListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("FarrowListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}
