package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

/**
 * A Class controlling the processes of the screen of the app where passenger's information is displayed for the driver
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 */
public class DriverPassengerInfoDisplay extends AppCompatActivity {

    // buttons that take driver to passengers current location/destination and next button
    Button mToPassengerCurrentLocation, mToPassengerDestination, mNext;
    TextView mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;
    // Passenger's user key
    String passenger_id;

    // Boolean if a passenger has been connected or not
    boolean passengerConnected = false;

    /**
     * This function is called when the current activity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_passenger_info);

        // Connects the variables to their respective Views
        mCurrentLocation = (TextView) findViewById(R.id.passengerCurrentLocation);
        mDestination = (TextView) findViewById(R.id.passengerDestination);
        mPhone = (TextView) findViewById(R.id.passengerPhoneNumber);
        mToPassengerCurrentLocation = (Button) findViewById(R.id.directionToPassengerCurrentLocation);
        mToPassengerDestination = (Button) findViewById(R.id.directionToPassengerDestination);
        mNext = (Button) findViewById(R.id.toDriverDestinationActivity);

        // Storing values from the Firebase database
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference passengers = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers");
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);

        // Go to passenger Current Location button functionality
        mToPassengerCurrentLocation.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // Only runs if a passenger has been connected to the driver
                if (passengerConnected) {
                    // address to go to
                    String address = (String) mCurrentLocation.getText();

                    // Converts the address to an acceptable input format
                    address = address.trim().replace(" ", "+");
                    address = "google.navigation:q=" + address;

                    // Opens up turn by turn navigation on Google Maps
                    Uri gmmIntentUri = Uri.parse(address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

        // Go to passenger destination button functionality
        mToPassengerDestination.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                if (passengerConnected) {
                    // address to go to
                    String address = (String) mDestination.getText();

                    // converts address to an acceptable input format
                    address = address.trim().replace(" ", "+");
                    address = "google.navigation:q=" + address;

                    // opens Google Maps with turn by turn directions to the address
                    Uri gmmIntentUri = Uri.parse(address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

        // next button functionality
        mNext.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // opens Driver destination page
                Intent intent = new Intent(DriverPassengerInfoDisplay.this, DriverDestination.class);
                startActivity(intent);
                finish();
            }
        });

        // ChildEventListener listens for any changes to the children of a Firebase database.
        // Called in specific instances
        ChildEventListener childEventListener = new ChildEventListener() {
            /**
             * Called whenever a child has been added to a database reference.
             * Runs once when the the ChildEventListener is first initialized, and then again when changes are made to a child.
             *
             * @param snapshot stores a copy of the child that has been changed
             * @param previousChildName stores the key of the child directly above the changed child
             */
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                checkPassengers(snapshot);
            }

            /**
             * Called whenever a child value in a database reference has been changed.
             * Here only the "connectedTo" child will get changed when a driver successfully connects to the passenger.
             *
             * @param snapshot stores a copy of the child that has been changed
             * @param previousChildName stores the key of the child directly above the changed child
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                checkPassengers(snapshot);

            }

            /**
             * Called whenever a child value in a database reference has been removed.
             *
             * @param snapshot stores a copy of the child that has been changed
             */
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            /**
             * Called whenever the order of a child value in a database reference has been changed.
             *
             * @param snapshot stores a copy of the child that has been changed
             * @param previousChildName stores the key of the child directly above the changed child
             */
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            /**
             * Called whenever the ChildEventListener is cancelled.
             *
             * @param error DatabaseError value
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FAILED", "loadPost:onCancelled", error.toException());
            }

            /**
             * Checks whether the passengers are available and connects the drivers if they are.
             *
             * @param snapshot
             */
            private void checkPassengers(DataSnapshot snapshot) {
                // only runs if the driver has not been connected to a passenger yet.
                if (!passengerConnected) {
                    // Gets the values of the changed child
                    PersonInfo info = snapshot.getValue(PersonInfo.class);

                    // only runs if the changed child's "connectedTo" is empty (passenger is not connected to a driver)
                    if (info.getConnectedTo().isEmpty()) {
                        // sets passengerConnected to true so driver will stop connecting to other people
                        passengerConnected = true;

                        // sets the "connectedTo" value of the user to the driver's user ID
                        info.setConnectedTo(user_id);
                        // Collects passenger's user ID
                        passenger_id = snapshot.getKey();

                        // Stores the values to the respective user's Firebase
                        passengers.child(passenger_id).setValue(info);
                        user.child("connectedTo").setValue(passenger_id);

                        // Updates the TextViews with the Passenger's information
                        mCurrentLocation.setText(info.getcLocation());
                        mDestination.setText(info.getDestination());
                        mPhone.setText(info.getPhoneNumber());
                    }
                }
            }
        };
        // Adds a child event listener to passengers
        passengers.addChildEventListener(childEventListener);
    }
}