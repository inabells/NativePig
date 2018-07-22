package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.DateDialog;
import com.example.ina.nativepigdummy.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class AddNewPigActivity extends AppCompatActivity {

    DatabaseHelper addnewpigDB;
    private DrawerLayout drawer_layout;
    private ImageView imageView;
    private ActionBarDrawerToggle toggle_drawer;
    private Toolbar tool_bar;
    private NavigationView navigation_view;
    private EditText addAnimalEarnotch, addBirthDate, addWeanDate, addBirthWeight, addWeanWeight, addMotherEarnotch, addFatherEarnotch;
    private Spinner addSex;
    private Button addButton;
    private Bitmap bitmap_foto;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pig);

        addnewpigDB = new DatabaseHelper(this);

        navigation_view = findViewById(R.id.nav_view);
        imageView = findViewById(R.id.user_image);
        addAnimalEarnotch = findViewById(R.id.animal_earnotch);
        addSex = findViewById(R.id.gender);
        addBirthDate = findViewById(R.id.birth_date);
        addWeanDate = findViewById(R.id.weaning_date);
        addBirthWeight = findViewById(R.id.birth_weight);
        addWeanWeight = findViewById(R.id.weaning_weight);
        addMotherEarnotch = findViewById(R.id.motherearnotch);
        addFatherEarnotch = findViewById(R.id.fatherearnotch);
        addButton = findViewById(R.id.add_button);
        bitmap_foto = BitmapFactory.decodeResource(getResources(),R.drawable.image);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap_foto);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);

        createRadioButtons();

        addSex.setPrompt("Select Gender");
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddNewPigActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sex));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSex.setAdapter(myAdapter);


        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                        Intent intent_dashboard = new Intent(AddNewPigActivity.this, MainActivity.class);
                        startActivity(intent_dashboard);
                        break;
                    case R.id.nav_addnewpig:
                        finish();
                        startActivity(getIntent());
                        break;
                    case R.id.nav_breederrecords:
                        Intent intent_breederrecords = new Intent(AddNewPigActivity.this, BreederRecordsActivity.class);
                        startActivity(intent_breederrecords);
                        break;
                    case R.id.nav_breedingrecords:
                        Intent intent_breedingrecords = new Intent(AddNewPigActivity.this, BreedingRecordsActivity.class);
                        startActivity(intent_breedingrecords);
                        break;
                    case R.id.nav_growerrecords:
                        Intent intent_growerrecords = new Intent(AddNewPigActivity.this, GrowerRecordsActivity.class);
                        startActivity(intent_growerrecords);
                        break;
                    case R.id.nav_mortalityandsales:
                        Intent intent_mortalityandsales = new Intent(AddNewPigActivity.this, MortalityAndSalesActivity.class);
                        startActivity(intent_mortalityandsales);
                        break;
                    case R.id.nav_profile:
                        Intent intent_viewprofile = new Intent(AddNewPigActivity.this, ViewProfileActivity.class);
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
                        mGoogleSignInClient = GoogleSignIn.getClient(AddNewPigActivity.this, gso);

                        // Google sign out
                        mGoogleSignInClient.signOut().addOnCompleteListener(AddNewPigActivity.this,
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Firebase sign out
                                        mAuth.signOut();

                                        Intent intent_signout = new Intent(AddNewPigActivity.this, LoginActivity.class);
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
        getSupportActionBar().setTitle("Add New Pig");

        addPig();
    }

    private void createRadioButtons(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.newpig_classification);
        String[] new_classification = getResources().getStringArray(R.array.classification);

        for(int i = 0;  i < new_classification.length; i++){
            String breederORgrower = new_classification[i];

            RadioButton button = new RadioButton(this);
            button.setText(breederORgrower);

            radioGroup.addView(button);
        }
    }


    private void requestFocus (View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onStart(){
        super.onStart();
        EditText birthDate = findViewById(R.id.birth_date);
        birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                    dialog.show(ft,"Birth Date");
                }
            }
        });

        EditText weanDate = findViewById(R.id.weaning_date);
        weanDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                    dialog.show(ft,"Weaning Date");
                }
            }
        });
    }

    public void addPig(){
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RadioGroup group = findViewById(R.id.newpig_classification);
                int idofselected = group.getCheckedRadioButtonId();
                RadioButton radiobutton = findViewById(idofselected);

                String classification = radiobutton.getText().toString();
                String animalearnotch = addAnimalEarnotch.getText().toString();
                String sex = addSex.getSelectedItem().toString();
                String birthdate = addBirthDate.getText().toString();
                String weandate = addWeanDate.getText().toString();
                String birthweight = addBirthWeight.getText().toString();
                String weanweight = addWeanWeight.getText().toString();
                String motherearnotch = addMotherEarnotch.getText().toString();
                String fatherearnotch = addFatherEarnotch.getText().toString();


                if(animalearnotch.equals("") && birthdate.equals("")){
                    Toast.makeText(AddNewPigActivity.this, "Please fill out animal earnotch and birthdate", Toast.LENGTH_SHORT).show();
                }else{
                    boolean insertData = addnewpigDB.addNewPigData(classification, animalearnotch, sex, birthdate, weandate, birthweight, weanweight, motherearnotch, fatherearnotch);
                    if(insertData == true) Toast.makeText(AddNewPigActivity.this, "Data successfully inserted", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(AddNewPigActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

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




    public AddNewPigActivity() {
    }
}

