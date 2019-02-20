package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ina.nativepigdummy.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle toggle_drawer;
    private Toolbar tool_bar;
    private NavigationView navigation_view;
    RelativeLayout rellay_sows, rellay_boars, rellay_female_growers, rellay_male_growers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView noOfSows = findViewById(R.id.noOfSows);
        TextView noOfBoars = findViewById(R.id.noOfBoars);
        TextView noOfFemaleGrowers = findViewById(R.id.noOfFemaleGrowers);
        TextView noOfMaleGrowers = findViewById(R.id.noOfMaleGrowers);

        Calendar calendar = Calendar.getInstance();
        String currentDate =  DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textView_dateToday);
        textViewDate.setText(currentDate);

        rellay_sows = findViewById(R.id.rellay_sows);
        rellay_boars = findViewById(R.id.rellay_boars);
        rellay_female_growers = findViewById(R.id.rellay_female);
        rellay_male_growers = findViewById(R.id.rellay_male);

        rellay_sows.setOnClickListener(new View.OnClickListener() {
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

    private void setFragment(Fragment fragment){
        FragmentTransaction fragment_transaction = getSupportFragmentManager().beginTransaction();
        fragment_transaction.replace(R.id.main_frame, fragment);
        fragment_transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle_drawer.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
