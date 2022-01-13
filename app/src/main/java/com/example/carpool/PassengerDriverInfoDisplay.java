package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class PassengerDriverInfoDisplay extends AppCompatActivity {

    // logout button
    Button mLogout;
    // Driver's current location, destination, and phone number will be displayed in these TextViews
    TextView mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;

    // Driver's Firebase key
    String driver_id;

    /**
     * This function is called when the current activity is first created
     *
     * @param savedInstanceState - Stores the most recent data of the activity.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info_display);

        // Connects the variables to their respective Views
        mCurrentLocation = (TextView) findViewById(R.id.driverCurrentLocation);
        mDestination = (TextView) findViewById(R.id.driverDestination);
        mPhone = (TextView) findViewById(R.id.driverPhoneNumber);
        mLogout = (Button) findViewById(R.id.toPassengerLogout);

        // Storing values from the Firebase database
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers").child(user_id);

        // ChildEventListener listens for any changes to the children of a Firebase database.
        // Called in specific instances
        ChildEventListener childEventListener = new ChildEventListener() {
            /**
             * Called whenever a child has been added to a database reference.
             *
             * @param snapshot stores a copy of the child that has been changed
             * @param previousChildName stores the key of the child directly above the changed child
             */
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            /**
             * Called whenever a child value in a database reference has been changed.
             * Here only the connectedTo child will change when a driver successfully connects to the passenger.
             *
             * @param snapshot stores a copy of the child that has been changed
             * @param previousChildName stores the key of the child directly above the changed child
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                getDriverInfo(snapshot);
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

            }

            /**
             * Gets the connected driver's info and updates the screen with it.
             *
             * @param snapshot
             */
            private void getDriverInfo(DataSnapshot snapshot) {
                // Driver's user key taken from the snapshot
                driver_id = snapshot.getValue(String.class);

                // gets the driver's information using driver_id
                FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driver_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    /**
                     * is called when the .get() function is finished
                     *
                     * @param task - the value returned by the .get() function
                     */
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        // if task is not successful, return an error message
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            // gets the value of task as a PersonInfo class
                            PersonInfo info = task.getResult().getValue(PersonInfo.class);

                            // Updates the TextViews
                            mCurrentLocation.setText(info.getcLocation());
                            mDestination.setText(info.getDestination());
                            mPhone.setText(info.getPhoneNumber());
                        }
                    }
                });
            }
        };
        // Connects the childEventListener to the user
        // Only changes made to the user's data will be noted
        user.addChildEventListener(childEventListener);

        // Logout button functionality
        mLogout.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when logout button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // removes childEventListener so that further changes will not call any additional functions
                user.removeEventListener(childEventListener);

                // sets all the user's values to "logout" so driver's won't accidentally connect to the passenger
                PersonInfo info = new PersonInfo();
                info.logOutUser();
                user.setValue(info);

                // sign out user and change activity to Logout
                mAuth.signOut();
                Intent intent = new Intent(PassengerDriverInfoDisplay.this, Logout.class);
                startActivity(intent);
                finish();
            }
        });
    }
}