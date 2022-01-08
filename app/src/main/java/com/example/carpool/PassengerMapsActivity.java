package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class PassengerMapsActivity extends AppCompatActivity {

    TextView mCurrentLocation, mDestination;
    Button mButton;

    private String currentLocation, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mCurrentLocation = (TextView) findViewById(R.id.CurrentLocation);
        mDestination = (TextView) findViewById(R.id.Destination);
        mButton = (Button) findViewById(R.id.button);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_maps);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocation = mCurrentLocation.getText().toString();
                destination = mDestination.getText().toString();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Users/Passengers");

                String[] a = new String[2];
                a[0] = currentLocation;
                a[1] = destination;
            }
        });
    }
}