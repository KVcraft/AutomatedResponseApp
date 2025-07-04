package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    int maxTankHeightDp = 200; // height of tankFrame in dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        waterLevel = findViewById(R.id.waterLevel);
        tvWaterMM = findViewById(R.id.tvWaterMM);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvPressure = findViewById(R.id.tvPressure);

        // Firebase reference to lowercase keys
        DatabaseReference sensorRef = FirebaseDatabase.getInstance().getReference("Sensors");

        sensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Integer waterMM = snapshot.child("waterLevel").getValue(Integer.class);
                    Integer temperature = snapshot.child("temperature").getValue(Integer.class);
                    Integer pressure = snapshot.child("pressure").getValue(Integer.class);

                    if (waterMM != null && temperature != null && pressure != null) {
                        updateUI(waterMM, temperature, pressure);
                    } else {
                        Toast.makeText(DashboardActivity.this, "Incomplete sensor data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DashboardActivity.this, "Data format error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(int waterMM, int temp, int pressure) {
        float scale = getResources().getDisplayMetrics().density;
        int tankHeightPx = (int) (maxTankHeightDp * scale + 0.5f);

        // Limit the value range for visualization
        waterMM = Math.max(0, Math.min(waterMM, 1000));
        int fillHeightPx = (int) (tankHeightPx * (waterMM / 1000f));

        ViewGroup.LayoutParams params = waterLevel.getLayoutParams();
        params.height = fillHeightPx;
        waterLevel.setLayoutParams(params);

        tvWaterMM.setText(waterMM + " mm");
        tvTemperature.setText(temp + " °C");
        tvPressure.setText(pressure + " hPa");
    }
}
