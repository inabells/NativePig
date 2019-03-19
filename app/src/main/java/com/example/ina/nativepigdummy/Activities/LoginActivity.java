package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import java.util.Optional;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean loggedInFlag = false;
    DatabaseHelper dbHelper;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            Cursor cursor = dbHelper.getEmailInLocalDb(mAuth.getCurrentUser().getEmail());
            while(cursor.moveToNext()){
                MyApplication.id = cursor.getInt(cursor.getColumnIndex("id"));
                MyApplication.name = cursor.getString(cursor.getColumnIndex("name"));
                MyApplication.email = cursor.getString(cursor.getColumnIndex("email"));
            }

            //proceed to dashboard
            Log.d("Google hndlSignInResult", "Proceed to Intent");
            Intent startDashboard = new Intent(this, MainActivity.class);
            startActivity(startDashboard);

            // Signed in successfully, show UI change

        } catch (ApiException e) {
            Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode());
//            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
        }
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

