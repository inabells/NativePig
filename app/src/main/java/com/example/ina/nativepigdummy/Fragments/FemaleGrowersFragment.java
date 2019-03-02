package com.example.ina.nativepigdummy.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.ViewBreederActivity;
import com.example.ina.nativepigdummy.Activities.ViewGrowerActivity;
import com.example.ina.nativepigdummy.Adapters.BoarDataAdapter;
import com.example.ina.nativepigdummy.Adapters.FemaleGrowerDataAdapter;
import com.example.ina.nativepigdummy.Adapters.SowDataAdapter;
import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.Data.FemaleGrowerData;
import com.example.ina.nativepigdummy.Data.SowData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FemaleGrowersFragment extends Fragment {

    DatabaseHelper dbHelper;
    ArrayList<FemaleGrowerData> femaleList;
    FemaleGrowerData femaleData;
    ListView nListView;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_female_growers, container, false);
        nListView = view.findViewById(R.id.listview_female_grower);
        dbHelper = new DatabaseHelper(getActivity());
        femaleList = new ArrayList<>();

        if(ApiHelper.isInternetAvailable(getContext())) {
            api_getFemaleGrowers();
        } else{
            local_getFemaleGrowers();
        }

        nListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listview = femaleList.get(i).getFemale_grower_reg_id();
                Intent intent = new Intent(getActivity(), ViewGrowerActivity.class);
                intent.putExtra("ListClickValue", listview);
                startActivity(intent);
            }
        });

        return view;
    }

    private void local_getFemaleGrowers() {
        Cursor data = dbHelper.getFemaleGrowerContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(getActivity(),"The database is empty.",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                femaleData = new FemaleGrowerData(data.getString(1));
                femaleList.add(femaleData);
            }
            FemaleGrowerDataAdapter adapter = new FemaleGrowerDataAdapter(getActivity(), R.layout.listview_breeder_grower, femaleList);
            nListView.setAdapter(adapter);
        }
    }

    private void api_getFemaleGrowers(){
        ApiHelper.getAllFemaleGrowers("getAllFemaleGrowers", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("API HANDLER Success", rawJsonResponse);
                FemaleGrowerDataAdapter adapter = new FemaleGrowerDataAdapter(getActivity(), R.layout.listview_breeder_grower, femaleList);
                nListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(), "Error in parsing data", Toast.LENGTH_SHORT).show();
                Log.d("API HANDLER FAIL", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    femaleList.add(new FemaleGrowerData(jsonObject.getString("pig_registration_id")));
                }
                return null;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
