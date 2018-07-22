package com.example.ina.nativepigdummy.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Activities.ViewBreederActivity;
import com.example.ina.nativepigdummy.Activities.ViewGrowerActivity;
import com.example.ina.nativepigdummy.Activities.ViewProfileActivity;
import com.example.ina.nativepigdummy.Adapters.FemaleGrowerDataAdapter;
import com.example.ina.nativepigdummy.Adapters.MaleGrowerDataAdapter;
import com.example.ina.nativepigdummy.Data.FemaleGrowerData;
import com.example.ina.nativepigdummy.Data.MaleGrowerData;
import com.example.ina.nativepigdummy.Data.SowData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class MaleGrowerFragment extends Fragment {

    DatabaseHelper myDB;
    ArrayList<MaleGrowerData> maleList;
    MaleGrowerData maleData;
    ListView nListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_male_grower, container, false);
        nListView = view.findViewById(R.id.listview_male_grower);

        myDB = new DatabaseHelper(getActivity());
        maleList = new ArrayList<>();
        Cursor data = myDB.getMaleGrowerContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                maleData = new MaleGrowerData(data.getString(0));
                maleList.add(i, maleData);
                System.out.println(data.getString(0));
                System.out.println(maleList.get(i).getMale_grower_reg_id());
            }

            MaleGrowerDataAdapter adapter = new MaleGrowerDataAdapter(getActivity(), R.layout.listview_breeder_grower, maleList);
            nListView.setAdapter(adapter);

        }

        nListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listview = maleList.get(i).getMale_grower_reg_id();
                Intent intent = new Intent(getActivity(), ViewGrowerActivity.class);
                intent.putExtra("ListClickValue", listview);
                startActivity(intent);
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
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
