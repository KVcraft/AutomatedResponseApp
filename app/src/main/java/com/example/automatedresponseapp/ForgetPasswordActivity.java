package com.example.automatedresponseapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button btnSendReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        etEmail = findViewById(R.id.etEmail);

        btnSendReset.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            if (!email.isEmpty()) {
                Toast.makeText(this, "Reset link sent to " + email, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
