package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    View waterLevel;
    TextView tvWaterMM, tvTemperature, tvPressure;

    int maxTankHeightDp = 200; // same as tankFrame height

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        waterLevel = findViewById(R.id.waterLevel);
        tvWaterMM = findViewById(R.id.tvWaterMM);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvPressure = findViewById(R.id.tvPressure);

        // ✅ Firebase sensor reference
        DatabaseReference sensorRef = FirebaseDatabase.getInstance().getReference("Sensors");

        sensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer waterMM = snapshot.child("waterLevel").getValue(Integer.class);
                    Float temp = snapshot.child("temperature").getValue(Float.class);
                    Integer press = snapshot.child("pressure").getValue(Integer.class);

                    if (waterMM != null && temp != null && press != null) {
                        updateUI(waterMM, temp, press);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout navControl = findViewById(R.id.nav_control);
        LinearLayout navNotification = findViewById(R.id.nav_notification);
        LinearLayout navProfile = findViewById(R.id.nav_profile);

        navControl.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ControlPanelActivity.class);
            startActivity(intent);
        });

        navNotification.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

    }

    private void updateUI(int waterMM, float temp, int pressure) {
        float scale = getResources().getDisplayMetrics().density;
        int tankHeightPx = (int) (maxTankHeightDp * scale + 0.5f);

        // Clamp max
        if (waterMM > 1000) waterMM = 1000;
        int fillHeightPx = (int) (tankHeightPx * (waterMM / 1000f));

        ViewGroup.LayoutParams params = waterLevel.getLayoutParams();
        params.height = fillHeightPx;
        waterLevel.setLayoutParams(params);

        tvWaterMM.setText(waterMM + " mm");
        tvTemperature.setText(temp + " °C");
        tvPressure.setText(pressure + " hPa");
    }
}