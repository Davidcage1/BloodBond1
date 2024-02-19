package com.example.bloodbond;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextReenterPassword,editTextPhoneNumber;
    private Spinner spinnerBloodType,spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Find views
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextReenterPassword = findViewById(R.id.editTextReenterPassword);
        editTextPhoneNumber=findViewById(R.id.editTextPhoneNumber);
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        spinnerGender = findViewById(R.id.spinnerGender);

        // Find the "Register" button and set a click listener
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> registerUser());
    }




    private void registerUser() {
        // Extract user input
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String reenterPassword = editTextReenterPassword.getText().toString().trim();
        String bloodType = spinnerBloodType.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();  // New phone number field

        // Basic validation
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || reenterPassword.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegisterActivity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(reenterPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the user using Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String fullName1 = fullName;
                            String email1 = email;
                            String phoneNumber1 = phoneNumber;
                            String bloodType1 = bloodType;
                            String gender1 = gender;
                            // Save user details to Firebase Realtime Database
                            saveUserDetails(userId, fullName1, email1, phoneNumber1, bloodType1, gender1);

                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }





    private void saveUserDetails(String userId, String fullName, String email,String phoneNumber,String bloodType,String gender) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        User user = new User(fullName, email, phoneNumber, bloodType, gender);
        databaseReference.setValue(user);
        Toast.makeText(RegisterActivity.this, "Registration Success!!", Toast.LENGTH_SHORT).show();

    }

}