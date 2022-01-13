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

/**
 * A Class controlling the processes of the screen of the app where driver can enter their information
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 */
public class DriverInfoUpdate extends AppCompatActivity {

    // Submit button
    Button mSubmit;
    // Driver's current location, destination, and phone number
    EditText mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;

    /**
     * This function is called when the current activity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info_update);

        // Storing values from the Firebase database
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);

        // Connects the variables to their respective Views
        mCurrentLocation = (EditText) findViewById(R.id.driverInputtedCurrentLocation);
        mDestination = (EditText) findViewById(R.id.driverInputtedDestination);
        mPhone = (EditText) findViewById(R.id.driverInputtedPhoneNumber);
        mSubmit = (Button) findViewById(R.id.updateDriverInfo);

        // Submit button functionality
        mSubmit.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // get driver's information from the EditTexts
                final String currentLocation = mCurrentLocation.getText().toString();
                final String destination = mDestination.getText().toString();
                final String phone = mPhone.getText().toString();

                // If one of the TextViews is left blank display an error message
                if (currentLocation.isEmpty() || destination.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(DriverInfoUpdate.this, "Please do not leave the form blank!", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the entered information to the user
                    user.setValue(new PersonInfo(currentLocation, destination, phone));

                    // change the app to the next DriverPassengerDisplay screen
                    Intent intent = new Intent(DriverInfoUpdate.this, DriverPassengerInfoDisplay.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}