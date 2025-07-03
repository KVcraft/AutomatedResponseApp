package com.example.automatedresponseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText etCustomerName, etEmail, etFarmName, etUsername, etPassword, etContact;
    Button btnRegister;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etCustomerName = findViewById(R.id.etCustomerName);
        etEmail = findViewById(R.id.etEmail);
        etFarmName = findViewById(R.id.etFarmName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etContact = findViewById(R.id.etContact);
        btnRegister = findViewById(R.id.btnRegister);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        btnRegister.setOnClickListener(v -> {
            String name = etCustomerName.getText().toString();
            String email = etEmail.getText().toString();
            String farm = etFarmName.getText().toString();
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String contact = etContact.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(farm)
                    || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(contact)) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                User user = new User(name, email, farm, username, password, contact);
                databaseUsers.child(username).setValue(user).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
