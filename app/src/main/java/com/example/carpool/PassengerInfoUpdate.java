package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerInfoUpdate extends AppCompatActivity {

    Button mSubmit;
    EditText mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info_update);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference passengers = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers");

        mSubmit = (Button) findViewById(R.id.updatePassengerInfo);
        mCurrentLocation = (EditText) findViewById(R.id.passengerInputtedCurrentLocation);
        mDestination = (EditText) findViewById(R.id.passengerInputtedDestination);
        mPhone = (EditText) findViewById(R.id.passengerInputtedPhoneNumber);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentLocation = mCurrentLocation.getText().toString();
                final String destination = mDestination.getText().toString();
                final String phone = mPhone.getText().toString();

                if (currentLocation.isEmpty() || destination.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(PassengerInfoUpdate.this, "Please do not leave the form blank!", Toast.LENGTH_SHORT).show();
                } else {
                    passengers.child(user_id).setValue(new PersonInfo(currentLocation, destination, phone));
                    Intent intent = new Intent(PassengerInfoUpdate.this, PassengerDriverInfoDisplay.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}