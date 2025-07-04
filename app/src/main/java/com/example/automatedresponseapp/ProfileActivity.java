package com.example.automatedresponseapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvEmail;
    Button btnEditProfile;
    ImageButton btnLogout;

    String userKey;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        userKey = getIntent().getStringExtra("userKey");
        if (userKey == null || userKey.isEmpty()) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userKey);
        loadUserData();

        btnEditProfile.setOnClickListener(v -> showEditDialog());
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // Bottom Navigation
        findViewById(R.id.nav_dashboard).setOnClickListener(v -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra("userKey", userKey);
            startActivity(intent);
            finish();
        });

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

        findViewById(R.id.nav_profile).setOnClickListener(v ->
                Toast.makeText(this, "You are already on Profile", Toast.LENGTH_SHORT).show()
        );

        ImageButton btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

    }

    private void loadUserData() {
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot snapshot = task.getResult();
                String email = snapshot.child("email").getValue(String.class);
                String username = snapshot.child("username").getValue(String.class);

                tvUsername.setText("Username: " + (username != null ? username : "N/A"));
                tvEmail.setText("Email: " + (email != null ? email : "N/A"));
            } else {
                Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Profile");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 10);

        EditText inputEmail = new EditText(this);
        inputEmail.setHint("New Email");
        inputEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(inputEmail);

        EditText inputContact = new EditText(this);
        inputContact.setHint("New Contact");
        inputContact.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(inputContact);

        EditText inputCustomerName = new EditText(this);
        inputCustomerName.setHint("New Customer Name");
        layout.addView(inputCustomerName);

        builder.setView(layout);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newEmail = inputEmail.getText().toString().trim();
            String newContact = inputContact.getText().toString().trim();
            String newCustomerName = inputCustomerName.getText().toString().trim();

            if (!newEmail.isEmpty()) userRef.child("email").setValue(newEmail);
            if (!newContact.isEmpty()) userRef.child("contact").setValue(newContact);
            if (!newCustomerName.isEmpty()) userRef.child("customerName").setValue(newCustomerName);

            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            loadUserData();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
