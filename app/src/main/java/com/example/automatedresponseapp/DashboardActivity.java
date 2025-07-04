package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    View waterLevel;
    TextView tvWaterMM, tvTemperature, tvPressure;
    ImageButton btnLogout;
    int maxTankHeightDp = 200;
    String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userKey = getIntent().getStringExtra("userKey");

        // Initialize views
        waterLevel = findViewById(R.id.waterLevel);
        tvWaterMM = findViewById(R.id.tvWaterMM);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvPressure = findViewById(R.id.tvPressure);
        btnLogout = findViewById(R.id.btnLogout);

        // Firebase sensor data listener
        DatabaseReference sensorRef = FirebaseDatabase.getInstance().getReference("Sensors");
        sensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer waterMM = snapshot.child("waterLevel").getValue(Integer.class);
                Float temp = snapshot.child("temperature").getValue(Float.class);
                Integer press = snapshot.child("pressure").getValue(Integer.class);

                if (waterMM != null && temp != null && press != null) {
                    updateUI(waterMM, temp, press);
                } else {
                    Toast.makeText(DashboardActivity.this, "Sensor data missing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Bottom Navigation
        findViewById(R.id.nav_control).setOnClickListener(v -> {
            Intent intent = new Intent(this, ControlPanelActivity.class);
            intent.putExtra("userKey", userKey);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.nav_notification).setOnClickListener(v -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("userKey", userKey);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("userKey", userKey);
            startActivity(intent);
            finish();
        });

        // Just go back to MainActivity without clearing saved credentials
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void updateUI(int waterMM, float temp, int pressure) {
        float scale = getResources().getDisplayMetrics().density;
        int tankHeightPx = (int) (maxTankHeightDp * scale + 0.5f);

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
