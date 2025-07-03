package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LinearLayout navDashboard = findViewById(R.id.nav_dashboard);
        LinearLayout navControl = findViewById(R.id.nav_control);
        LinearLayout navNotification = findViewById(R.id.nav_notification);
        LinearLayout navProfile = findViewById(R.id.nav_profile);

// Navigate to Dashboard
        navDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });

// Navigate to Control Panel
        navControl.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ControlPanelActivity.class);
            startActivity(intent);
            finish();
        });

// Navigate to Notifications
        navNotification.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
            startActivity(intent);
            finish();
        });

// Already in Profile
        navProfile.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "You are already on Profile", Toast.LENGTH_SHORT).show();
        });

    }
}
