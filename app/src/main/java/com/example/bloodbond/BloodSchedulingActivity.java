package com.example.bloodbond;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class BloodSchedulingActivity extends AppCompatActivity {
    String time;
    String date;
    private EditText editTextRequesterName;
    private Button buttonSetDate, buttonSetTime;
    private TextView textViewDateTime;
    private Calendar selectedDateTime;
    private Spinner spinnerBloodType;
    private int selectedYear,selectedMonth, selectedDay,selectedHour, selectedMinute;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_scheduling);

        editTextRequesterName = findViewById(R.id.editTextRequesterName);
        buttonSetDate = findViewById(R.id.buttnSetDate);
        buttonSetTime = findViewById(R.id.buttonSetTime);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        spinnerBloodType=findViewById(R.id.spinnerBloodType);
        selectedDateTime = Calendar.getInstance();

        Button buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);
        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleBloodDonation();
            }
        });

        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        // Rest of the existing code
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDateTime.set(Calendar.YEAR, year);
                        selectedDateTime.set(Calendar.MONTH, month);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateTimeDisplay();
                    }
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        updateDateTimeDisplay();
                    }
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateTimeDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        String dateTimeText = dateFormat.format(selectedDateTime.getTime()) +
                " " + timeFormat.format(selectedDateTime.getTime());

        textViewDateTime.setText(dateTimeText);
        time=timeFormat.format(selectedDateTime.getTime());
        date=dateFormat.format(selectedDateTime.getTime());
    }

    // Rest of the existing code

    private void scheduleBloodDonation() {
        String requesterName = editTextRequesterName.getText().toString().trim();



        if (requesterName.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new BloodDonation object


        // Add the selected date and time


        Schedule donation = new Schedule(requesterName, spinnerBloodType.getSelectedItem().toString(),date,time);

        // Push the data to Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Blood_Donations").child(userId);
        databaseReference.setValue(donation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BloodSchedulingActivity.this, "Blood donation scheduled successfully", Toast.LENGTH_SHORT).show();
                            finish();  // Close the activity after successful scheduling
                        } else {
                            Toast.makeText(BloodSchedulingActivity.this, "Failed to schedule blood donation", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
