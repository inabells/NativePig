package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.ViewProfileDialog;
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

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class ViewProfileActivity extends AppCompatActivity implements ViewProfileDialog.ViewProfileListener{

    FragmentTransaction fragment_transaction;

    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle toggle_drawer;
    private Toolbar tool_bar;
    private NavigationView navigation_view;
    private FirebaseAuth mAuth;
    private DatabaseHelper dbHelper;

    private TextView textViewFarmName;
    private TextView textViewContactNo;
    private TextView textViewRegion;
    private TextView textViewProvince;
    private TextView textViewTown;
    private TextView textViewBarangay;
    private TextView textViewEmailAdd;
    private TextView textViewFarmId;
    private TextView textViewBreed;

    private String editFarmName, editContactNo, editRegion, editProvince, editTown, editBarangay, editFarmId, editBreed;

    private ImageView edit_profile;
    private ImageView imageView;
    private Bitmap bitmap_foto;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        textViewEmailAdd = findViewById(R.id.textViewEmaiAdd);
        textViewFarmName = (TextView) findViewById(R.id.farm);
        textViewContactNo = (TextView) findViewById(R.id.textViewNumber);
        textViewRegion = (TextView) findViewById(R.id.textViewRegion);
        textViewProvince = (TextView) findViewById(R.id.textViewProvince);
        textViewTown = (TextView) findViewById(R.id.textViewTown);
        textViewBarangay = (TextView) findViewById(R.id.textViewBarangay);
        textViewBreed = findViewById(R.id.textViewBreed);
        textViewFarmId = findViewById(R.id.textViewFarmID);

        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper(this);

        imageView = findViewById(R.id.user_image);
        bitmap_foto = BitmapFactory.decodeResource(getResources(),R.drawable.image);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap_foto);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);


        textViewEmailAdd.setText(mAuth.getCurrentUser().getEmail());

        navigation_view = findViewById(R.id.nav_view);
        edit_profile = findViewById(R.id.edit_profile);

        RequestParams params = new RequestParams();
        params.add("farmable_id", Integer.toString(MyApplication.id));
        params.add("breedable_id", Integer.toString(MyApplication.id));

        if(ApiHelper.isInternetAvailable(getApplicationContext()))
            api_getFarmProfile(params);
        else
            local_getFarmProfile();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                if(Build.VERSION.SDK_INT < 19){
                    i = new Intent();
                    i.setAction(Intent.ACTION_GET_CONTENT);
                }else{
                    i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                }
                i.setType("image/*");
                startActivityForResult(i, 1);
            }
        });

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch(item.getItemId()){
                    case R.id.nav_dashboard:
                        Intent intent_dashboard = new Intent(ViewProfileActivity.this, MainActivity.class);
                        startActivity(intent_dashboard);
                        break;
                    case R.id.nav_addnewpig:
                        Intent intent_addnewpig = new Intent(ViewProfileActivity.this, AddNewPigActivity.class);
                        startActivity(intent_addnewpig);
                        break;
                    case R.id.nav_breederrecords:
                        Intent intent_breederrecords = new Intent(ViewProfileActivity.this, BreederRecordsActivity.class);
                        startActivity(intent_breederrecords);
                        break;
                    case R.id.nav_breedingrecords:
                        Intent intent_breedingrecords = new Intent(ViewProfileActivity.this, BreedingRecordsActivity.class);
                        startActivity(intent_breedingrecords);
                        break;
                    case R.id.nav_growerrecords:
                        Intent intent_growerrecords = new Intent(ViewProfileActivity.this, GrowerRecordsActivity.class);
                        startActivity(intent_growerrecords);
                        break;
                    case R.id.nav_mortalityandsales:
                        Intent intent_mortalityandsales = new Intent(ViewProfileActivity.this, MortalityAndSalesActivity.class);
                        startActivity(intent_mortalityandsales);
                        break;
                    case R.id.nav_profile:
                        finish();
                        startActivity(getIntent());
                        break;
                    //EDITED//START
                    case R.id.nav_logout:
                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        GoogleSignInClient mGoogleSignInClient;
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(ViewProfileActivity.this, gso);

                        // Google sign out
                        mGoogleSignInClient.signOut().addOnCompleteListener(ViewProfileActivity.this,
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Firebase sign out
                                        mAuth.signOut();

                                        Intent intent_signout = new Intent(ViewProfileActivity.this, LoginActivity.class);
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
        getSupportActionBar().setTitle("Profile");

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewProfileDialog();
            }
        });
    }

    private void local_getFarmProfile() {
        Cursor dataFromFarm = dbHelper.getFarmProfile(MyApplication.id);
        if(dataFromFarm.moveToFirst()) {
            textViewRegion.setText(dataFromFarm.getString(dataFromFarm.getColumnIndex("region")));
//            textViewContactNo.setText(dataFromFarm.getString(dataFromFarm.getColumnIndex("contactno")));
            textViewTown.setText(dataFromFarm.getString(dataFromFarm.getColumnIndex("town")));
            textViewBarangay.setText(dataFromFarm.getString(dataFromFarm.getColumnIndex("barangay")));
        }

        Cursor dataFromUsers = dbHelper.getUserProfile(MyApplication.id);
        if(dataFromUsers.moveToFirst()){
//            textViewEmailAdd.setText(dataFromFarm.getString(dataFromFarm.getColumnIndex("email")));
            textViewContactNo.setText(dataFromFarm.getString(dataFromFarm.getColumnIndex("phone")));
        }
    }


    public void openViewProfileDialog(){
        ViewProfileDialog viewProfileDialog = new ViewProfileDialog();
        viewProfileDialog.show(getSupportFragmentManager(),"ViewProfileDialog");
    }

    private void api_getFarmProfile(RequestParams params) {
        ApiHelper.getFarmProfilePage("getFarmProfilePage", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                textViewRegion.setText(editRegion);
                textViewBarangay.setText(editBarangay);
                textViewContactNo.setText(editContactNo);
                textViewTown.setText(editTown);
                textViewFarmName.setText(editFarmName);
                textViewFarmId.setText(editFarmId);
                textViewBreed.setText(editBreed);
                textViewProvince.setText(editProvince);
                Log.d("getFarmProfile", "Successfully fetched count");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getFarmProfile", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONObject jsonObjectFarm = jsonObject.getJSONObject("farm");
                    JSONObject jsonObjectBreed = jsonObject.getJSONObject("breed");
                    JSONObject jsonObjectUser = jsonObject.getJSONObject("user");
                    editContactNo = jsonObjectFarm.getString("phone");
                    editTown = jsonObjectBreed.getString("town");
                    editBarangay = jsonObjectBreed.getString("barangay");
                    editRegion = jsonObjectBreed.getString("region");
                    editFarmName = jsonObjectBreed.getString("name");
                    editBreed = jsonObjectUser.getString("breed");
                    editFarmId = jsonObjectBreed.getString("code");
                    editProvince = jsonObjectBreed.getString("province");
                }
                return null;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle_drawer.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private byte[] imageToByte(ImageView image) {
        Bitmap bitmapFoto = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapFoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == 1){
            imageView.setImageURI(data.getData());
            bytes = imageToByte(imageView);

            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void applyFarmName(String farmname){ textViewFarmName.setText(farmname); }

    @Override public void applyContactNo(String contactno){ textViewContactNo.setText(contactno); }

    @Override public void applyRegion(String region){ textViewRegion.setText(region); }

    @Override public void applyProvince(String province){ textViewProvince.setText(province); }

    @Override public void applyTown(String town){ textViewTown.setText(town); }

    @Override public void applyBarangay(String barangay){ textViewBarangay.setText(barangay); }


    public ViewProfileActivity() {
    }

}
