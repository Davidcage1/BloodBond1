package com.example.bloodbond;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Schedule {
    private String requesterName;
    private String bloodType;
    private String scheduleDate;  // Store schedule date as a string
    private String scheduleTime;  // Store schedule time as a string

    public Schedule(String requesterName, String bloodType, String scheduleDate, String scheduleTime) {
        this.requesterName = requesterName;
        this.bloodType = bloodType;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
    }

    public Schedule() {
    }
// Required default constructor for Firebase


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


    public void setScheduleTime(int selectedHour, int selectedMinute) {
        this.scheduleTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
    }

    public void setScheduleDate(int selectedYear, int selectedMonth, int selectedDay) {
        selectedMonth++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(selectedYear - 1900, selectedMonth, selectedDay);
        this.scheduleDate = dateFormat.format(date);

    }
}
