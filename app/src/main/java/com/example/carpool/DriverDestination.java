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

public class DriverDestination extends AppCompatActivity {

    Button mDirection, mLogout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_destination);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);

        mDirection = (Button) findViewById(R.id.directionToDriverDestination);
        mLogout = (Button) findViewById(R.id.driverLogout);

        mDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.child("destination").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        String address = (String) task.getResult().getValue();
                        address = address.trim().replace(" ", "+");
                        address = "google.navigation:q=" + address;

                        Uri gmmIntentUri = Uri.parse(address);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonInfo info = new PersonInfo();
                info.logOutUser();
                user.setValue(info);
                mAuth.signOut();
                Intent intent = new Intent(DriverDestination.this, Logout.class);
                startActivity(intent);
                finish();
            }
        });

    }
}