package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Fragments.ViewGrowerFragment;
import com.example.ina.nativepigdummy.R;

public class GrowerDialog extends DialogFragment {
    private static final String TAG = "GrowerDialog";

    public GrowerDialog(){

    }

    private ImageView edit_profile;
    private TextView registration_id;
    private Switch add_breeder;
    private String pigRegIdHolder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_view_grower, null);

        edit_profile = view.findViewById(R.id.edit_view_grower);

        registration_id = view.findViewById(R.id.registration_id);
        add_breeder = view.findViewById(R.id.add_as_breeder);

        pigRegIdHolder = getArguments().getString("ListClickValue");
        registration_id.setText(pigRegIdHolder);

        add_breeder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    AddAsBreederPromptDialog(pigRegIdHolder);
                    add_breeder.setChecked(false);
                }
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGrowerDialog growerDialog = new ViewGrowerDialog(pigRegIdHolder);
                growerDialog.setTargetFragment(GrowerDialog.this, 1);
                growerDialog.show(getFragmentManager(),"ViewGrowerDialog");
            }
        });


        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public void AddAsBreederPromptDialog(String regId){
        AddAsBreederPromptDialog dialog = new AddAsBreederPromptDialog(regId);
        dialog.setTargetFragment(GrowerDialog.this,1);
        dialog.show(getFragmentManager(),"AddAsBreederPromptDialog");
    }
}
