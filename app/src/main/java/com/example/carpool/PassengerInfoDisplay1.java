package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PassengerInfoDisplay1 extends AppCompatActivity {

    Button mDirection, mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info_display1);

        mDirection = (Button) findViewById(R.id.direction);
        mNext = (Button) findViewById(R.id.next);

        mDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Opem Maps Intent
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassengerInfoDisplay1.this, PassengerInfoDisplay2.class);
            }
        });
    }
}