package com.example.bloodbond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewFullName, textViewEmail, textViewPhoneNumber, textViewBloodType, textViewGender;
    private Button buttonEdit;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textViewFullName = findViewById(R.id.textViewFullName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewBloodType = findViewById(R.id.textViewBloodType);
        textViewGender = findViewById(R.id.textViewGender);
        buttonEdit = findViewById(R.id.buttonEdit);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, update the header with user details
            updateProfileDetails(currentUser.getUid());
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the UpdateProfileActivity for editing
                startActivity(new Intent(UserProfileActivity.this, UpdateProfileActivity.class));
            }
        });
    }

    private void updateProfileDetails(String userId) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userData = dataSnapshot.getValue(User.class);
                    if (userData != null) {
                        textViewFullName.setText("Full Name: " + userData.getFullName());
                        textViewEmail.setText("Email: " + userData.getEmail());
                        textViewPhoneNumber.setText("Phone Number: " + userData.getPhoneNumber());
                        textViewBloodType.setText("Blood Type: " + userData.getBloodType());
                        textViewGender.setText("Gender: " + userData.getGender());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
