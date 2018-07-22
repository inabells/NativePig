package com.example.ina.nativepigdummy.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Adapters.OffspringDataAdapter;
import com.example.ina.nativepigdummy.Adapters.OthersDataAdapter;
import com.example.ina.nativepigdummy.Data.OffspringData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.GroupWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.IndividualWeighingDialog;
import com.example.ina.nativepigdummy.Dialog.OthersDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;


public class OffspringFragment extends Fragment {

    ListView listView;
    DatabaseHelper myDB;
    ArrayList<OffspringData> offspringList;
    OffspringData offspringData;
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


        myDB = new DatabaseHelper(getActivity());

        offspringList = new ArrayList<>();
        Cursor data  = myDB.getOffspringRecordsContent();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                offspringData = new OffspringData(data.getString(1), data.getString(2),data.getString(3), data.getString(4));
                offspringList.add(i, offspringData);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4));
                System.out.println(offspringList.get(i).getOffspring_id());
                i++;
            }

            OffspringDataAdapter adapter = new OffspringDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, offspringList);
            listView.setAdapter(adapter);
        }

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupWeighingDialog dialog = new GroupWeighingDialog();
                dialog.show(getActivity().getFragmentManager(), "GroupWeighingDialog");
            }
        });

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndividualWeighingDialog dialog = new IndividualWeighingDialog();
                dialog.show(getActivity().getFragmentManager(), "IndividualWeighingDialog");
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
