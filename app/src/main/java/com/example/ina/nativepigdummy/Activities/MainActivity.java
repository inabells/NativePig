package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Adapters.MortalityDataAdapter;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle toggle_drawer;
    private Toolbar tool_bar;
    private NavigationView navigation_view;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView noOfBoars;
    TextView noOfFemaleGrowers;
    TextView noOfMaleGrowers;
    TextView noOfSows, noOfGilts, noOfBreeders, noOfGrowers, noOfMortality;
    String giltNum, breederNum, growerNum, mortalityNum, sowNum, boarNum, femaleGrowerNum, maleGrowerNum = "-";
    CardView rellay_sows, rellay_gilts, rellay_boars, rellay_female_growers, rellay_male_growers, rellay_breeders, rellay_growers, rellay_mortality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        noOfSows = findViewById(R.id.noOfSows);
        noOfBoars = findViewById(R.id.noOfBoars);
        noOfFemaleGrowers = findViewById(R.id.noOfFemaleGrowers);
        noOfMaleGrowers = findViewById(R.id.noOfMaleGrowers);
        noOfBreeders = findViewById(R.id.noOfBreeders);
        noOfGilts = findViewById(R.id.noOfGilts);
        noOfMortality = findViewById(R.id.noOfMortality);
        noOfGrowers = findViewById(R.id.noOfGrowers);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        getLogInCredentials();
        if(ApiHelper.isInternetAvailable(getApplicationContext())){
            if(dbHelper.syncAllTablesFromLocalToServer()){
                dbHelper.clearLocalDatabases();
                dbHelper.getAllDataFromServer();
                Toast.makeText(MainActivity.this, "Local Data Added to Server", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Error in Adding Local Data to Server", Toast.LENGTH_SHORT).show();
            }
            api_getAllCount();
        } else{
            setLocalCount(dbHelper.local_getAllCount());
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLogInCredentials();
                if(ApiHelper.isInternetAvailable(getApplicationContext())){
                    if(dbHelper.syncAllTablesFromLocalToServer()){
                        dbHelper.clearLocalDatabases();
                        dbHelper.getAllDataFromServer();
                        Toast.makeText(MainActivity.this, "Local Data Added to Server", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Error in Adding Local Data to Server", Toast.LENGTH_SHORT).show();
                    }

                    api_getAllCount();
                } else{
                    setLocalCount(dbHelper.local_getAllCount());
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 15000);
            }
        });

        Calendar calendar = Calendar.getInstance();
        String currentDate =  DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textView_dateToday);
        textViewDate.setText(currentDate);

        rellay_sows = findViewById(R.id.rellay_sows);
        rellay_gilts = findViewById(R.id.rellay_gilts);
        rellay_boars = findViewById(R.id.rellay_boars);
        rellay_female_growers = findViewById(R.id.rellay_female);
        rellay_male_growers = findViewById(R.id.rellay_male);
        rellay_breeders = findViewById(R.id.rellay_breeders);
        rellay_growers = findViewById(R.id.rellay_growers);
        rellay_mortality = findViewById(R.id.rellay_mortality);

        rellay_sows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BreederRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_gilts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BreederRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_boars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BreederRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_breeders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BreederRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_female_growers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GrowerRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_male_growers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GrowerRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_growers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GrowerRecordsActivity.class);
                startActivity(intent);
            }
        });

        rellay_mortality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MortalityAndSalesActivity.class);
                startActivity(intent);
            }
        });

        navigation_view = findViewById(R.id.nav_view);

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch(item.getItemId()){
                    case R.id.nav_dashboard:
                        finish();
                        startActivity(getIntent());
                        break;
                    case R.id.nav_addnewpig:
                        Intent intent_addnewpig = new Intent(MainActivity.this, AddNewPigActivity.class);
                        startActivity(intent_addnewpig);
                        break;
                    case R.id.nav_breederrecords:
                        Intent intent_breederrecords = new Intent(MainActivity.this, BreederRecordsActivity.class);
                        startActivity(intent_breederrecords);
                        break;
                    case R.id.nav_breedingrecords:
                        Intent intent_breedingrecords = new Intent(MainActivity.this, BreedingRecordsActivity.class);
                        startActivity(intent_breedingrecords);
                        break;
                    case R.id.nav_growerrecords:
                        Intent intent_growerrecords = new Intent(MainActivity.this, GrowerRecordsActivity.class);
                        startActivity(intent_growerrecords);
                        break;
                    case R.id.nav_mortalityandsales:
                        Intent intent_mortalityandsales = new Intent(MainActivity.this, MortalityAndSalesActivity.class);
                        startActivity(intent_mortalityandsales);
                        break;
                    //EDITED//START
                    case R.id.nav_logout:
                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        GoogleSignInClient mGoogleSignInClient;
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

                        // Google sign out
                        mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this,
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Firebase sign out
                                        mAuth.signOut();

                                        Intent intent_signout = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent_signout);
                                    }
                                });
                        break;
                    //EDITED//END
                    case R.id.nav_profile:
                        Intent intent_viewprofile = new Intent(MainActivity.this, ViewProfileActivity.class);
                        startActivity(intent_viewprofile);
                        break;


                }
                return true;
            }

        });

        tool_bar = findViewById(R.id.nav_action);
        setSupportActionBar(tool_bar);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle_drawer = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.closed);

        drawer_layout.addDrawerListener(toggle_drawer);
        toggle_drawer.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dashboard");
    }

    private void getLogInCredentials() {
        Cursor data = dbHelper.getLoggedInUserTable();
        if(data.moveToLast()){
            MyApplication.id = data.getInt(data.getColumnIndex("user_id"));
            MyApplication.email = data.getString(data.getColumnIndex("user_email"));
            MyApplication.name = data.getString(data.getColumnIndex("user_name"));
        }
    }

    private void setLocalCount(HashMap<String, Integer> map) {
        sowNum = Integer.toString(map.get("sowCount"));
        boarNum = Integer.toString(map.get("boarCount"));
        femaleGrowerNum = Integer.toString(map.get("femaleGrowerCount"));
        maleGrowerNum = Integer.toString(map.get("maleGrowerCount"));
        giltNum = Integer.toString(map.get("giltCount"));
        breederNum = Integer.toString(map.get("breederInventory"));
        growerNum = Integer.toString(map.get("growerInventory"));
        mortalityNum = Integer.toString(map.get("mortalityInventory"));

        noOfSows.setText(sowNum);
        noOfBoars.setText(boarNum);
        noOfFemaleGrowers.setText(femaleGrowerNum);
        noOfMaleGrowers.setText(maleGrowerNum);
        noOfGilts.setText(giltNum);
        noOfGrowers.setText(growerNum);
        noOfMortality.setText(mortalityNum);
        noOfBreeders.setText(breederNum);
    }

    private void api_getAllCount() {
        RequestParams requestParams = new RequestParams();
        requestParams.add("farmable_id", Integer.toString(MyApplication.id));
        requestParams.add("breedable_id", Integer.toString(MyApplication.id));

        ApiHelper.getAllCount("getAllCount", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                noOfSows.setText(sowNum);
                noOfBoars.setText(boarNum);
                noOfFemaleGrowers.setText(femaleGrowerNum);
                noOfMaleGrowers.setText(maleGrowerNum);
                noOfGilts.setText(giltNum);
                noOfGrowers.setText(growerNum);
                noOfMortality.setText(mortalityNum);
                noOfBreeders.setText(breederNum);
                Log.d("getAllCount", "Succesfully fetched count");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getAllCount", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                sowNum = jsonObject.get("sowCount").toString();
                boarNum = jsonObject.get("boarCount").toString();
                femaleGrowerNum = jsonObject.get("femaleGrowerCount").toString();
                maleGrowerNum = jsonObject.get("maleGrowerCount").toString();
                giltNum = jsonObject.get("giltCount").toString();
                breederNum = jsonObject.get("breederInventory").toString();
                growerNum = jsonObject.get("growerInventory").toString();
                mortalityNum = jsonObject.get("mortalityInventory").toString();
                return null;
            }
        });
    }

//    private void setFragment(Fragment fragment){
//        FragmentTransaction fragment_transaction = getSupportFragmentManager().beginTransaction();
//        fragment_transaction.replace(R.id.main_frame, fragment);
//        fragment_transaction.commit();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle_drawer.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
