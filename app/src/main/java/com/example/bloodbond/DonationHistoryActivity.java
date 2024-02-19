package com.example.bloodbond;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonationHistoryActivity extends AppCompatActivity implements NewAdapter.ItemClickListener {

    private List<Donation> donationList;
    private RecyclerView recyclerView;
    private NewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);

        recyclerView = findViewById(R.id.review);
        donationList = new ArrayList<>();

        String uname = getIntent().getStringExtra("Username");

        // Get a reference to the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the "confirmedDonations" node in the database
            DatabaseReference confirmedDonationsRef = FirebaseDatabase.getInstance().getReference("confirmedDonations");

            confirmedDonationsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    donationList.clear(); // Clear the list before updating

                    for (DataSnapshot donationSnapshot : snapshot.getChildren()) {
                        Donation donation = donationSnapshot.getValue(Donation.class);
if(donation.getRequesterName().equals(uname)){
    donationList.add(donation);
    Toast.makeText(DonationHistoryActivity.this, donation.toString(), Toast.LENGTH_SHORT).show();
}

                    }



                    // Update the RecyclerView
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled
                }
            });

            // Set up the RecyclerView and Adapter
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new NewAdapter(donationList, this);
            recyclerView.setAdapter(adapter);
        }

        Toast.makeText(this,donationList.toString() , Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onItemClick(View view, int position) {
        // Handle item click, e.g., open a new activity with detailed donation information
        Donation selectedDonation = donationList.get(position);
        Toast.makeText(this, "Donation Clicked: " + selectedDonation.getRequesterName(), Toast.LENGTH_SHORT).show();

        // Example: Open a new activity with detailed donation information
        Intent intent = new Intent(this, DetailedDonationActivity.class);
        intent.putExtra("donationDetails", (CharSequence) selectedDonation);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void onCLick(View view, int adapterPosition) {
    }
}
