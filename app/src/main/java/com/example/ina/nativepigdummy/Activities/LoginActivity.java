package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean loggedInFlag = false;
    String editName, editEmail, editId;
    DatabaseHelper dbHelper;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        dbHelper = new DatabaseHelper(getApplicationContext());
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        //check if user is logged in
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()){
        if(mAuth.getCurrentUser() != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String logInEmail = currentUser.getEmail();
            Log.d("Google LogIn onCreate", "Currently logged in as "+logInEmail);
            loggedInFlag = true;

            //proceed to intent
            Intent startDashboard = new Intent(this, MainActivity.class);
            startActivity(startDashboard);
        } else{
            Log.d("Google LogIn onCreate", "Not logged in");
        }
    }

    public void onClickLogin(View view){
        if(!loggedInFlag) {
            Log.d("Google onClickLogin", "loggedInFlag = false");
            signIn();
        }
        if(loggedInFlag) {
            Log.d("Google onClickLogin", "loggedInFlag = true");
            Intent startDashboard = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(startDashboard);
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loggedInFlag = true;
            firebaseAuthWithGoogle(account);

            RequestParams params = new RequestParams();
            params.add("email", account.getEmail());

            api_checkEmailInDb(params);
            dbHelper.clearLoggedInUserTable();
            local_insertToLoggedInUserDb(account.getEmail());

            //proceed to dashboard
            Log.d("Google hndlSignInResult", "Proceed to Intent");
            Intent startDashboard = new Intent(this, MainActivity.class);
            startActivity(startDashboard);

            // Signed in successfully, show UI change

        } catch (ApiException e) {
            Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode());
//            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void local_insertToLoggedInUserDb(String accountEmail) {
        if(accountEmail.equals("baibppig@gmail.com")){
            MyApplication.id = 1;
            MyApplication.name = "BAI";
        }else if(accountEmail.equals("benguetpig@gmai.com")){
            MyApplication.id = 2;
            MyApplication.name = "BSU";
        }else if(accountEmail.equals("siniranganpig@gmail.com")){
            MyApplication.id = 3;
            MyApplication.name = "ESSU";
        }else if(accountEmail.equals("berkjalapig@gmail.com")){
            MyApplication.id = 4;
            MyApplication.name = "IAS";
        }else if(accountEmail.equals("isabelaisupig@gmail.com")){
            MyApplication.id = 5;
            MyApplication.name = "ISU";
        }else if(accountEmail.equals("yookahpig@gmail.com")){
            MyApplication.id = 6;
            MyApplication.name = "KSU";
        }else if(accountEmail.equals("marindukepig@gmail.com")){
            MyApplication.id = 7;
            MyApplication.name = "MSC";
        }else if(accountEmail.equals("nuevaviscayapig@gmail.com")){
            MyApplication.id = 8;
            MyApplication.name = "NVSU";
        }else if(accountEmail.equals("marmscpig@gmail.com")){
            MyApplication.id = 9;
            MyApplication.name = "MSC2";
        }else if(accountEmail.equals("inalagmaaan@gmail.com")){
            MyApplication.id = 10;
            MyApplication.name = "PBL";
        }

        MyApplication.email = accountEmail;
        boolean insertData = dbHelper.insertToLoggedInUserTable(MyApplication.id, MyApplication.name, accountEmail);

        if(insertData)
            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(LoginActivity.this, "Error in adding log in credentials", Toast.LENGTH_SHORT).show();
    }

    private void api_checkEmailInDb(RequestParams params) throws JSONException {
        ApiHelper.getEmail("getEmail", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getEmail", "Success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getEmail", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONObject jsonObjectUser = jsonObject.getJSONObject("user");
                    MyApplication.id = jsonObjectUser.getInt("id");
                    MyApplication.name = jsonObjectUser.getString("name");
                    MyApplication.email = jsonObjectUser.getString("email");
                }
                return null;
            }
        });
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //update ui
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthCredential", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        loggedInFlag = false;

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //update UI
                    }
                });
    }
}

