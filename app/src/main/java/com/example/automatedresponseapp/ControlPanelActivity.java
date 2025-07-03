package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class ControlPanelActivity extends AppCompatActivity {

    ToggleButton toggleDoor1, toggleDoor2, toggleDoor3, toggleDoor4, toggleDoor5, toggleSprinkler;
    ImageButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel); // Link to your XML

        // Initialize toggle buttons
        toggleDoor1 = findViewById(R.id.toggleDoor1);
        toggleDoor2 = findViewById(R.id.toggleDoor2);
        toggleDoor3 = findViewById(R.id.toggleDoor3);
        toggleDoor4 = findViewById(R.id.toggleDoor4);
        toggleDoor5 = findViewById(R.id.toggleDoor5);
        toggleSprinkler = findViewById(R.id.toggleSprinkler);
        btnLogout = findViewById(R.id.btnLogout);

        // Example: Toggle listener
        toggleDoor1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Close door logic
            } else {
                // Open door logic
            }
        });

        // Add similar listeners for other toggles and logout if needed
        LinearLayout navControl = findViewById(R.id.nav_control);
        LinearLayout navNotification = findViewById(R.id.nav_notification);
        LinearLayout navProfile = findViewById(R.id.nav_profile);
        LinearLayout navDashboard = findViewById(R.id.nav_dashboard); // Make sure you also have this ID in your XML

        navDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(ControlPanelActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Optional: finish current activity to prevent back stack clutter
        });

        navControl.setOnClickListener(v -> {
            // You're already in Control Panel
            Toast.makeText(ControlPanelActivity.this, "You are already on Control Panel", Toast.LENGTH_SHORT).show();
        });

        navNotification.setOnClickListener(v -> {
            Intent intent = new Intent(ControlPanelActivity.this, NotificationActivity.class);
            startActivity(intent);
            finish();
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ControlPanelActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

    }


}
