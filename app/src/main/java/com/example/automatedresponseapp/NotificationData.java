package com.example.automatedresponseapp;

public class NotificationData {
    public String title;
    public String description;
    public String date;
    public String time;

    public NotificationData() {
        // Required for Firebase
    }

    public NotificationData(String title, String description, String date, String time) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }
}
