package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverDestination extends AppCompatActivity {

    Button mDirection, mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_destination);

        mDirection = (Button) findViewById(R.id.direction);
        mLogout = (Button) findViewById(R.id.logout);

        mDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Google Maps INTENT
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Logout feature
            }
        });

    }
}