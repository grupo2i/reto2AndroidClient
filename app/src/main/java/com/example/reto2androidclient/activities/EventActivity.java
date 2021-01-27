package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTRatingClient;
import com.example.reto2androidclient.client.RESTRatingInterface;
import com.example.reto2androidclient.model.Artist;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.Event;
import com.example.reto2androidclient.model.Rating;
import com.example.reto2androidclient.model.RatingList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    Event event = null;
    Client client = null;
    Rating rating = null;

    ImageView logoImageView;
    TextView dateTextView;
    TextView nameTextView;
    TextView placeTextView;
    TextView descriptionTextView;
    TextView priceTextView;
    //TextView ratingsTextView;
    TextView artistsTextView;
    TextView clubTextView;
    RatingBar ratingBar;
    EditText editTextTextMultiLine;
    Button saveRatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Get event and client
        client = (Client)getIntent().getExtras().getSerializable("CLIENT");
        event = (Event)getIntent().getExtras().getSerializable("EVENT");

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
                            if(r.getId().getEventId() == event.getId()) {
                                rating = r;
                                ratingBar.setNumStars(rating.getRating());
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

        logoImageView = (ImageView)findViewById(R.id.logoImageView);
        dateTextView = (TextView)findViewById(R.id.dateTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        placeTextView = (TextView)findViewById(R.id.placeTextView);
        descriptionTextView = (TextView)findViewById(R.id.descriptionTextView);
        priceTextView = (TextView)findViewById(R.id.priceTextView);
        //ratingsTextView = (TextView)findViewById(R.id.ratingsTextView);
        artistsTextView = (TextView)findViewById(R.id.artistsTextView);
        clubTextView = (TextView)findViewById(R.id.clubTextView);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        editTextTextMultiLine = (EditText)findViewById(R.id.editTextTextMultiLine);
        saveRatingButton = (Button)findViewById(R.id.saveRatingButton);
        saveRatingButton.setOnClickListener((View v) -> {
            if(rating == null) {
                rating = new Rating();
                rating.setClient(client);
                rating.setEvent(event);
                rating.setRating(ratingBar.getNumStars());
                rating.setComment(editTextTextMultiLine.getText().toString());
            } else {
                rating.setRating(ratingBar.getNumStars());
                rating.setComment(editTextTextMultiLine.getText().toString());
            }
            Call<ResponseBody> callEditRating = restRatingInterface.edit(rating);
            callEditRating.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    switch(response.code()) {
                        case 200:
                            Toast.makeText(getApplicationContext(), R.string.saved_rating, Toast.LENGTH_LONG).show();
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
        });

        logoImageView.setImageResource(getResources().getIdentifier(event.getProfileImage(), "drawable", getPackageName()));
        dateTextView.setText(event.getDate().substring(0, event.getDate().indexOf("T")));
        nameTextView.setText(event.getName());
        placeTextView.setText(event.getPlace());
        descriptionTextView.setText(event.getDescription());
        priceTextView.setText(String.valueOf(event.getTicketprice()));
        //ratingsTextView = (TextView)findViewById(R.id.ratingsTextView);
        if(!event.getArtists().isEmpty()) {
            String artists = "";
            for(Artist a : event.getArtists()) {
                artists += a.getFullName() + ", ";
            }
            artists = artists.substring(artists.lastIndexOf(","));
            artistsTextView.setText(artists);
        }
        clubTextView.setText(event.getClub().getFullName());
    }
}