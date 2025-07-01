package com.example.automatedresponseapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    View waterLevel;
    TextView tvWaterMM, tvTemperature, tvPressure;

    int maxTankHeightDp = 200; // same as tankFrame height
    int currentLevelMM = 480;  // manually assigned
    float temperature = 27.5f;
    int pressure = 1013;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        waterLevel = findViewById(R.id.waterLevel);
        tvWaterMM = findViewById(R.id.tvWaterMM);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvPressure = findViewById(R.id.tvPressure);

        updateUI(currentLevelMM, temperature, pressure);
    }

    private void updateUI(int waterMM, float temp, int pressure) {
        float scale = getResources().getDisplayMetrics().density;
        int tankHeightPx = (int) (maxTankHeightDp * scale + 0.5f);

        // Clamp to max height
        if (waterMM > 1000) waterMM = 1000;
        int fillHeightPx = (int) (tankHeightPx * (waterMM / 1000f));

        ViewGroup.LayoutParams params = waterLevel.getLayoutParams();
        params.height = fillHeightPx;
        waterLevel.setLayoutParams(params);

        // Update TextViews
        tvWaterMM.setText(waterMM + " mm");
        tvTemperature.setText(temp + " °C");
        tvPressure.setText(pressure + " hPa");
    }
}
