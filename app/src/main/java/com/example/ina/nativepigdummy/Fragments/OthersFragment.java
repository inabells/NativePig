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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Adapters.OthersDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.Dialog.OthersDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class OthersFragment extends Fragment {

    ListView listView;
    private FloatingActionButton others;
    DatabaseHelper myDB;
    ArrayList<OthersData> otherList;
    OthersData othersData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_others, container, false);
        listView = view.findViewById(R.id.listview_others);
        others = view.findViewById(R.id.floating_action_others);

        myDB = new DatabaseHelper(getActivity());

        otherList = new ArrayList<>();
        Cursor data  = myDB.getOthersContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                othersData = new OthersData(data.getString(1), data.getString(2),data.getString(3), data.getString(4));
                otherList.add(i, othersData);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4));
                System.out.println(otherList.get(i).getOthers_reg_id());
                i++;
            }
            OthersDataAdapter adapter = new OthersDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, otherList);
            listView.setAdapter(adapter);
        }

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OthersDialog dialog = new OthersDialog();
                dialog.show(getFragmentManager(), "OthersDialog");
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
