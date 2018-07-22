package com.example.ina.nativepigdummy.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Activities.ViewBreederActivity;
import com.example.ina.nativepigdummy.Activities.ViewBreedingActivity;
import com.example.ina.nativepigdummy.Adapters.BreedingRecordDataAdapter;
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Data.BreedingRecordData;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.BreedingRecordsDialog;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;


public class SowBoarIDDateBredFragment extends Fragment {

    ListView listView;
    private FloatingActionButton breedingrecord;
    DatabaseHelper myDB;
    ArrayList<BreedingRecordData> breedingRecordList;
    BreedingRecordData breedingRecordData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_sow_boar_iddate_bred, container, false);
        listView = view.findViewById(R.id.listview_breeding_record);
        breedingrecord = view.findViewById(R.id.floating_action_breeding_record);

        myDB = new DatabaseHelper(getActivity());

        breedingRecordList = new ArrayList<>();
        Cursor data  = myDB.getBreedingRecordsContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                breedingRecordData = new BreedingRecordData(data.getString(1), data.getString(2),data.getString(3));
                breedingRecordList.add(i, breedingRecordData);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3));
                System.out.println(breedingRecordList.get(i).getSow_id());
                i++;
            }
            BreedingRecordDataAdapter adapter = new BreedingRecordDataAdapter(getActivity(), R.layout.listview_breeding_record, breedingRecordList);
            listView.setAdapter(adapter);
        }

        breedingrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreedingRecordsDialog dialog = new BreedingRecordsDialog();
                dialog.show(getFragmentManager(), "BreedingRecordsDialog");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_view_breeding = new Intent(getActivity(), ViewBreedingActivity.class);
                startActivity(intent_view_breeding);
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
        if(item.getItemId() == R.id.action_search) {

        }
        return true;
    }
}
