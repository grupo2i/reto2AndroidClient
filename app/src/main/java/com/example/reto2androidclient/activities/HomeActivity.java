package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.model.Client;

/**
 * Controller for the Home window.
 *
 * @author Aitor Fidalgo
 */
public class HomeActivity extends AppCompatActivity {

    private Client client;
    private ImageButton imageButtonReload, imageButtonWishlist, imageButtonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageButtonReload = findViewById(R.id.imageButtonReloadHome);
        imageButtonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadContentList();
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistHome);
        imageButtonWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new UnsupportedOperationException();
            }
        });

        imageButtonProfile = findViewById(R.id.imageButtonProfileHome);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToClientProfile = new Intent(HomeActivity.this, ClientProfileActivity.class);
                intentToClientProfile.putExtra("CLIENT", client);
                startActivity(intentToClientProfile);
            }
        });
    }

    private void reloadContentList() {
        throw new UnsupportedOperationException();
    }
}