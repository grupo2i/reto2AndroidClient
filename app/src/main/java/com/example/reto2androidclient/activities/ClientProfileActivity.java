package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reto2androidclient.R;

/**
 * Controller for the client profile window.
 *
 * @author Aitor Fidalgo
 */
public class ClientProfileActivity extends AppCompatActivity {

    private Button buttonLogOut, buttonChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        buttonLogOut = findViewById(R.id.buttonLogOutClientProfile);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLogIn = new Intent(ClientProfileActivity.this, LogInActivity.class);
                startActivity(intentToLogIn);
            }
        });

        buttonChangePassword = findViewById(R.id.buttonChangePasswordClientProfile);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordChange();
            }
        });
    }

    private void passwordChange() {
        throw new UnsupportedOperationException();
    }
}