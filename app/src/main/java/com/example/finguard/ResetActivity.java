package com.example.finguard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
    private EditText mEmail;
    private Button btnReset;
    private EditText mPass;
    private FirebaseAuth mAuth;
    private Button bc_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        mEmail = findViewById(R.id.email_login);
        mPass = findViewById(R.id.password_login);
        btnReset = findViewById(R.id.btn_reset);
        bc_btn = findViewById(R.id.btn_back);

        // Back button event
        bc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetActivity.this, MainActivity.class));
                finish(); // Finish ResetActivity to prevent going back to it
            }
        });

        // Reset button event
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    mPass.setError("Password is Required..");
                    return;
                }

                // Send password reset email
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish(); // Close activity after sending email
                            } else {
                                Toast.makeText(ResetActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
