package com.example.ina.nativepigdummy.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.DateDialog;
import com.example.ina.nativepigdummy.Dialog.GroupWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;


public class MortalityFragment extends Fragment {

    DatabaseHelper myDB;
    ArrayList<MortalityData> mortalityList;
    MortalityData mortalityData;
    ListView listView;
    private FloatingActionButton mortality;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_mortality, container, false);
        listView = view.findViewById(R.id.listview_mortality);
        mortality = view.findViewById(R.id.floating_action_mortality);

        myDB = new DatabaseHelper(getActivity());

        mortalityList = new ArrayList<>();
        Cursor data  = myDB.getMortalityContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                mortalityData = new MortalityData(data.getString(1), data.getString(2),data.getString(3), data.getString(4));
                mortalityList.add(i, mortalityData);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4));
                System.out.println(mortalityList.get(i).getMortality_reg_id());
                i++;
            }
            MortalityDataAdapter adapter = new MortalityDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, mortalityList);
            listView.setAdapter(adapter);
        }

        mortality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MortalityDialog dialog = new MortalityDialog();
                dialog.show(getFragmentManager(), "MortalityDialog");
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        }
        return true;
    }

}
