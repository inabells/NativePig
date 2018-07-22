package com.example.ina.nativepigdummy.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

public class ViewProfileDialog extends AppCompatDialogFragment {
    private EditText farmname;
    private EditText contactno;
    private EditText region;
    private EditText province;
    private EditText town;
    private EditText barangay;
    DatabaseHelper myDB;
    private ViewProfileListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_profile,null);

        farmname = view.findViewById(R.id.farm_name);
        contactno = view.findViewById(R.id.contact_no);
        region = view.findViewById(R.id.region);
        province = view.findViewById(R.id.province);
        town = view.findViewById(R.id.town);
        barangay = view.findViewById(R.id.barangay);
        myDB = new DatabaseHelper(getActivity());

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editfarmname = farmname.getText().toString();
                        String editcontactno = contactno.getText().toString();
                        String editregion = region.getText().toString();
                        String editprovince = province.getText().toString();
                        String edittown = town.getText().toString();
                        String editbarangay = barangay.getText().toString();

                        addProfileRecordData(editfarmname,editcontactno, editregion,editprovince,edittown,editbarangay);

                        if(!editfarmname.equals("")) listener.applyFarmName(editfarmname);
                        if(!editcontactno.equals("")) listener.applyContactNo(editcontactno);
                        if(!editregion.equals("")) listener.applyRegion(editregion);
                        if(!editprovince.equals("")) listener.applyProvince(editprovince);
                        if(!edittown.equals("")) listener.applyTown(edittown);
                        if(!editbarangay.equals("")) listener.applyBarangay(editbarangay);

                    }
                });

        return builder.create();
    }

    @Override
    public  void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (ViewProfileListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ViewProfileListener");

        }
    }

    public interface ViewProfileListener{
        void applyFarmName(String farmname);
        void applyContactNo(String contactno);
        void applyRegion(String region);
        void applyProvince(String province);
        void applyTown(String town);
        void applyBarangay(String barangay);
    }



    public void addProfileRecordData(String farmname, String contactno, String region, String province, String town, String barangay){
        boolean insertData = myDB.addProfileRecordData(farmname,contactno,region,province,town,barangay);

        if(insertData==true){
            Toast.makeText(getActivity(),"Data successfully inserted!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Something went wrong.",Toast.LENGTH_LONG).show();
        }
    }

}
