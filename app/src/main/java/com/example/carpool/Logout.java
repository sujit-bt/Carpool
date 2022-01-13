package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * A Class controlling the processes of the final Logout Screen
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 */
public class Logout extends AppCompatActivity {

    // Back and Home Buttons
    Button mBack, mClose;

    /**
     * This function is called when the current activity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // Connects the variables to their respective Buttons
        mBack = (Button) findViewById(R.id.backHome);
        mClose = (Button) findViewById(R.id.closeApp);

        // Back button Functionality
        mBack.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when back button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // Goes back to MainActivity
                Intent intent = new Intent(Logout.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Close App button functionality
        mClose.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when Close App button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // closes app
                finish();
            }
        });
    }
}