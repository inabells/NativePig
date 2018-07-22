package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.R;

public class StatusDialog extends DialogFragment {

    private static final String TAG = "StatusDialog";

    private Spinner status;
    public ViewStatusListener onViewStatusListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_status,null);

        status = view.findViewById(R.id.status);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editstatus = status.getSelectedItem().toString();
                        if(!editstatus.equals("")) onViewStatusListener.applyStatus(editstatus);
                    }

                });

        return builder.create();
    }

    public interface ViewStatusListener{
        void applyStatus(String status);
    }


    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            onViewStatusListener = (ViewStatusListener) getTargetFragment();
        } catch(ClassCastException e) {
            Log.e("StatusListener","onAtttach:ClassCastException: "+ e.getMessage());
        }
    }

}