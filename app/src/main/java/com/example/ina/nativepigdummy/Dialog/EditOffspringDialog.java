package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Fragments.MorphCharFragment;
import com.example.ina.nativepigdummy.Fragments.OffspringFragment;
import com.example.ina.nativepigdummy.R;

import java.util.List;

public class EditOffspringDialog extends DialogFragment{
    private static final String TAG = "EditOffspringDialog";

    String offspringRegId;
    EditText textViewOffspring, textViewDateWeaned, textViewWeaningWeight;
    Spinner textViewSex;
    DatabaseHelper dbHelper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_offspring, null);

        offspringRegId = getArguments().getString("OffspringRegId");
        textViewOffspring = view.findViewById(R.id.offspring_earnotch);
        textViewSex = view.findViewById(R.id.offspring_sex);
        textViewDateWeaned = view.findViewById(R.id.date_weaned);
        textViewWeaningWeight = view.findViewById(R.id.weaning_weight);

        dbHelper = new DatabaseHelper(getActivity());


        local_getOffspringRecord();

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                local_updateOffspringRecord();
            }
        });

        return builder.create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        List<Fragment> fragList = getFragmentManager().getFragments();
        Fragment fragment = null;
        for(int i=0; i<fragList.size(); i++)
            if(fragList.get(i) instanceof OffspringFragment)
                fragment = fragList.get(i);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void local_getOffspringRecord() {
        Cursor data = dbHelper.getSinglePig(offspringRegId);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "1": textViewOffspring.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "6": textViewDateWeaned.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
                case "7": textViewWeaningWeight.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                    break;
            }
        }
    }

    private void local_updateOffspringRecord() {
        String editOffspringEarnotch = textViewOffspring.getText().toString();
        String editSex = textViewSex.getSelectedItem().toString();
        String editDateWeaned = textViewDateWeaned.getText().toString();
        String editWeaningWeight = textViewWeaningWeight.getText().toString();

        String animalId = dbHelper.getAnimalId(offspringRegId);
        int animalIdInt = Integer.parseInt(animalId);

        dbHelper.insertOrReplaceInAnimalPropertyDB(1, animalIdInt, editOffspringEarnotch, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(2, animalIdInt, editSex, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(6, animalIdInt, editDateWeaned, "false");
        dbHelper.insertOrReplaceInAnimalPropertyDB(7, animalIdInt, editWeaningWeight, "false");
    }

    private String setDefaultTextIfNull(String text) {
        return ((text=="null" || text.isEmpty()) ? "Not specified" : text);
    }
}
