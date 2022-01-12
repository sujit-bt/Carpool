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
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Arrays;

public class PassengerDriverInfoDisplay extends AppCompatActivity {

    Button mLogout;
    TextView mCurrentLocation, mDestination, mPhone;

    // Object that lets the code connect to the Firebase database
    FirebaseAuth mAuth;

    String driver_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info_display);

        mCurrentLocation = (TextView) findViewById(R.id.driverCurrentLocation);
        mDestination = (TextView) findViewById(R.id.driverDestination);
        mPhone = (TextView) findViewById(R.id.driverPhoneNumber);
        mLogout = (Button) findViewById(R.id.toPassengerLogout);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers").child(user_id);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                checkConnectedTo(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            private void checkConnectedTo(DataSnapshot snapshot) {
                driver_id = snapshot.getValue(String.class);

                FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driver_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            PersonInfo info = task.getResult().getValue(PersonInfo.class);
                            mCurrentLocation.setText(info.getcLocation());
                            mDestination.setText(info.getDestination());
                            mPhone.setText(info.getPhoneNumber());
                        }
                    }
                });
            }
        };
        user.addChildEventListener(childEventListener);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.removeEventListener(childEventListener);
                PersonInfo info = new PersonInfo();
                info.logOutUser();
                user.setValue(info);
                mAuth.signOut();
                Intent intent = new Intent(PassengerDriverInfoDisplay.this, PassengerLogout.class);
                startActivity(intent);
                finish();
            }
        });
    }
}