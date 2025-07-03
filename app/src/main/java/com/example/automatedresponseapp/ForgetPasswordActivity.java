package com.example.automatedresponseapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button btnResetPassword;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword); // Make sure this matches your XML filename

        etEmail = findViewById(R.id.etEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        btnResetPassword.setOnClickListener(v -> {
            String enteredEmail = etEmail.getText().toString().trim();

            if (enteredEmail.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean found = false;

                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String email = userSnapshot.child("email").getValue(String.class);
                        String password = userSnapshot.child("password").getValue(String.class);

                        if (email != null && email.equalsIgnoreCase(enteredEmail)) {
                            found = true;
                            Toast.makeText(ForgetPasswordActivity.this, "Your password: " + password, Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

                    if (!found) {
                        Toast.makeText(ForgetPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ForgetPasswordActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
