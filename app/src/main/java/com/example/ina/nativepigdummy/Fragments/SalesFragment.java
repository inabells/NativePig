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
import com.example.ina.nativepigdummy.Adapters.SalesDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.Data.SalesData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.MortalityDialog;
import com.example.ina.nativepigdummy.Dialog.SalesDialog;
import com.example.ina.nativepigdummy.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class SalesFragment extends Fragment {

    ListView listView;
    private FloatingActionButton sales;
    DatabaseHelper myDB;
    ArrayList<SalesData> salesList;
    SalesData salesData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_sales, container, false);
        listView = view.findViewById(R.id.listview_sales);
        sales = view.findViewById(R.id.floating_action_sales);

        myDB = new DatabaseHelper(getActivity());

        salesList = new ArrayList<>();
        Cursor data  = myDB.getSalesContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                salesData = new SalesData(data.getString(1), data.getString(2),data.getString(3), data.getString(4));
                salesList.add(i, salesData);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4));
                System.out.println(salesList.get(i).getSales_reg_id());
                i++;
            }
            SalesDataAdapter adapter = new SalesDataAdapter(getActivity(), R.layout.listview_mortality_sales_others, salesList);
            listView.setAdapter(adapter);
        }

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalesDialog dialog = new SalesDialog();
                dialog.show(getFragmentManager(), "SalesDialog");
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
