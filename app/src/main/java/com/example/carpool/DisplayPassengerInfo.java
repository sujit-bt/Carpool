package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayPassengerInfo extends AppCompatActivity {

    Button mToPassengerCurrentLocation, mToPassengerDestination, mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_passenger_info);

        mToPassengerCurrentLocation = (Button) findViewById(R.id.directionToPassengerCurrentLocation);
        mToPassengerDestination = (Button) findViewById(R.id.directionToPassengerDestination);
        mNext = (Button) findViewById(R.id.toDriverDestinationActivity);

        mToPassengerCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set Google Maps Intent
            }
        });

        mToPassengerDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set Google MAps Intent
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayPassengerInfo.this, DriverDestination.class);
                startActivity(intent);
                finish();
            }
        });
    }
}