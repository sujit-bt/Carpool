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

public class DriverPassengerInfoDisplay extends AppCompatActivity {

    Button mToPassengerCurrentLocation, mToPassengerDestination, mNext;
    TextView mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;
    String passenger_id;

    boolean passengerConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_passenger_info);

        mCurrentLocation = (TextView) findViewById(R.id.passengerCurrentLocation);
        mDestination = (TextView) findViewById(R.id.passengerDestination);
        mPhone = (TextView) findViewById(R.id.passengerPhoneNumber);
        mToPassengerCurrentLocation = (Button) findViewById(R.id.directionToPassengerCurrentLocation);
        mToPassengerDestination = (Button) findViewById(R.id.directionToPassengerDestination);
        mNext = (Button) findViewById(R.id.toDriverDestinationActivity);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference passengers = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers");
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);

        mToPassengerCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentLocation.getText() != getString(R.string.waiting_for_passenger)) {
                    String address = (String) mCurrentLocation.getText();
                    address = address.trim().replace(" ", "+");
                    address = "google.navigation:q=" + address;
                    Uri gmmIntentUri = Uri.parse(address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

        mToPassengerDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDestination.getText() != getString(R.string.waiting_for_passenger)) {
                    String address = (String) mDestination.getText();
                    address = address.trim().replace(" ", "+");
                    address = "google.navigation:q=" + address;
                    Uri gmmIntentUri = Uri.parse(address);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverPassengerInfoDisplay.this, DriverDestination.class);
                startActivity(intent);
                finish();
            }
        });

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                checkPassengers(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                checkPassengers(snapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FAILED", "loadPost:onCancelled", error.toException());
            }

            private void checkPassengers(DataSnapshot snapshot) {
                if (!passengerConnected) {
                    PersonInfo info = snapshot.getValue(PersonInfo.class);
                    if (info.getConnectedTo().isEmpty()) {
                        info.setConnectedTo(user_id);
                        passenger_id = snapshot.getKey();

                        passengers.child(passenger_id).setValue(info);
                        user.child("connectedTo").setValue(passenger_id);
                        passengerConnected = true;

                        mCurrentLocation.setText(info.getcLocation());
                        mDestination.setText(info.getDestination());
                        mPhone.setText(info.getPhoneNumber());
                    }
                }
            }
        };
        passengers.addChildEventListener(childEventListener);
    }

    private void removePassengerChildEventListener(DatabaseReference reference, ChildEventListener childEventListener) {
        reference.removeEventListener(childEventListener);
    }
}