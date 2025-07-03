package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


    LinearLayout navDashboard = findViewById(R.id.nav_dashboard);
    LinearLayout navControl = findViewById(R.id.nav_control);
    LinearLayout navNotification = findViewById(R.id.nav_notification);
    LinearLayout navProfile = findViewById(R.id.nav_profile);

// Navigate to Dashboard
navDashboard.setOnClickListener(v -> {
        Intent intent = new Intent(NotificationActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    });

// Navigate to Control Panel
navControl.setOnClickListener(v -> {
        Intent intent = new Intent(NotificationActivity.this, ControlPanelActivity.class);
        startActivity(intent);
        finish();
    });

// Already in NotificationActivity
navNotification.setOnClickListener(v -> {
        Toast.makeText(NotificationActivity.this, "You are already on Notifications", Toast.LENGTH_SHORT).show();
    });

// Navigate to Profile
navProfile.setOnClickListener(v -> {
        Intent intent = new Intent(NotificationActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    });


    }
}
