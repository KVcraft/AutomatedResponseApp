package com.example.automatedresponseapp;

import android.os.Bundle;
import android.widget.ImageButton;
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
    }
}
