package com.example.bloodbond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BloodRequestsActivity extends AppCompatActivity {

    private DatabaseReference requestsReference;
    private EditText editTextRequesterName, editTextPhoneNumber; // Added phone number field
    private Spinner spinnerBloodType;
    private RadioGroup radioGroupUrgency;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_requests);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        editTextRequesterName = findViewById(R.id.editTextRequesterName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber); // Added phone number field
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        radioGroupUrgency = findViewById(R.id.radioGroupUrgency);
        Button buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);

        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest();
            }

            private void submitRequest() {
                String requesterName = editTextRequesterName.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString(); // Get phone number
                String bloodType = spinnerBloodType.getSelectedItem().toString();
                String urgency = getUrgency();

                String message = "Requester Name: " + requesterName +
                        "\nPhone Number: " + phoneNumber + // Include phone number in the message
                        "\nBlood Type: " + bloodType +
                        "\nUrgency: " + urgency;

                addBloodRequest(requesterName, phoneNumber, bloodType, urgency); // Include phone number in the method
                Toast.makeText(BloodRequestsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getUrgency() {
        int selectedRadioButtonId = radioGroupUrgency.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedRadioButtonId);
        if (radioButton != null) {
            return radioButton.getText().toString();
        }
        return "";
    }

    private void addBloodRequest(String requesterName, String phoneNumber, String bloodType, String urgency) {
        DatabaseReference requestId = FirebaseDatabase.getInstance().getReference("Requests").child(userId);
        BloodDonation request = new BloodDonation(requesterName, phoneNumber, bloodType, urgency); // Updated constructor
        requestId.setValue(request);
        Toast.makeText(BloodRequestsActivity.this, "Request Success!!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(BloodRequestsActivity.this, MainActivity.class));
    }
}
