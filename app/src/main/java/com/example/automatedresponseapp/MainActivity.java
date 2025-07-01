package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvSignUp, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
        });


        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.equals("admin") && password.equals("1234")) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Navigate to DashboardActivity
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
