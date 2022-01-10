package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverInfoUpdate extends AppCompatActivity {

    Button mSubmit, mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info_update);

        mSubmit = (Button) findViewById(R.id.updateDriverInfo);
        mNext = (Button) findViewById(R.id.toDisplayPassengerInfo);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Submit info
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverInfoUpdate.this,DisplayPassengerInfo.class);
                startActivity(intent);
                finish();
            }
        });
    }
}