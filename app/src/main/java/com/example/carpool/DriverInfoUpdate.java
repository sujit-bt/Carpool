package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class DriverInfoUpdate extends AppCompatActivity {

    Button mSubmit;
    EditText mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info_update);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);

        mCurrentLocation = (EditText) findViewById(R.id.driverInputtedCurrentLocation);
        mDestination = (EditText) findViewById(R.id.driverInputtedDestination);
        mPhone = (EditText) findViewById(R.id.driverInputtedPhoneNumber);
        mSubmit = (Button) findViewById(R.id.updateDriverInfo);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentLocation = mCurrentLocation.getText().toString();
                final String destination = mDestination.getText().toString();
                final String phone = mPhone.getText().toString();

                if (currentLocation.isEmpty() || destination.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(DriverInfoUpdate.this, "Please do not leave the form blank!", Toast.LENGTH_SHORT).show();
                } else {
                    user.setValue(new PersonInfo(currentLocation, destination, phone));

                    Intent intent = new Intent(DriverInfoUpdate.this, DriverPassengerInfoDisplay.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}