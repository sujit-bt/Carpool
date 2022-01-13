package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A Class controlling the processes of passenger information input screen of the app
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 *
 */
public class PassengerInfoUpdate extends AppCompatActivity {

    // Submit button
    Button mSubmit;
    // Current Location, destination, phone number textboxes where passenger will input their information
    EditText mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;

    /**
     * This function is called when the current activity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info_update);

        // Storing values from the Firebase database
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference passengers = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers");

        // Connects the variables to their respective Views
        mSubmit = (Button) findViewById(R.id.updatePassengerInfo);
        mCurrentLocation = (EditText) findViewById(R.id.passengerInputtedCurrentLocation);
        mDestination = (EditText) findViewById(R.id.passengerInputtedDestination);
        mPhone = (EditText) findViewById(R.id.passengerInputtedPhoneNumber);

        // Controls what happens when submit button is pressed
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the strings that were inputted into the textviews
                final String currentLocation = mCurrentLocation.getText().toString();
                final String destination = mDestination.getText().toString();
                final String phone = mPhone.getText().toString();

                // if the fields are empty a warning will be displayed
                if (currentLocation.isEmpty() || destination.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(PassengerInfoUpdate.this, "Please do not leave the form blank!", Toast.LENGTH_SHORT).show();
                } else {
                    // Stores value into the database
                    passengers.child(user_id).setValue(new PersonInfo(currentLocation, destination, phone));

                    // Changes to the next screen - PassengerDriverInfoDisplay
                    Intent intent = new Intent(PassengerInfoUpdate.this, PassengerDriverInfoDisplay.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}