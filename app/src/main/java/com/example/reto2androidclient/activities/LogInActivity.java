package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reto2androidclient.R;

/**
 * Controller for the log in window.
 *
 * @author Aitor Fidalgo
 */
public class LogInActivity extends AppCompatActivity {

    private Button buttonSignIn, buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intentToHome);
            }
        });

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSignUp = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intentToSignUp);
            }
        });
    }
}