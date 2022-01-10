package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverInfoDisplay extends AppCompatActivity {

    Button mRefresh, mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info_display);

        mRefresh = (Button) findViewById(R.id.refresh);
        mLogout = (Button) findViewById(R.id.passengerLogout);

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO connect passenger with driver
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Logout functionality
            }
        });

    }
}