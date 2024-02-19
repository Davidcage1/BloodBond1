package com.example.bloodbond;

public class Donation {
    public Donation() {
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Donation(String requesterName, String bloodType, String scheduleDate, String scheduleTime) {
        this.requesterName = requesterName;
        this.bloodType = bloodType;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
    }

    private String requesterName;
    private String bloodType;
    private String scheduleDate;
    private String scheduleTime;

    // Add constructors, getters, and setters
}
