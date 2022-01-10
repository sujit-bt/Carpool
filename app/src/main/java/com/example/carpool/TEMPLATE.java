package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TEMPLATE extends AppCompatActivity {

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;
    // Object that listens to any changes in the login/logout status
    FirebaseAuth.AuthStateListener mAuthStateListener;

    // Submit and logout buttons
    Button mSubmit, mLogout, mOrder;
    // Location EditTexts and Phone EditText
    EditText mCurrentLocation, mDestination, mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        mSubmit = (Button) findViewById(R.id.submit);
        mLogout = (Button) findViewById(R.id.logout);
        mOrder = (Button) findViewById(R.id.order);
        mCurrentLocation = (EditText) findViewById(R.id.currentLocation);
        mDestination = (EditText) findViewById(R.id.destination);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);


        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference passengers = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers");

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(TEMPLATE.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gets the email and password strings from the textviews
                final String currentLocation = mCurrentLocation.getText().toString();
                final String destination = mDestination.getText().toString();
                final String phoneNumber = mPhoneNumber.getText().toString();

                // if the fields are empty a warning will be sent out
                if (currentLocation.isEmpty() || destination.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(TEMPLATE.this, "Please don't enter blank text.", Toast.LENGTH_SHORT).show();
                } else {
                    final PersonInfo passengerInfo = new PersonInfo(currentLocation, destination, phoneNumber, false);
                    passengers.child(user_id).setValue(passengerInfo);
                }
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query unreachedPassenger = passengers.orderByChild("clocation");
                unreachedPassenger.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.e("childMoved", previousChildName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}