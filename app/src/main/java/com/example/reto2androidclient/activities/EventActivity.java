package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTClientClient;
import com.example.reto2androidclient.client.RESTClientInterface;
import com.example.reto2androidclient.client.RESTRatingClient;
import com.example.reto2androidclient.client.RESTRatingInterface;
import com.example.reto2androidclient.model.Artist;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.model.Rating;
import com.example.reto2androidclient.model.RatingId;
import com.example.reto2androidclient.model.RatingList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    private Event event = null;
    private Client client = null;
    private Rating rating = null;

    private ImageView logoImageView;
    private TextView dateTextView;
    private TextView nameTextView;
    private TextView placeTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private TextView artistsTextView;
    private TextView clubTextView;
    private RatingBar ratingBar;
    private EditText editTextTextMultiLine;
    private Button saveRatingButton;
    private Button wishlistButton;
    private Boolean addToWishlist = true;

    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //TODO: -Show rating average
        //      -Save new rating

        //Get event and client
        client = (Client)getIntent().getExtras().getSerializable("CLIENT");
        event = (Event)getIntent().getExtras().getSerializable("EVENT");

        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToHome = new Intent(EventActivity.this, HomeActivity.class);
                intentToHome.putExtra("CLIENT", client);
                startActivity(intentToHome);
            }
        });

        imageButtonSearch = findViewById(R.id.imageButtonSearchHome);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSearch = new Intent(EventActivity.this, SearchActivity.class);
                startActivity(intentToSearch);
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistClientProfile);
        imageButtonWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToWishlist = new Intent(EventActivity.this, WishlistActivity.class);
                intentToWishlist.putExtra("CLIENT", client);
                startActivity(intentToWishlist);
            }
        });

        imageButtonProfile = findViewById(R.id.imageButtonProfileClientProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToClientProfile = new Intent(EventActivity.this, ClientProfileActivity.class);
                intentToClientProfile.putExtra("CLIENT", client);
                startActivity(intentToClientProfile);
            }
        });

        //Get the associated rating if any
        RESTRatingInterface restRatingInterface = RESTRatingClient.getClient();
        Call<RatingList> callClientRatings = restRatingInterface.getAllRatingsByUserId(client.getId());
        callClientRatings.enqueue(new Callback<RatingList>() {
            @Override
            public void onResponse(Call<RatingList> call, Response<RatingList> response) {
                switch(response.code()) {
                    case 200:
                        RatingList ratings = response.body();
                        for(Rating r : ratings.getRatings()) {
                            if(r.getId().getEventId().intValue() == event.getId().intValue()) {
                                rating = r;
                                ratingBar.setRating(rating.getRating());
                                editTextTextMultiLine.setText(rating.getComment());
                            }
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RatingList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
            }
        });

        //Setup layout
        logoImageView = (ImageView)findViewById(R.id.logoImageView);
        dateTextView = (TextView)findViewById(R.id.dateTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        placeTextView = (TextView)findViewById(R.id.placeTextView);
        descriptionTextView = (TextView)findViewById(R.id.descriptionTextView);
        priceTextView = (TextView)findViewById(R.id.priceTextView);
        artistsTextView = (TextView)findViewById(R.id.artistsTextView);
        clubTextView = (TextView)findViewById(R.id.clubTextView);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        editTextTextMultiLine = (EditText)findViewById(R.id.editTextTextMultiLine);
        saveRatingButton = (Button)findViewById(R.id.saveRatingButton);
        saveRatingButton.setOnClickListener((View v) -> {
            if(rating == null) {
                rating = new Rating();
                rating.setId(new RatingId(client.getId(), event.getId()));
                rating.setRating((int)ratingBar.getRating());
                rating.setComment(editTextTextMultiLine.getText().toString());

                Call<ResponseBody> callRatingCreate = restRatingInterface.create(rating);
                callRatingCreate.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        switch(response.code()) {
                            case 204:
                                Toast.makeText(getApplicationContext(), R.string.saved_rating, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                rating.setRating((int)ratingBar.getRating());
                rating.setComment(editTextTextMultiLine.getText().toString());
                Call<ResponseBody> callEditRating = restRatingInterface.edit(rating);
                callEditRating.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        switch(response.code()) {
                            case 204:
                                Toast.makeText(getApplicationContext(), R.string.saved_rating, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        wishlistButton = (Button)findViewById(R.id.wishlistButton);
        for(Event e : client.getEvents()) {
            if(e.getId().intValue() == event.getId().intValue()) {
                wishlistButton.setText(R.string.remove_wishlist);
                addToWishlist = false;
            }
        }
        wishlistButton.setOnClickListener((View v) -> {
            if(addToWishlist) {
                client.getEvents().add(event);
                RESTClientInterface restClientInterface = RESTClientClient.getClient();
                Call<ResponseBody> callClientEdit = restClientInterface.edit(client);
                callClientEdit.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        switch(response.code()) {
                            case 204:
                                wishlistButton.setText(R.string.remove_wishlist);
                                addToWishlist = false;
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                for(Event e : client.getEvents()) {
                    if(e.getId().intValue() == event.getId().intValue()) {
                        client.getEvents().remove(e);
                        break;
                    }
                }
                RESTClientInterface restClientInterface = RESTClientClient.getClient();
                Call<ResponseBody> callClientEdit = restClientInterface.edit(client);
                callClientEdit.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        switch(response.code()) {
                            case 204:
                                wishlistButton.setText(R.string.add_wishlist);
                                addToWishlist = true;
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        logoImageView.setImageResource(getResources().getIdentifier(event.getProfileImage(), "drawable", getPackageName()));
        dateTextView.setText(event.getDate().substring(0, event.getDate().indexOf("T")));
        nameTextView.setText(event.getName());
        placeTextView.setText(event.getPlace());
        descriptionTextView.setText(event.getDescription());
        priceTextView.setText(event.getTicketprice() + "€");
        if(!event.getArtists().isEmpty()) {
            String artists = "";
            for(Artist a : event.getArtists()) {
                artists += a.getFullName() + ", ";
            }
            artists = artists.substring(0, artists.lastIndexOf(","));
            artistsTextView.setText(artists);
        }
        clubTextView.setText(event.getClub().getFullName());
    }
}