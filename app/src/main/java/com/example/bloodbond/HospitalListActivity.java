package com.example.bloodbond;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewHospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        recyclerViewHospitals = findViewById(R.id.recyclerViewHospitals);
        recyclerViewHospitals.setLayoutManager(new LinearLayoutManager(this));

        List<Hospital> hospitals = createHospitalList(); // Replace with your data source
        HospitalListAdapter adapter = new HospitalListAdapter(hospitals, new HospitalListAdapter.OnHospitalClickListener() {
            @Override
            public void onHospitalClick(int position) {
                Hospital clickedHospital = hospitals.get(position);
                openGoogleMaps(clickedHospital);
            }
        });
        recyclerViewHospitals.setAdapter(adapter);
    }

    // Create a sample hospital list (replace with your data source)
    private List<Hospital> createHospitalList() {
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital("Chellaram Hospital", "Building No 4, Lalani Quantum, AH47, Bavdhan, Pune, Maharashtra 411021", "2.3 miles", 18.5197493, 73.7673263));
        hospitals.add(new Hospital("Surya Sahyadri Hospital", "1317, Agarwal Rd, Near Shaniwar Wada, Lunanagar, Kasba Peth, Pune, Maharashtra 411011", "5.1 miles", 18.5214306, 73.8532778));
        hospitals.add(new Hospital("Deccan Hardikar Hospital.", "1160/61, University Rd, Shivajinagar, Pune, Maharashtra 411005", "3.7 miles", 18.5164401, 73.8404561));
        return hospitals;
    }

    // Open Google Maps with the hospital's location
    private void openGoogleMaps(Hospital hospital) {
        String geoUri = "geo:" + hospital.getLatitude() + "," + hospital.getLongitude() +
                "?q=" + hospital.getLatitude() + "," + hospital.getLongitude() +
                "(" + Uri.encode(hospital.getName()) + ")";

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Handle the case where Google Maps is not installed
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
