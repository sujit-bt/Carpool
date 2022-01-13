package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A Class controlling the processes of the screen of the app where driver can det directions to his destination
 *
 * @author Sujit Patil
 * @since JDK 11.0.10
 * @version 1.0
 */
public class DriverDestination extends AppCompatActivity {

    // Directions and logout buttons
    Button mDirection, mLogout;
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
        setContentView(R.layout.activity_driver_destination);

        // Storing values from the Firebase database
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);

        // Connects the variables to their respective Views
        mDirection = (Button) findViewById(R.id.directionToDriverDestination);
        mLogout = (Button) findViewById(R.id.driverLogout);

        // Go to driver's destination button functionality
        mDirection.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // gets the user's destination
                user.child("destination").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    /**
                     * is called when the .get() function is finished
                     *
                     * @param task - the value returned by the .get() function
                     */
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        // The address to go to
                        String address = (String) task.getResult().getValue();

                        // Changing the format of address to an iterable format
                        address = address.trim().replace(" ", "+");
                        address = "google.navigation:q=" + address;

                        // Opens Google Maps with Turn by turn instructions to go to address
                        Uri gmmIntentUri = Uri.parse(address);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
            }
        });

        // Logout button functionality
        mLogout.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when button is clicked
             *
             * @param v - view that was clicked
             */
            @Override
            public void onClick(View v) {
                // Sets all the child values of the user to "logout" to avoid accidentally connecting with another passenger
                PersonInfo info = new PersonInfo();
                info.setAllAttributes("logout");
                user.setValue(info);

                // Logs out user
                mAuth.signOut();

                // Opens the final screen
                Intent intent = new Intent(DriverDestination.this, Logout.class);
                startActivity(intent);
                finish();
            }
        });

    }
}