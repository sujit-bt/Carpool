package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PassengerInfoUpdate extends AppCompatActivity {

    Button mSubmit, mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info_update);

        mSubmit = (Button) findViewById(R.id.updatePassengerInfo);
        mNext = (Button) findViewById(R.id.toDriverInfoDisplay);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Submit to online
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassengerInfoUpdate.this, DriverInfoDisplay.class);
                startActivity(intent);
                finish();
            }
        });
    }
}