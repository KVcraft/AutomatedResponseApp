package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvForgotPassword, tvSignUp;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Login button logic
        btnLogin.setOnClickListener(v -> {
            String enteredUsername = etUsername.getText().toString().trim();
            String enteredPassword = etPassword.getText().toString().trim();

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean found = false;

                    // Inside your login success block
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String dbUsername = userSnapshot.child("username").getValue(String.class);
                        String dbPassword = userSnapshot.child("password").getValue(String.class);

                        if (enteredUsername.equals(dbUsername) && enteredPassword.equals(dbPassword)) {
                            found = true;

                            // Get the Firebase UID of the matched user
                            String userKey = userSnapshot.getKey();

                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // ✅ Pass userKey to Dashboard
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            intent.putExtra("userKey", userKey);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }

                    if (!found) {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Forgot password link
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });

        // Sign up link
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

    }

}
