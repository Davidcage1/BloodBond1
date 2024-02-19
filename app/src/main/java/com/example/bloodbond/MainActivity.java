package com.example.bloodbond;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity <ItemClicked> extends AppCompatActivity {

    ListView listView;
    ArrayList<DashB> dashBArrayList;

    private MyCustomAdapter adapter;

    String name;

    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private ImageView imageViewProfile,imageViewProfile1;
    private TextView textViewFullName,textViewBloodType,textViewProfile;
    private DataSnapshot dataSnapshot;
    Button b1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b1=findViewById(R.id.logout);
        listView=findViewById(R.id.listvw);

        dashBArrayList=new ArrayList<>();

        DashB dashB1=new DashB("Requests",R.drawable.request);
        dashBArrayList.add(dashB1);

        DashB dashB2=new DashB("Schedule",R.drawable.scheduling);
        dashBArrayList.add(dashB2);

        DashB dashB3=new DashB("Emergency",R.drawable.locator);
        dashBArrayList.add(dashB3);

        DashB dashB4=new DashB("History",R.drawable.history1);
        dashBArrayList.add(dashB4);

        DashB dashB5=new DashB("Profile",R.drawable.ic_male_avatar);
        dashBArrayList.add(dashB5);



        /* DashB dashB5=new DashB("Donation History",R.drawable.history);
        dashBArrayList.add(dashB5);

        DashB dashB6=new DashB("Emergency Services",R.drawable.emergency);
        dashBArrayList.add(dashB6)*/

        adapter=new MyCustomAdapter(dashBArrayList,getApplicationContext());
        listView.setAdapter(adapter);


        navigationView = findViewById(R.id.navigationdrawer);
        View headerView = navigationView.getHeaderView(0);
        imageViewProfile = headerView.findViewById(R.id.imageViewProfile);
        textViewFullName = headerView.findViewById(R.id.textViewFullName);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                // Redirect to WelcomeActivity
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();  // Finish the current activity to prevent going back on pressing back button
                Toast.makeText(MainActivity.this,"Signed out",Toast.LENGTH_LONG).show();
            }
        });


        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Set up the navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());

        // Retrieve user details from Firebase Realtime Database
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    User userData = dataSnapshot.getValue(User.class);
                    if (userData != null) {
                        // Set user's full name
//                        textViewFullName.setText(userData.getFullName());

                        name=userData.getFullName();
                        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_sign_out) {
                    signOut();
                }
                // Close the drawer after selecting an item
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }



            private void signOut() {
                FirebaseAuth.getInstance().signOut();

                // Redirect to WelcomeActivity
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();  // Finish the current activity to prevent going back on pressing back button
                Toast.makeText(MainActivity.this,"Signed out",Toast.LENGTH_LONG).show();
            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemPosition = i;
                if (itemPosition == 0) {
                    Intent intent = new Intent(MainActivity.this, BloodRequestsActivity.class);
                    startActivity(intent);

                } else if (itemPosition == 1) {
                    Intent intent = new Intent(MainActivity.this, BloodSchedulingActivity.class);
                    startActivity(intent);

                }else if (itemPosition == 2) {
                    Intent intent = new Intent(MainActivity.this, HospitalListActivity.class);
                    startActivity(intent);

                }else if (itemPosition == 3) {
                    Intent intent = new Intent(MainActivity.this, DonationHistoryActivity.class);
                    intent.putExtra("Username",name);
                    startActivity(intent);

                }else if (itemPosition == 4) {
                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in
        com.google.firebase.auth.FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // User is signed in, update the header with user details
            updateNavHeader(currentUser);
//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }

    }

    private void updateNavHeader(FirebaseUser user) {
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

            // Retrieve user details from Firebase Realtime Database
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {
                        User userData = dataSnapshot.getValue(User.class);
                        if (userData != null) {
                            // Set user's full name
                            textViewFullName.setText(userData.getFullName());



                            // Determine gender based on gender field
                            if (userData.getGender() != null) {
                                if (userData.getGender().equalsIgnoreCase("female")) {
                                    imageViewProfile.setImageResource(R.drawable.ic_female_avatar);
                                } else if (userData.getGender().equalsIgnoreCase("male")) {
                                    imageViewProfile.setImageResource(R.drawable.ic_male_avatar);
                                } else {
                                    // Default to male if gender is not specified
                                    imageViewProfile.setImageResource(R.drawable.ic_male_avatar);
                                }
                            }
                        }
                    } else {
                        // Handle the case where the user data doesn't exist
                        // This could be due to incorrect user ID or other issues
                        // You may display a default message or take appropriate action
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

    }





    // Set a default male or female avatar based on the display name

}
