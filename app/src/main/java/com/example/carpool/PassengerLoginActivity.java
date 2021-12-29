package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A Class controlling the processes of passenger login screen of the app
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 *
 */
public class PassengerLoginActivity extends AppCompatActivity {

    // The email and password input fields
    TextView mEmail, mPassword;
    // Login, register, and back buttons
    Button mLogin, mRegister;

    // Email and password strings from the two textviews
    private String email, password;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;
    // Object that listens to any changes in the login/logout status
    FirebaseAuth.AuthStateListener mAuthStateListener;

    /**
     * This function is called when the DriverLoginActivity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_login);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        
        // Listens for any changes in login/logout status
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                /*
                only runs when user != null
                It won't run when user logs out and will run when user logs in
                */
                if (user != null) {
                    Intent intent = new Intent(PassengerLoginActivity.this, TEMPLATE.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        // Controls what happens when the register button is clicked
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // gets the email and password strings from the textviews
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                // if the fields are empty a warning will be sent out and user is not registered
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PassengerLoginActivity.this, "Please enter email and Password.", Toast.LENGTH_SHORT).show();
                } else {

                    // creates a user in firebase database
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(PassengerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers").child(user_id);
                                current_user.setValue(true);
                            } else {
                                Toast.makeText(PassengerLoginActivity.this, "FAILED TO REGISTER USER!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Controls what happens when the login button is clicked
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // gets the email and password strings from the textviews
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                // if the fields are empty a warning will be sent out
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PassengerLoginActivity.this, "Please enter email and Password.", Toast.LENGTH_SHORT).show();
                } else {

                    // logs in user in firebase database if the password and email matches to an existing user
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(PassengerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("suCCesSS");
                            } else {
                                Toast.makeText(PassengerLoginActivity.this, "FAILED TO LOGIN USER!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    /**
     * Controls the function of the back button
     */
    @Override
    public void onBackPressed() {
        // Switches the activity to the main activity
        Intent intent = new Intent(PassengerLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    /**
     * Method called when the activity starts, called after onCreate()
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    /**
     * Called before the activity is stopped
     */
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }
}