package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * A Class controlling the processes of main starting screen of the app
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    // "I am a driver" and "I am a passenger" buttons
    private Button mDriver, mPassenger;

    /**
     * This function is called when the current activity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriver = (Button) findViewById(R.id.driver);
        mPassenger = (Button) findViewById(R.id.passengerHeader);

        // Controls what happens when the "I am a Driver" button is clicked
        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        // Controls what happens when the "I am a Passenger" button is clicked
        mPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PassengerLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}