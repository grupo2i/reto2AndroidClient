package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.reto2androidclient.R;

/**
 * Controller for the Home window.
 *
 * @author Aitor Fidalgo
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}