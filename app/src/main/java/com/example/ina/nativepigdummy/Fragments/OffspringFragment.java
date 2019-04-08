package com.example.ina.nativepigdummy.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ina.nativepigdummy.Adapters.OffspringDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OffspringData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.EditOffspringDialog;
import com.example.ina.nativepigdummy.Dialog.GroupWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.IndividualWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.SowAndLitterDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;


public class OffspringFragment extends Fragment {

    ListView listView;
    DatabaseHelper dbHelper;
    ArrayList<OffspringData> offspringList;
    OffspringData offspringData;
    String sowId, sowRegId, boarRegId, boarId, groupingId;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton group, individual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_offspring, container, false);
        listView = view.findViewById(R.id.listview_offspring);
        floatingActionMenu = view.findViewById(R.id.floating_action_menu);
        group = view.findViewById(R.id.group_weighing);
        individual = view.findViewById(R.id.individual_weighing);
        dbHelper = new DatabaseHelper(getActivity());
        offspringList = new ArrayList<>();

        sowRegId = getActivity().getIntent().getStringExtra("sow_idSLR");
        boarRegId = getActivity().getIntent().getStringExtra("boar_idSLR");

        sowId = dbHelper.getAnimalId(sowRegId);
        boarId = dbHelper.getAnimalId(boarRegId);
        groupingId = dbHelper.getGroupingId(sowId, boarId);

        local_getOffsprings();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String offspring_id = offspringList.get(i).getOffspring_id();
                EditOffspringDialog dialog = new EditOffspringDialog();
                Bundle data = new Bundle();
                data.putString("OffspringRegId", offspring_id);
                dialog.setArguments(data);
                //dialog.setTargetFragment(OffspringFragment.this, 1);
                dialog.show(getFragmentManager(),"EditOffspringDialog");
            }
        });


        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupWeighingDialog dialog = new GroupWeighingDialog();
                //dialog.show(getActivity().getFragmentManager(), "GroupWeighingDialog");
                dialog.show(getActivity().getSupportFragmentManager(), "IndividualWeighingDialog");
            }
        });

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndividualWeighingDialog dialog = new IndividualWeighingDialog();
//                dialog.show(getActivity().getFragmentManager(), "IndividualWeighingDialog");
                dialog.show(getActivity().getSupportFragmentManager(), "IndividualWeighingDialog");
            }
        });

        return view;
    }

    private void local_getOffsprings() {
        String regId = "";
        String sex = "";
        String birthWeight = "";
        String weaningWeight = "";
        Cursor data = dbHelper.getOffspringContents(groupingId);
        while(data.moveToNext()){
            switch(data.getString(data.getColumnIndex("property_id"))){
                case "4":
                    offspringData = new OffspringData(regId, sex, birthWeight, weaningWeight);
                    offspringList.add(offspringData);
                    sex = "";
                    birthWeight = "";
                    weaningWeight = "";
                    regId = data.getString(data.getColumnIndex("value"));
                    break;
                case "2": sex = data.getString(data.getColumnIndex("value"));
                        break;
                case "5": birthWeight = data.getString(data.getColumnIndex("value"));
                        break;
                case "7": weaningWeight = data.getString(data.getColumnIndex("value"));
            }
        }
        offspringData = new OffspringData(regId, sex, birthWeight, weaningWeight);
        offspringList.add(offspringData);
        OffspringDataAdapter adapter = new OffspringDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, offspringList);
        listView.setAdapter(adapter);
    }
}
