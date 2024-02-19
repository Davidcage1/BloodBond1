package com.example.bloodbond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPhoneNumber;
    private Spinner spinnerBloodType;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Button buttonSave;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        buttonSave = findViewById(R.id.buttonSave);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Populate the EditText fields with existing user data
            // Assuming you have a User class to hold user data
            User user = getUserDetailsFromDatabase(currentUser.getUid());

            if (user != null) {
                editTextFullName.setText(user.getFullName());
                editTextEmail.setText(user.getEmail());
                editTextPhoneNumber.setText(user.getPhoneNumber());
                // Set other fields accordingly
            }
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });
    }

    private User getUserDetailsFromDatabase(String userId) {
        // Fetch user details from the database based on userId
        // Return a User object
        // Implement this based on your database structure
        // For example:
        // DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        // Retrieve data from userReference and create a User object
        return null;
    }

    private void updateUserProfile() {
        // Get the updated details from the EditText fields
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String bloodType = spinnerBloodType.getSelectedItem().toString();
        String gender = radioButtonMale.isChecked() ? "Male" : "Female";

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Update the user details in the database
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
            userReference.child("fullName").setValue(fullName);
            userReference.child("email").setValue(email);
            userReference.child("phoneNumber").setValue(phoneNumber);
            userReference.child("bloodType").setValue(bloodType);
            userReference.child("gender").setValue(gender);
        }


        Intent intent = new Intent(UpdateProfileActivity.this, UserProfileActivity.class);
        startActivity(intent);

        Toast.makeText(UpdateProfileActivity.this,"Updated Successfully",Toast.LENGTH_LONG).show();
        finish();

    }
}
