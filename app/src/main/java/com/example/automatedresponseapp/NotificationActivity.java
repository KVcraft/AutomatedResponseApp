package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NotificationData> notificationList;
    NotificationAdapter adapter;

    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        databaseRef = FirebaseDatabase.getInstance().getReference("Notifications");

        loadNotifications();

        // Bottom Navigation setup
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

        ImageButton btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

    }

    private void loadNotifications() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NotificationData notification = dataSnapshot.getValue(NotificationData.class);
                    if (notification != null) {
                        notificationList.add(notification);
                    }
                }

                // Reverse the list to show latest notifications first
                java.util.Collections.reverse(notificationList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NotificationActivity.this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
