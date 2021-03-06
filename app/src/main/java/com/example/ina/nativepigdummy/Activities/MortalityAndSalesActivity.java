package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MortalityAndSalesActivity extends AppCompatActivity {

    FragmentTransaction fragment_transaction;

    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle toggle_drawer;
    private Toolbar tool_bar;
    private NavigationView navigation_view;
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private PagerAdapter page_adapter;
    private DatabaseHelper dbHelper;
    private TabItem tab_mortality;
    private TabItem tab_sales;
    private TabItem tab_others;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortality_and_sales);
        dbHelper = new DatabaseHelper(this);

        navigation_view = findViewById(R.id.nav_view);
        tab_layout = findViewById(R.id.tab_layout);
        tab_mortality = findViewById(R.id.tab_mortality);
        tab_sales = findViewById(R.id.tab_sales);
        tab_others = findViewById(R.id.tab_others);
        view_pager = findViewById(R.id.view_pager);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        page_adapter = new MortalityAndSalesPageAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        view_pager.setAdapter(page_adapter);


        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==1){
                    tool_bar.setBackgroundColor(ContextCompat.getColor(MortalityAndSalesActivity.this, R.color.colorPrimary));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(MortalityAndSalesActivity.this, R.color.colorPrimary));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(MortalityAndSalesActivity.this, R.color.colorPrimary));
                    }
                } else {
                    tool_bar.setBackgroundColor(ContextCompat.getColor(MortalityAndSalesActivity.this, R.color.colorPrimary));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(MortalityAndSalesActivity.this, R.color.colorPrimary));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(MortalityAndSalesActivity.this, R.color.colorPrimary));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if(ApiHelper.isInternetAvailable(getApplicationContext())){
//                    if(dbHelper.syncAllTablesFromLocalToServer()){
//                        dbHelper.clearLocalDatabases();
//                        dbHelper.getAllDataFromServer();
//                        Toast.makeText(MortalityAndSalesActivity.this, "Local Data Added to Server", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(MortalityAndSalesActivity.this, "Error in Adding Local Data to Server", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(MortalityAndSalesActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch(item.getItemId()){
                    case R.id.nav_dashboard:
                        Intent intent_dashboard = new Intent(MortalityAndSalesActivity.this, MainActivity.class);
                        startActivity(intent_dashboard);
                        break;
                    case R.id.nav_addnewpig:
                        Intent intent_addnewpig = new Intent(MortalityAndSalesActivity.this, AddNewPigActivity.class);
                        startActivity(intent_addnewpig);
                        break;
                    case R.id.nav_breederrecords:
                        Intent intent_breederrecords = new Intent(MortalityAndSalesActivity.this, BreederRecordsActivity.class);
                        startActivity(intent_breederrecords);
                        break;
                    case R.id.nav_breedingrecords:
                        Intent intent_breedingrecords = new Intent(MortalityAndSalesActivity.this, BreedingRecordsActivity.class);
                        startActivity(intent_breedingrecords);
                        break;
                    case R.id.nav_growerrecords:
                        Intent intent_growerrecords = new Intent(MortalityAndSalesActivity.this, GrowerRecordsActivity.class);
                        startActivity(intent_growerrecords);
                        break;
                    case R.id.nav_mortalityandsales:
                        finish();
                        startActivity(getIntent());
                        break;
                    case R.id.nav_profile:
                        Intent intent_viewprofile = new Intent(MortalityAndSalesActivity.this, ViewProfileActivity.class);
                        startActivity(intent_viewprofile);
                        break;
                    //EDITED//START
                    case R.id.nav_logout:
                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        GoogleSignInClient mGoogleSignInClient;
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(MortalityAndSalesActivity.this, gso);

                        // Google sign out
                        mGoogleSignInClient.signOut().addOnCompleteListener(MortalityAndSalesActivity.this,
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Firebase sign out
                                        mAuth.signOut();

                                        Intent intent_signout = new Intent(MortalityAndSalesActivity.this, LoginActivity.class);
                                        startActivity(intent_signout);
                                    }
                                });
                        break;
                    //EDITED//END
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
        getSupportActionBar().setTitle("Mortality and Sales");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle_drawer.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public MortalityAndSalesActivity() {
    }
}
