package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Event;

/**
 * Controller for the Home window.
 *
 * @author Aitor Fidalgo
 */
public class HomeActivity extends AppCompatActivity {

    private Client client;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonProfile;
    private Event events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        client = (Client) getIntent().getExtras().getSerializable("CLIENT");
        events = (Event) getIntent().getExtras().getSerializable("Filtered");

        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setEnabled(false);

        imageButtonSearch = findViewById(R.id.imageButtonSearchHome);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intentToSearch = new Intent(HomeActivity.this, SearchActivity.class);
            intentToSearch.putExtra("CLIENT", client);
            startActivity(intentToSearch);
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistClientProfile);
        imageButtonWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new UnsupportedOperationException();
            }
        });

        imageButtonProfile = findViewById(R.id.imageButtonProfileClientProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToClientProfile = new Intent(HomeActivity.this, ClientProfileActivity.class);
                intentToClientProfile.putExtra("CLIENT", client);
                startActivity(intentToClientProfile);
            }
        });
    }

}