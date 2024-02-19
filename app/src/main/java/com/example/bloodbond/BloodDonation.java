package com.example.bloodbond;

public class BloodDonation {
    private final String requesterName;
    private final String phoneNumber; // Added phone number field
    private final String bloodType;
    private final String urgency;

    public BloodDonation(String requesterName, String phoneNumber, String bloodType, String urgency) {
        this.requesterName = requesterName;
        this.phoneNumber = phoneNumber;
        this.bloodType = bloodType;
        this.urgency = urgency;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getUrgency() {
        return urgency;
    }
}
