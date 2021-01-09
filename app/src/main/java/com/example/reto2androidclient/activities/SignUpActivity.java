package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.reto2androidclient.R;

/**
 * Controller for the sign up window.
 *
 * @author Aitor Fidalgo
 */
public class SignUpActivity extends AppCompatActivity {

    private Button buttonCancel, buttonAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonCancel = findViewById(R.id.buttonCancelSignUp);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLogIn = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intentToLogIn);
            }
        });

        buttonAccept = findViewById(R.id.buttonAcceptSignUp);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signUp();
                    Intent intentToHome = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intentToHome);
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void signUp() {
        throw new UnsupportedOperationException();
    }
}