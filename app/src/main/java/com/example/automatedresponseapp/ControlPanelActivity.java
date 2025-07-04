package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ControlPanelActivity extends AppCompatActivity {

    ToggleButton toggleDoor1, toggleDoor2, toggleDoor3, toggleDoor4, toggleDoor5, toggleSprinkler;
    ImageButton btnLogout;

    DatabaseReference databaseRef;

    // Firebase keys
    private final String KEY_FRONT_DOOR = "frontDoor";
    private final String KEY_BACK_DOOR = "backDoor";
    private final String KEY_MAIN_GATE = "mainGate";
    private final String KEY_BUNK_DOOR = "bunkDoor";
    private final String KEY_HILL_GATE = "hillGate";
    private final String KEY_WATER_SPRINKLER = "waterSpinner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        // Initialize toggle buttons
        toggleDoor1 = findViewById(R.id.toggleDoor1);
        toggleDoor2 = findViewById(R.id.toggleDoor2);
        toggleDoor3 = findViewById(R.id.toggleDoor3);
        toggleDoor4 = findViewById(R.id.toggleDoor4);
        toggleDoor5 = findViewById(R.id.toggleDoor5);
        toggleSprinkler = findViewById(R.id.toggleSprinkler);
        btnLogout = findViewById(R.id.btnLogout)            ;

        // Initialize Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("Door");

        // Load current statuses from Firebase and listen for real-time updates
        loadStatusesFromFirebase();

        // Set listeners to update Firebase when toggles change by user
        setupToggleListeners();

        // Bottom navigation setup
        setupBottomNavigation();

        // Just go back to LoginActivity without clearing saved credentials
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ControlPanelActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadStatusesFromFirebase() {
        // For each door and sprinkler, listen for real-time updates and update toggle accordingly
        updateToggleFromFirebase(KEY_FRONT_DOOR, toggleDoor1);
        updateToggleFromFirebase(KEY_BACK_DOOR, toggleDoor2);
        updateToggleFromFirebase(KEY_MAIN_GATE, toggleDoor3);
        updateToggleFromFirebase(KEY_BUNK_DOOR, toggleDoor4);
        updateToggleFromFirebase(KEY_HILL_GATE, toggleDoor5);
        updateToggleFromFirebase(KEY_WATER_SPRINKLER, toggleSprinkler);
    }

    private void updateToggleFromFirebase(String key, ToggleButton toggle) {
        databaseRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.getValue(String.class);
                if (status != null) {
                    boolean isOpen = status.equalsIgnoreCase("open");
                    // IMPORTANT: Remove listener temporarily to avoid infinite loop
                    toggle.setOnCheckedChangeListener(null);
                    toggle.setChecked(isOpen);
                    // Re-attach listener after programmatic update
                    toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        updateFirebaseStatus(key, isChecked);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ControlPanelActivity.this, "Failed to load " + key + " status.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToggleListeners() {
        toggleDoor1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFirebaseStatus(KEY_FRONT_DOOR, isChecked);
        });

        toggleDoor2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFirebaseStatus(KEY_BACK_DOOR, isChecked);
        });

        toggleDoor3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFirebaseStatus(KEY_MAIN_GATE, isChecked);
        });

        toggleDoor4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFirebaseStatus(KEY_BUNK_DOOR, isChecked);
        });

        toggleDoor5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFirebaseStatus(KEY_HILL_GATE, isChecked);
        });

        toggleSprinkler.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFirebaseStatus(KEY_WATER_SPRINKLER, isChecked);
        });
    }

    private void updateFirebaseStatus(String key, boolean isOpen) {
        String status = isOpen ? "open" : "close";
        databaseRef.child(key).setValue(status)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ControlPanelActivity.this, key + " set to " + status, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ControlPanelActivity.this, "Failed to update " + key, Toast.LENGTH_SHORT).show();
                });
    }

    private void setupBottomNavigation() {
        LinearLayout navControl = findViewById(R.id.nav_control);
        LinearLayout navNotification = findViewById(R.id.nav_notification);
        LinearLayout navProfile = findViewById(R.id.nav_profile);
        LinearLayout navDashboard = findViewById(R.id.nav_dashboard);

        navDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(ControlPanelActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });

        navControl.setOnClickListener(v -> {
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
